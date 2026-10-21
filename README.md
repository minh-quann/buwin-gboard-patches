<h1 align="center">BuWin Gboard Patches</h1>

<p align="center">
  <b>Bộ patch tối ưu hóa giao diện và tính năng cao cấp cho Google Keyboard (Gboard) trên nền tảng Morphe Patcher.</b><br>
  <i>Vá bo góc tận gốc ở tầng InputView & Window, triệt tiêu hoàn toàn viền xám trên Messenger, hỗ trợ kính mờ Frosted Glass và bộ công cụ tiện ích mạnh mẽ.</i>
</p>

<p align="center">
  <a href="https://github.com/minh-quann/buwin-gboard-patches/releases/latest"><img alt="Latest release" src="https://img.shields.io/github/v/release/minh-quann/buwin-gboard-patches?display_name=tag&label=Phi%C3%AAn%20b%E1%BA%A3n&style=for-the-badge&color=007AFF"></a>
  <a href="https://github.com/minh-quann/buwin-gboard-patches"><img alt="License" src="https://img.shields.io/badge/License-GPL%20v3.0-blue?style=for-the-badge"></a>
  <a href="https://morphe.software/add-source?github=minh-quann/buwin-gboard-patches"><img alt="Add to Morphe" src="https://img.shields.io/badge/Morphe-Th%C3%AAm%20Ngu%E1%BB%93n-00A8FF?style=for-the-badge"></a>
  <a href="https://github.com/minh-quann/buwin-gboard-patches"><img alt="GitHub stars" src="https://img.shields.io/github/stars/minh-quann/buwin-gboard-patches?style=social"></a>
</p>

---

## 🌟 Điểm nổi bật & Cải tiến độc quyền

### 1. Vá bo góc tận gốc (Root InputView & Window Top-Only Rounding)
* **Khắc phục lỗi gốc**: Ở bản patch gốc, tính năng bo góc chỉ được can thiệp ở tầng theme CSS `.keyboard-base-area.keyboard-outline`. Khi đó chỉ phần thân bàn phím bo góc nhưng view cha phía sau (`InputView` và `Window`) vẫn có nền xám mặc định, dẫn đến việc khi chat trên **Messenger**, **Telegram** hay các ứng dụng nền tối thì ở **2 góc trên bàn phím bị lộ 2 mẩu màu xám rất khó chịu**.
* **Giải pháp vá tận gốc**:
  - Can thiệp trực tiếp ở tầng sâu nhất: `Window` của `InputMethodService` và root `InputView`.
  - Toàn bộ background của Window và DecorView được đưa về chế độ **Trong suốt (`Color.TRANSPARENT`)** hoàn toàn.
  - Sử dụng `ViewOutlineProvider` kết hợp `clipToOutline` ở tầng gốc: Cắt bo chuẩn xác **2 góc trên (Top-only)** với đường cong mềm mại, đáy bàn phím giữ phẳng tuyệt đối không hở mép.
  - Mọi thành phần con (thanh gợi ý, toolbar, thân bàn phím) đều tự động ôm khít theo đường cong bo tròn, hòa nhập 100% với giao diện ứng dụng phía sau.
  - **Mặc định bật sẵn** chế độ chỉ bo góc trên (`Mode.TOP`) với bán kính lý tưởng 32 dp.

### 2. Kính mờ thời gian thực (Frosted Glass)
* Hiệu ứng làm mờ nền xuyên thấu (cross-window background blur) sang trọng trên Android 12+.
* Hỗ trợ tinh chỉnh độ mờ (Blur Radius) và độ trong suốt của theme (Theme Opacity).

### 3. Đồng bộ bộ nhớ tạm qua LAN (Web Clipboard & FTP Server)
* Tích hợp máy chủ Web Clipboard nội bộ: Đồng bộ văn bản copy giữa điện thoại và máy tính trong cùng mạng Wi-Fi nhanh chóng qua trình duyệt web với mã ghép đôi bảo mật.
* Tích hợp FTP Server gọn nhẹ: Truy cập và quản lý tệp trên thiết bị từ máy tính không cần cáp nối.

### 4. Cử chỉ thông minh & Công cụ tiện ích
* **Vuốt xuống để ẩn bàn phím (Swipe down to dismiss)**: Vuốt nhẹ từ thanh gợi ý/toolbar để đóng bàn phím ngay lập tức.
* **Máy tính bỏ túi (Inline Calculator)**: Gõ biểu thức toán học (ví dụ: `25*4+10=`) trực tiếp trong bất kỳ ô nhập liệu nào, kết quả sẽ hiện ngay trên thanh gợi ý.
* **Con trỏ Trackpad**: Giữ phím cách để di chuyển con trỏ chuột chính xác từng ký tự.
* **Tùy biến hàng phím trên cùng (Swipeable Custom Top Row)**: Vuốt ngang hàng phím số/chữ trên cùng để truy cập các ký tự và script tùy chỉnh.

---

## 📋 Danh sách các bản Patch chi tiết

### 🛠️ Tính năng phát triển chuyên sâu (Project-Built)

| Tên Patch | Mô tả chi tiết |
| :--- | :--- |
| **Rounded Keyboard Panel** | Vá tận gốc bo tròn góc bàn phím ở tầng `InputView`/`Window`. Mặc định chỉ bo 2 góc trên (`top-only`), triệt tiêu viền xám Messenger, tùy chỉnh bán kính từ 0–64 dp. |
| **Frosted Glass** | Bật hiệu ứng kính mờ (Background Blur) thời gian thực xuyên qua cửa sổ bàn phím trên Android 12+ hỗ trợ Blur. |
| **Clipboard Enhancements** | Tùy biến bộ nhớ tạm: Tăng thời gian lưu trữ, số lượng mục lưu, số dòng xem trước, nhãn thời gian tạo/hết hạn, chia cột lưới (1–3 cột). |
| **Web Clipboard** | Khởi chạy cổng Web Clipboard nội bộ qua LAN, cho phép trình duyệt máy tính gửi và nhận văn bản từ Gboard. |
| **Swipe Down to Dismiss Keyboard** | Vuốt trượt xuống từ thanh gợi ý hoặc thanh công cụ toolbar để ẩn nhanh bàn phím. |
| **Simple Calculator** | Máy tính mini tự động tính toán các phép toán số học gõ trong văn bản và hiển thị kết quả lên thanh gợi ý. |
| **Custom Theme** | Nhập theme từ tệp ZIP ngoài và tải bộ theme đẹp mắt từ kho lưu trữ Rboard chính thức. |
| **Long-Press Editing Shortcuts** | Thêm phím tắt nhấn giữ cho Chọn tất cả, Hoàn tác, Sao chép, Cắt, Dán, Làm lại trên bàn phím QWERTY. |
| **Toolbar Editing Buttons** | Thêm các nút thao tác văn bản (Chọn tất cả, Sao chép, Cắt, Dán) vào thanh menu toolbar để kéo thả tiện lợi. |
| **FTP Server** | Khởi chạy máy chủ FTP nội bộ trên điện thoại để truyền file qua lại với máy tính. |
| **G Logo on Spacebar** | Hiển thị biểu tượng logo Google (G) trên phím cách và ẩn nhãn ngôn ngữ. |
| **Custom Symbols** | Thêm tab ký tự đặc biệt chuyên dụng và lối tắt truy cập nhanh khi nhấn giữ dấu phẩy. |
| **Backup & Restore** | Sao lưu toàn bộ thiết lập Gboard Patches ra tệp JSON và khôi phục linh hoạt từng module. |

### 🔓 Mở khóa tính năng Flag ẩn (Gboard Feature Unlocks)

| Tên Patch | Mô tả |
| :--- | :--- |
| **AI Writing Tools** | Bật công cụ hỗ trợ viết văn bản bằng AI (Sửa lỗi, Viết lại phong cách) cho mọi ngôn ngữ. |
| **Advanced Voice Typing** | Kích hoạt tính năng nhập giọng nói nâng cao trợ lý ảo kèm tự động chèn dấu câu. |
| **Enable OCR / Scan Text** | Mở tính năng quét văn bản từ camera (OCR) hỗ trợ nhiều bộ ký tự. |
| **Grammar Checker** | Bật tính năng kiểm tra ngữ pháp nâng cao (Grammar Check). |
| **Inline Suggestions** | Bật đề xuất từ thông minh Smart Compose trực tiếp trong dòng văn bản. |
| **Enable cursor trackpad mode** | Bật chế độ rê con trỏ dạng Trackpad khi nhấn giữ phím Space. |
| **Enable split keyboard** | Mở khóa bố cục bàn phím chia đôi (Split Keyboard) cho màn hình lớn và gập. |
| **Use Bluetooth Microphone** | Cho phép sử dụng micro tai nghe Bluetooth khi gõ bằng giọng nói. |
| **Change emoji size** | Bật tùy chọn điều chỉnh kích thước biểu tượng cảm xúc (Emoji Size). |
| **Quick Insert** | Bật bảng chèn nhanh Quick Insert trên thanh công cụ. |

---

## 🚀 Hướng dẫn Cài đặt & Patch APK

### Cách 1: Sử dụng Morphe Manager (Khuyên dùng)
1. Cài đặt ứng dụng **Morphe Manager** trên điện thoại Android của bạn.
2. Mở Morphe Manager $\to$ vào phần **Nguồn (Sources)** $\to$ Thêm nguồn mới:
   ```text
   minh-quann/buwin-gboard-patches
   ```
3. Tải file APK Gboard gốc (khuyên dùng bản phát hành chính thức `18.0.3.x arm64-v8a` từ APKMirror).
4. Chọn Gboard trong mục **Patcher**, tích chọn các patch bạn muốn (đặc biệt là **Rounded Keyboard Panel**).
5. Bấm **Patch**, chờ quá trình hoàn tất và cài đặt APK đã vá.

### Cách 2: Sử dụng Morphe CLI (Dành cho máy tính)
```bash
java -jar morphe-cli.jar patch \
  --patches patches-3.13.0.mpp \
  --out gboard-patched.apk \
  gboard-base.apk
```

---

## ⚙️ Cấu hình Bo góc bàn phím trong Cài đặt Gboard

Sau khi cài đặt bản vá:
1. Mở bàn phím Gboard $\to$ Nhấn vào biểu tượng **Cài đặt (Bánh răng)** $\to$ chọn **Gboard Patches** (nằm ở trên cùng).
2. Tìm đến mục **Rounded Keyboard Panel**:
   - **Bật bo góc bàn phím**: Đã được bật mặc định (`ON`).
   - **Vị trí bo góc (Rounded corners)**: Chọn **Top** (chỉ bo góc trên - khuyến nghị) hoặc **All corners** (cả 4 góc).
   - **Bán kính góc trên (Top radius)**: Điều chỉnh từ `0` đến `64 dp` (mặc định: `32 dp` cho độ cong mềm mại nhất).
3. Mở ứng dụng chat (Messenger, Telegram, Zalo) để chiêm ngưỡng bàn phím bo góc sạch sẽ, không còn bất kỳ viền xám thừa nào!

---

## 📄 Bản quyền & Tuyên bố miễn trừ

* Dự án được phân phối theo giấy phép [GNU General Public License v3.0](LICENSE).
* Gboard là nhãn hiệu thuộc sở hữu của Google LLC. Dự án này không liên kết hoặc được tài trợ bởi Google LLC.
