import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    // Bài 1: kích thước cố định 360x640
    static final int WIDTH = 360;
    static final int HEIGHT = 640;

    private BufferedImage background;
    private Bird bird;
    private ArrayList<Pipe> pipes;

    private Timer gameTimer;
    private int score = 0;
    private boolean gameOver = false;
    private boolean gameStarted = false;

    private final int PIPE_INTERVAL = 1500; // ms giữa các cặp pipe
    private Timer pipeTimer;

    private final int GAP = 160; // khoảng hở giữa pipe trên và dưới
    private final Random random = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        // Load background
        try {
            background = ImageIO.read(new File("flappybirdbg.png"));
        } catch (Exception e) {
            background = null;
        }

        initGame();
    }

    private void initGame() {
        bird = new Bird(80, HEIGHT / 2 - 12);
        pipes = new ArrayList<>();
        score = 0;
        gameOver = false;
        gameStarted = false;

        // Game loop ~60fps
        if (gameTimer != null) gameTimer.stop();
        gameTimer = new Timer(1000 / 60, this);
        gameTimer.start();

        // Pipe spawn timer
        if (pipeTimer != null) pipeTimer.stop();
        pipeTimer = new Timer(PIPE_INTERVAL, e -> spawnPipe());
    }

    private void spawnPipe() {
        // Chiều cao pipe trên ngẫu nhiên từ 80 đến HEIGHT - GAP - 80
        int topHeight = 80 + random.nextInt(HEIGHT - GAP - 160);
        int bottomY = topHeight + GAP;
        int bottomHeight = HEIGHT - bottomY;

        pipes.add(new Pipe(WIDTH, 0, topHeight, true));
        pipes.add(new Pipe(WIDTH, bottomY, bottomHeight, false));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted && !gameOver) {
            bird.update();

            // Cập nhật pipes
            ArrayList<Pipe> toRemove = new ArrayList<>();
            for (Pipe pipe : pipes) {
                pipe.update();
                if (pipe.isOffScreen()) {
                    toRemove.add(pipe);
                }

                // Va chạm
                if (pipe.getBounds().intersects(bird.getBounds())) {
                    gameOver = true;
                    pipeTimer.stop();
                }

                // Tính điểm: chim vượt qua pipe trên
                if (!pipe.passed && pipe.x + pipe.width < bird.x) {
                    pipe.passed = true;
                    // Mỗi cặp pipe = 1 điểm → chỉ tính pipe trên (isTop)
                    // Mỗi pipe vượt qua += 0.5 => 2 pipe = 1 điểm
                    score++;
                }
            }
            pipes.removeAll(toRemove);

            // Rơi ra khỏi màn hình
            if (bird.y + bird.height >= HEIGHT || bird.y <= 0) {
                gameOver = true;
                pipeTimer.stop();
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ background
        if (background != null) {
            g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
        } else {
            g.setColor(new Color(135, 206, 235));
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }

        // Vẽ pipes
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }

        // Vẽ bird
        bird.draw(g);

        // Vẽ điểm
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        String scoreText = String.valueOf(score / 2); // mỗi cặp = 1 điểm
        FontMetrics fm = g.getFontMetrics();
        g.drawString(scoreText, WIDTH / 2 - fm.stringWidth(scoreText) / 2, 60);

        // Màn hình chờ bắt đầu
        if (!gameStarted && !gameOver) {
            drawCenteredMessage(g, "Nhấn SPACE để bắt đầu", WIDTH / 2, HEIGHT / 2 + 60, 18);
        }

        // Game over
        if (gameOver) {
            drawGameOver(g);
        }
    }

    private void drawCenteredMessage(Graphics g, String msg, int cx, int cy, int fontSize) {
        g.setFont(new Font("Arial", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int tw = fm.stringWidth(msg);

        // Shadow
        g.setColor(new Color(0, 0, 0, 120));
        g.drawString(msg, cx - tw / 2 + 2, cy + 2);

        g.setColor(Color.WHITE);
        g.drawString(msg, cx - tw / 2, cy);
    }

    private void drawGameOver(Graphics g) {
        // Overlay mờ
        g.setColor(new Color(0, 0, 0, 140));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics fm = g.getFontMetrics();
        String go = "GAME OVER";
        g.drawString(go, WIDTH / 2 - fm.stringWidth(go) / 2, HEIGHT / 2 - 40);

        g.setFont(new Font("Arial", Font.BOLD, 24));
        fm = g.getFontMetrics();
        String sc = "Điểm: " + score / 2;
        g.drawString(sc, WIDTH / 2 - fm.stringWidth(sc) / 2, HEIGHT / 2 + 10);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        fm = g.getFontMetrics();
        String restart = "Nhấn SPACE / ENTER để chơi lại";
        g.drawString(restart, WIDTH / 2 - fm.stringWidth(restart) / 2, HEIGHT / 2 + 60);
    }

    // ===== KeyListener =====

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER) {
            if (gameOver) {
                // Restart
                initGame();
            } else if (!gameStarted) {
                // Bắt đầu game
                gameStarted = true;
                pipeTimer.start();
                bird.jump();
            } else {
                // Nhảy
                bird.jump();
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}