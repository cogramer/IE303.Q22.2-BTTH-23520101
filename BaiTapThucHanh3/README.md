MSSV: 23520101
Tên SV: Huỳnh Khánh Bảo

Bài thực hành 03

YÊU CẦU:
- Cài JDK 21 hoặc mới hơn
- Tải JavaFX SDK tại: https://openjfx.io/

Sau khi tải JavaFX:
Ví dụ thư mục JavaFX:

D:\javafx-sdk-21.0.11

----------------------------------------

CẤU TRÚC THƯ MỤC:

Main.java
img1.png
img2.png
img3.png
img4.png
img5.png
img6.png

----------------------------------------

COMPILE:

javac --module-path "...\javafx-sdk-21.0.11\lib" --add-modules javafx.controls Main.java

----------------------------------------

RUN:

java --module-path "...\javafx-sdk-21.0.11\lib" --add-modules javafx.controls Main

----------------------------------------

Thay "...\javafx-sdk-21.0.11\lib" bằng đường dẫn folder thực tế trên máy

CHỨC NĂNG:
- Hiển thị danh sách sản phẩm
- Click đổi sản phẩm
- Fade animation
- Hover effect
