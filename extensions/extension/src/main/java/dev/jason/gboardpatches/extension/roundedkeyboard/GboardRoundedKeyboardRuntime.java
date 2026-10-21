package dev.jason.gboardpatches.extension.roundedkeyboard;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;

import dev.jason.gboardpatches.extension.flagsettings.GboardFlagRuntimeContext;

public final class GboardRoundedKeyboardRuntime {
    private static final String MAIN_PANEL_SELECTOR =
            ".keyboard-base-area.keyboard-outline";
    private static final String BOTTOM_OUTLINE_SELECTOR =
            ".keyboard-body-area.keyboard-outline-bottom.clip-to-outline-for-old-android";

    private GboardRoundedKeyboardRuntime() {
    }

    /**
     * Resolves the top corner radius configured via Morphe patch option,
     * falling back to the default radius (32 dp).
     */
    public static int resolveTopRadiusDp(Context context) {
        try {
            if (context != null) {
                PackageManager pm = context.getPackageManager();
                ApplicationInfo ai = pm.getApplicationInfo(
                        context.getPackageName(), PackageManager.GET_META_DATA);
                if (ai != null && ai.metaData != null
                        && ai.metaData.containsKey("buwin_rounded_keyboard_top_radius_dp")) {
                    Object val = ai.metaData.get("buwin_rounded_keyboard_top_radius_dp");
                    if (val instanceof Integer) {
                        return GboardRoundedKeyboardConfig.sanitizeRadiusDp((Integer) val);
                    }
                    if (val instanceof String) {
                        return GboardRoundedKeyboardConfig.sanitizeRadiusDp(
                                Integer.parseInt(((String) val).trim()));
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        GboardRoundedKeyboardConfig config = readConfigOrNull();
        return config != null ? config.getTopRadiusDp() : GboardRoundedKeyboardConfig.DEFAULT_RADIUS_DP;
    }

    /**
     * Hook called from LatinIME onWindowShown() to clip the root InputView and Window,
     * removing unwanted gray corner artifacts in apps like Messenger.
     */
    public static void onWindowShown(Object receiver) {
        try {
            if (!(receiver instanceof InputMethodService)) {
                return;
            }
            InputMethodService service = (InputMethodService) receiver;
            GboardRoundedKeyboardConfig config = readConfigOrNull();
            if (config != null && !config.isEnabled()) {
                return;
            }
            Dialog dialog = service.getWindow();
            if (dialog == null) {
                return;
            }
            Window window = dialog.getWindow();
            if (window == null) {
                return;
            }
            applyRootWindowAndInputViewRounding(window, service);
        } catch (Throwable ignored) {
            // Fail closed to prevent any IME crash
        }
    }

    private static void applyRootWindowAndInputViewRounding(
            Window window, InputMethodService service) {
        try {
            // Make Window background transparent so corners reveal app underneath instead of gray
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            View decor = window.getDecorView();
            if (decor == null) {
                return;
            }
            decor.setBackgroundColor(Color.TRANSPARENT);

            View inputArea = decor.findViewById(android.R.id.inputArea);
            if (inputArea != null) {
                inputArea.setBackgroundColor(Color.TRANSPARENT);
            }

            View inputView = findInputView(decor);
            View target = inputView != null ? inputView : (inputArea != null ? inputArea : decor);

            final int topRadiusDp = resolveTopRadiusDp(service);

            target.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    int width = view.getWidth();
                    int height = view.getHeight();
                    if (width <= 0 || height <= 0) {
                        return;
                    }
                    float density = view.getResources().getDisplayMetrics().density;
                    int topRadiusPx = Math.round(topRadiusDp * density);

                    if (topRadiusPx > 0) {
                        // Top-only: Extend bottom beyond viewport so only the top two corners are rounded
                        outline.setRoundRect(0, 0, width, height + topRadiusPx, topRadiusPx);
                    } else {
                        outline.setRect(0, 0, width, height);
                    }
                }
            });
            target.setClipToOutline(topRadiusDp > 0);
            target.addOnLayoutChangeListener((v, l, t, r, b, ol, ot, or, ob) -> v.invalidateOutline());
            target.invalidateOutline();

            decor.post(target::invalidateOutline);
        } catch (Throwable ignored) {
            // Best effort
        }
    }

    private static View findInputView(View root) {
        if (root == null) {
            return null;
        }
        if ("com.google.android.libraries.inputmethod.inputview.InputView"
                .equals(root.getClass().getName())) {
            return root;
        }
        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            for (int i = 0; i < group.getChildCount(); i++) {
                View found = findInputView(group.getChildAt(i));
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    public static boolean applyThemeAdmission(boolean stockResult) {
        if (stockResult) {
            return true;
        }
        try {
            GboardRoundedKeyboardConfig config = readConfigOrNull();
            return config == null || config.isEnabled();
        } catch (Throwable ignored) {
            return stockResult;
        }
    }

    public static float[] resolveEffectiveRadiiDp(String selector) {
        try {
            if (!MAIN_PANEL_SELECTOR.equals(selector)) {
                return null;
            }
            GboardRoundedKeyboardConfig config = readConfigOrNull();
            if (config != null && !config.isEnabled()) {
                return null;
            }
            Context context = GboardFlagRuntimeContext.contextOrNull();
            if (context != null) {
                InputMethodService ims = unwrapInputMethodService(context);
                if (ims != null) {
                    onWindowShown(ims);
                }
            }
            float top = (float) resolveTopRadiusDp(context);
            return new float[] {top, top, 0.0f, 0.0f};
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static InputMethodService unwrapInputMethodService(Context context) {
        Context current = context;
        while (current instanceof ContextWrapper) {
            if (current instanceof InputMethodService) {
                return (InputMethodService) current;
            }
            current = ((ContextWrapper) current).getBaseContext();
        }
        return current instanceof InputMethodService ? (InputMethodService) current : null;
    }

    private static GboardRoundedKeyboardConfig readConfigOrNull() {
        SharedPreferences preferences = GboardFlagRuntimeContext.preferencesOrNull();
        return GboardRoundedKeyboardSettings.readSnapshotOrNull(preferences);
    }
}
