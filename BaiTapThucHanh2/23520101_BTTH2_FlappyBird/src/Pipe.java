import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Pipe {
    public int x, y;
    public int width = 64;
    public int height;
    public boolean passed = false;
    private boolean isTop;

    private BufferedImage topImg, bottomImg;
    private static final int SPEED = 4;

    public Pipe(int x, int y, int height, boolean isTop) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.isTop = isTop;

        try {
            topImg = ImageIO.read(new File("toppipe.png"));
            bottomImg = ImageIO.read(new File("bottompipe.png"));
        } catch (Exception e) {
            topImg = null;
            bottomImg = null;
        }
    }

    public void update() {
        x -= SPEED;
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        if (isTop) {
            if (topImg != null)
                g.drawImage(topImg, x, y, width, height, null);
            else {
                g.setColor(Color.GREEN);
                g.fillRect(x, y, width, height);
            }
        } else {
            if (bottomImg != null)
                g.drawImage(bottomImg, x, y, width, height, null);
            else {
                g.setColor(Color.GREEN);
                g.fillRect(x, y, width, height);
            }
        }
    }
}
