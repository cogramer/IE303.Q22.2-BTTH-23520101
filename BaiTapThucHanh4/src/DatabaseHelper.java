import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:shoestore.db";
    private Connection conn;

    // ========== KẾT NỐI ==========
    public void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(DB_URL);
        System.out.println("Ket noi SQLite thanh cong.");
        initDatabase();
    }

    // ========== TẠO BẢNG + INSERT DỮ LIỆU MẪU ==========
    private void initDatabase() throws SQLException {
        String createTable = """
            CREATE TABLE IF NOT EXISTS products (
                id    INTEGER PRIMARY KEY AUTOINCREMENT,
                name  TEXT NOT NULL,
                price TEXT NOT NULL,
                brand TEXT NOT NULL,
                image TEXT NOT NULL
            );
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
        }

        // Chỉ insert nếu bảng rỗng
        if (countProducts() == 0) {
            String[][] data = {
                {"4DFWD PULSE SHOES", "$160.00", "Adidas", "img1.png"},
                {"FORUM MID SHOES",   "$100.00", "Adidas", "img2.png"},
                {"SUPERNOVA SHOES",   "$150.00", "Adidas", "img3.png"},
                {"NMD CITY STOCK 2",  "$160.00", "Adidas", "img4.png"},
                {"RUNNER PRO",        "$120.00", "Adidas", "img5.png"},
                {"4DFWD ORANGE",      "$160.00", "Adidas", "img6.png"}
            };
            for (String[] row : data) {
                insertProduct(row[0], row[1], row[2], row[3]);
            }
            System.out.println("Da insert du lieu mau.");
        }
    }

    // ========== ĐẾM SỐ DÒNG ==========
    private int countProducts() throws SQLException {
        String sql = "SELECT COUNT(*) FROM products";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // ========== LẤY TẤT CẢ SẢN PHẨM ==========
    public List<Main.Product> getAllProducts() throws SQLException {
        List<Main.Product> list = new ArrayList<>();
        String sql = "SELECT name, price, brand, image FROM products ORDER BY id";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Main.Product(
                    rs.getString("name"),
                    rs.getString("price"),
                    rs.getString("brand"),
                    rs.getString("image")
                ));
            }
        }
        return list;
    }

    // ========== TÌM KIẾM THEO TÊN ==========
    public List<Main.Product> searchByName(String keyword) throws SQLException {
        List<Main.Product> list = new ArrayList<>();
        String sql = "SELECT name, price, brand, image FROM products WHERE name LIKE ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Main.Product(
                    rs.getString("name"),
                    rs.getString("price"),
                    rs.getString("brand"),
                    rs.getString("image")
                ));
            }
        }
        return list;
    }

    // ========== THÊM SẢN PHẨM ==========
    public void insertProduct(String name, String price,
                               String brand, String image) throws SQLException {
        String sql = "INSERT INTO products (name, price, brand, image) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, price);
            pstmt.setString(3, brand);
            pstmt.setString(4, image);
            pstmt.executeUpdate();
        }
    }

    // ========== CẬP NHẬT GIÁ ==========
    public void updatePrice(int id, String newPrice) throws SQLException {
        String sql = "UPDATE products SET price = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPrice);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    // ========== XÓA SẢN PHẨM ==========
    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // ========== ĐÓNG KẾT NỐI ==========
    public void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Da dong ket noi.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}