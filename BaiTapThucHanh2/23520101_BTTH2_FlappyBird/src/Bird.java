import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Bird {
    public int x, y;
    public int width = 34, height = 24;
    private BufferedImage img;

    private double velocityY = 0;
    private final double gravity = 0.5;
    private final double jumpStrength = -9;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            img = ImageIO.read(new File("Flappybird.png"));
        } catch (Exception e) {
            img = null;
        }
    }

    public void update() {
        velocityY += gravity;
        y += (int) velocityY;
    }

    public void jump() {
        velocityY = jumpStrength;
    }

    public void reset(int startY) {
        y = startY;
        velocityY = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        if (img != null) {
            g.drawImage(img, x, y, width, height, null);
        } else {
            g.setColor(Color.YELLOW);
            g.fillOval(x, y, width, height);
        }
    }
}
