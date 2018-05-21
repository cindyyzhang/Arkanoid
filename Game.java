import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel {

    private Dimension gameField = new Dimension(400,300);
    private boolean isRunning = false;
    private boolean isPaused = false;
    private boolean won = false;
    private boolean lost = false;

    private int ballCount;

    private Platform[][] platforms;
    private Paddle paddle;
    private Ball ball;

    public Game(Frame container, int platformsX, int platformsY) {
        container.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (won || lost) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) System.exit(0);
                }
                else if (isRunning && (!isPaused)) {
                    if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        paddle.moveOnX(30);
                    }
                    if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                        paddle.moveOnX(-30);
                    }
                }
                else {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER) start();
                }
            }
        });
        platforms = new Platform[platformsX][platformsY];
        for (int i = 0; i < platforms.length; i++) {
            for (int j = 0; j < platforms[0].length; j++) {
                int pWidth = gameField.width/platformsX;
                int pHeight = (gameField.height/4)/platformsY;
                platforms[i][j] = new Platform(i*pWidth, j*pHeight, pWidth, pHeight);
            }
        }
        paddle = new Paddle(this, (int)((gameField.getWidth() - Paddle.swidth)/2), (int)(gameField.getHeight() - Paddle.sheight), Paddle.swidth, Paddle.sheight);
        ball = new Ball(this, gameField.width/2, gameField.height/2, Ball.sradius);
        ballCount = 3;
    }

    public void loseBall() {
        pause();
        ballCount--;
        if (ballCount <= 0) lost = true;
        ball.setVector(5,5);
        ball.setPosition(gameField.width/2, gameField.height/2);
        paddle.setX((int)((gameField.getWidth() - Paddle.swidth)/2));
        paddle.setY(gameField.height - Paddle.sheight);
        repaint();
    }

    public void playerWon() {
        won = true;
    }

    public void start() {
        isPaused = false;
        if (!isRunning) gameThread.start();
    }

    public void pause() {
        isPaused = true;
        gameThread.interrupt();
    }

    public Dimension getGameDimension() {
        return gameField;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Platform[][] getPlatforms() {
        return this.platforms;
    }

    public void setSize(Dimension size) {
        super.setSize(size);
        if (!isRunning) {
            gameField = new Dimension(size.width - 200, size.height - 200);
            for (int i = 0; i < platforms.length; i++) {
                for (int j = 0; j < platforms[0].length; j++) {
                    int pWidth = gameField.width/platforms.length;
                    int pHeight = (gameField.height/4)/platforms[0].length;
                    platforms[i][j] = new Platform(i*pWidth, j*pHeight, pWidth, pHeight);
                }
            }
            ball.setPosition(gameField.width/2, gameField.height/2);
            paddle.setX((int)((gameField.getWidth() - Paddle.swidth)/2));
            paddle.setY(gameField.height - Paddle.sheight);
        }
    }

    private Thread gameThread = new Thread(new Runnable() {
        public void run() {
            isRunning = true;
            ball.setVector(5,5);
            while (isRunning) {
                if (!isPaused) {
                    ball.tick();
                    repaint();
                    try {
                        Thread.sleep(30);
                    } catch (Exception e) {}
                }
                try {
                    Thread.sleep((long)0.1);
                } catch (Exception e) {}
            }
        }
    });

    public void paint(Graphics g) {
        super.paint(g);
        g.translate((getWidth()-gameField.width)/2,(getHeight()-gameField.height)/2);
        
        g.setColor(new Color(0,0,0));
        int radius = 4;
        for (int i = 0; i < ballCount; i++) {
            g.fillOval(i*radius*3, -(radius*2+3), radius*2, radius*2);
        }

        g.setColor(Color.white);
        g.fillRect(0,0,gameField.width,gameField.height);

        ball.render(g);
        paddle.render(g);

        for (Platform[] ps : platforms) {
            for (Platform p : ps) {
                p.render(g);
            }
        }

        g.setColor(Color.black);
        g.drawRect(0,0,gameField.width,gameField.height);
        if (won) {
            g.drawString("You won!", gameField.width/2, gameField.height/2);
            isRunning = false;
        }
        if (lost) {
            g.drawString("You lost!", gameField.width/2, gameField.height/2);
            isRunning = false;
        }
    }

} 