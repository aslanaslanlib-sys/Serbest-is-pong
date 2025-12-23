import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener, KeyListener {

    static final int WIDTH = 800;
    static final int HEIGHT = 400;

    int playerY = 150;
    int enemyY = 150;

    final int RACKET_WIDTH = 10;
    final int RACKET_HEIGHT = 80;

    int bombX = WIDTH / 2;
    int bombY = HEIGHT / 2;
    int bombSize = 15;
    int bombSpeedX = 4;
    int bombSpeedY = 3;

    String lastHit = "none";

    boolean up, down;
    Timer timer;

    public Main() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(30, playerY, RACKET_WIDTH, RACKET_HEIGHT);
        g.fillRect(WIDTH - 40, enemyY, RACKET_WIDTH, RACKET_HEIGHT);

        g.setColor(Color.RED);
        g.fillOval(bombX, bombY, bombSize, bombSize);

        g.setColor(Color.GREEN);
        g.drawString("Bomb Pong 💣", WIDTH / 2 - 40, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (up && playerY > 0) playerY -= 5;
        if (down && playerY < HEIGHT - RACKET_HEIGHT) playerY += 5;

        if (enemyY + RACKET_HEIGHT / 2 < bombY) enemyY += 3;
        if (enemyY + RACKET_HEIGHT / 2 > bombY) enemyY -= 3;

        bombX += bombSpeedX;
        bombY += bombSpeedY;

        if (bombY <= 0 || bombY >= HEIGHT - bombSize) {
            bombSpeedY *= -1;
        }

        if (bombX <= 40 &&
                bombY + bombSize >= playerY &&
                bombY <= playerY + RACKET_HEIGHT) {

            bombSpeedX *= -1;
            lastHit = "player";
        }

        if (bombX >= WIDTH - 55 &&
                bombY + bombSize >= enemyY &&
                bombY <= enemyY + RACKET_HEIGHT) {

            bombSpeedX *= -1;
            lastHit = "enemy";
        }

        if (bombX < 0 || bombX > WIDTH) {
            timer.stop();
            String msg = lastHit.equals("player")
                    ? "SƏN UDUZDUN 💥"
                    : "RƏQİB UDUZDU 💥";
            JOptionPane.showMessageDialog(this, msg);
            System.exit(0);
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = true;
        if (e.getKeyCode() == KeyEvent.VK_S) down = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = false;
        if (e.getKeyCode() == KeyEvent.VK_S) down = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bomb Pong 💣");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Main());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
