package dev.jason.gboardpatches.extension.roundedkeyboard;

public final class GboardRoundedKeyboardConfig {
    public static final boolean DEFAULT_ENABLED = true;
    public static final Mode DEFAULT_MODE = Mode.TOP;
    public static final int MIN_RADIUS_DP = 0;
    public static final int MAX_RADIUS_DP = 64;
    public static final int DEFAULT_RADIUS_DP = 32;

    public enum Mode {
        ALL("all"),
        TOP("top"),
        BOTTOM("bottom");

        private final String storedValue;

        Mode(String storedValue) {
            this.storedValue = storedValue;
        }

        public String storedValue() {
            return storedValue;
        }

        public static Mode fromStoredValue(String value) {
            if (value != null) {
                for (Mode mode : values()) {
                    if (mode.storedValue.equalsIgnoreCase(value.trim())) {
                        return mode;
                    }
                }
            }
            return null;
        }
    }

    private final boolean enabled;
    private final Mode mode;
    private final int topRadiusDp;
    private final int bottomRadiusDp;

    public GboardRoundedKeyboardConfig(
            boolean enabled, Mode mode, int topRadiusDp, int bottomRadiusDp) {
        this.enabled = enabled;
        this.mode = DEFAULT_MODE;
        this.topRadiusDp = sanitizeRadiusDp(topRadiusDp);
        this.bottomRadiusDp = 0;
    }

    public static GboardRoundedKeyboardConfig defaults() {
        return new GboardRoundedKeyboardConfig(
                DEFAULT_ENABLED, DEFAULT_MODE, DEFAULT_RADIUS_DP, 0);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Mode getMode() {
        return Mode.TOP;
    }

    public int getTopRadiusDp() {
        return topRadiusDp;
    }

    public int getBottomRadiusDp() {
        return 0;
    }

    public static int sanitizeRadiusDp(int value) {
        return Math.max(MIN_RADIUS_DP, Math.min(value, MAX_RADIUS_DP));
    }
}
