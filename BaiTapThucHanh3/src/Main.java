import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    // Khu vực hiển thị sản phẩm lớn bên trái
    private ImageView mainImage;
    private Label mainName;
    private Label mainPrice;
    private Label mainDescription;

    // Dữ liệu sản phẩm
    private final Product[] products = {
            new Product("4DFWD PULSE SHOES", "$160.00", "Adidas", "img1.png"),
            new Product("FORUM MID SHOES", "$100.00", "Adidas", "img2.png"),
            new Product("SUPERNOVA SHOES", "$150.00", "Adidas", "img3.png"),
            new Product("NMD CITY STOCK 2", "$160.00", "Adidas", "img4.png"),
            new Product("RUNNER PRO", "$120.00", "Adidas", "img5.png"),
            new Product("4DFWD ORANGE", "$160.00", "Adidas", "img6.png")
    };

    private final ArrayList<VBox> allCards = new ArrayList<>();

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ECECEC;");

        // ================= LEFT PANEL =================
        VBox leftPanel = createMainDisplay(products[0]);

        // ================= RIGHT PANEL =================
        TilePane productGrid = new TilePane();
        productGrid.setPadding(new Insets(20));
        productGrid.setHgap(15);
        productGrid.setVgap(15);
        productGrid.setPrefColumns(4);

        for (Product p : products) {
            VBox card = createProductCard(p);
            productGrid.getChildren().add(card);
        }

        ScrollPane scrollPane = new ScrollPane(productGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        root.setLeft(leftPanel);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1180, 600);

        stage.setTitle("Shoe Store UI");
        stage.setScene(scene);
        stage.show();
    }

    // ================= MAIN DISPLAY =================
    private VBox createMainDisplay(Product product) {

        mainImage = new ImageView(new Image(product.image));
        mainImage.setFitWidth(280);
        mainImage.setFitHeight(180);
        mainImage.setPreserveRatio(true);

        mainName = new Label(product.name);
        mainName.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        mainName.setTextFill(Color.web("#222"));

        mainPrice = new Label(product.price);
        mainPrice.setFont(Font.font("Arial", FontWeight.BOLD, 26));

        Label brand = new Label(product.brand);
        brand.setFont(Font.font(18));

        mainDescription = new Label(
                "This product is excluded from all promotional discounts and offers."
        );
        mainDescription.setWrapText(true);
        mainDescription.setTextFill(Color.GRAY);
        mainDescription.setFont(Font.font(18));

        VBox left = new VBox(15,
                mainImage,
                mainName,
                mainPrice,
                brand,
                mainDescription
        );

        left.setPadding(new Insets(40, 20, 20, 20));
        left.setPrefWidth(350);

        return left;
    }

    // ================= PRODUCT CARD =================
    private VBox createProductCard(Product product) {

        ImageView imageView = new ImageView(new Image("file:" + product.image));
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        // ===== PRODUCT NAME =====
        Label name = new Label(product.name);
        name.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        name.setStyle("-fx-text-fill: #222222;");

        // ===== BRAND =====
        Label brand = new Label(product.brand);
        brand.setFont(Font.font(14));
        brand.setStyle("-fx-text-fill: #666666;");

        // ===== PRICE =====
        Label price = new Label(product.price);
        price.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        price.setStyle("-fx-text-fill: #222222;");

        // ===== CARD =====
        VBox card = new VBox(10, imageView, name, brand, price);

        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15));
        card.setPrefSize(220, 230);

        // ===== STYLE =====
        String normalStyle =
                "-fx-background-color: #F5F5F5;" +
                "-fx-background-radius: 15;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: transparent;" +
                "-fx-border-width: 2;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 10, 0, 0, 3);";

        String selectedStyle =
                "-fx-background-color: #F5F5F5;" +
                "-fx-background-radius: 15;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: #4A90FF;" +
                "-fx-border-width: 2;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0, 0, 4);";

        card.setStyle(normalStyle);
        allCards.add(card);

        // ===== CLICK EVENT =====
        card.setOnMouseClicked(e -> {

            changeMainProduct(product);

            // reset tất cả card
            for (VBox c : allCards) {
                c.setStyle(normalStyle);
            }

            // highlight card hiện tại
            card.setStyle(selectedStyle);
        });

        // ===== HOVER EFFECT =====
        card.setOnMouseEntered(e -> {
            card.setScaleX(1.03);
            card.setScaleY(1.03);
        });

        card.setOnMouseExited(e -> {
            card.setScaleX(1);
            card.setScaleY(1);

            // nếu không selected thì trở về style bình thường
            if (!card.getStyle().contains("#4A90FF")) {
                card.setStyle(normalStyle);
            }
        });

        return card;
    }

    // ================= CHANGE PRODUCT WITH EFFECT =================
    private void changeMainProduct(Product product) {

        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), mainImage);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(event -> {
            mainImage.setImage(new Image(product.image));
            mainName.setText(product.name);
            mainPrice.setText(product.price);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(250), mainImage);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });

        fadeOut.play();
    }

    // ================= PRODUCT CLASS =================
    static class Product {
        String name;
        String price;
        String brand;
        String image;

        Product(String name, String price, String brand, String image) {
            this.name = name;
            this.price = price;
            this.brand = brand;
            this.image = image;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}