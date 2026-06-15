MSSV: 23520101
Tên SV: Huỳnh Khánh Bảo
Bài thực hành 04
YÊU CẦU:
- Cài JDK 21 hoặc mới hơn
- Tải JavaFX SDK tại: https://openjfx.io/
- Tải SQLite JDBC Driver: https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/sqlite-jdbc-3.36.0.3.jar
Sau khi tải JavaFX và SQLite JDBC:
Ví dụ thư mục JavaFX:
D:\javafx-sdk-21.0.11
----------------------------------------
CẤU TRÚC THƯ MỤC:
src\
    Main.java
    DatabaseHelper.java
    sqlite-jdbc-3.36.0.3.jar
    img1.png
    img2.png
    img3.png
    img4.png
    img5.png
    img6.png
javafx-sdk-21.0.11\
----------------------------------------
COMPILE:
javac --module-path "...\javafx-sdk-21.0.11\lib" --add-modules javafx.controls -cp src\sqlite-jdbc-3.36.0.3.jar src\Main.java src\DatabaseHelper.java -d out
----------------------------------------
COPY ẢNH:
copy src\*.png out\
----------------------------------------
RUN:
java --module-path "...\javafx-sdk-21.0.11\lib" --add-modules javafx.controls -cp "out;src\sqlite-jdbc-3.36.0.3.jar" Main
----------------------------------------
Thay "...\javafx-sdk-21.0.11\lib" bằng đường dẫn folder thực tế trên máy
File CSDL shoestore.db sẽ tự động được tạo khi chạy lần đầu
CHỨC NĂNG:
- Lưu thông tin sản phẩm vào CSDL SQLite
- Truy vấn danh sách sản phẩm từ CSDL khi khởi động
- Hiển thị danh sách sản phẩm
- Click đổi sản phẩm
- Fade animation
- Hover effect