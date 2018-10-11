import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Game extends JPanel {

    private Dimension gameField = new Dimension(400,300);
    private boolean isRunning = false;
    private boolean isPaused = false;
    private boolean won = false;
    private boolean lost = false;
    public boolean powerOn = false;

    private int ballCount = 3;
    public static int level = 1;

    private Platform[][] platforms;
    private Paddle paddle;
    private Ball ball;
    private Shield shield = new Shield(this);
    private boolean shieldOn = false;
    private int score;
    private boolean paddleRight;
    private boolean paddleLeft; //else no button, vector = 0.

    private int platformsX = 10;
    private int platformsY = 3;
    private int fraction = 4;
    private ArrayList<Power> powerups = new ArrayList<Power>();
    private Color transparent = new Color(1f,0f,0f,0f);

    public Game(Frame container) {
        container.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (won || lost) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) System.exit(0);
                }
                else if (isRunning && (!isPaused)) {
                    if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        paddleRight = true;
                        paddleLeft = false;
                    }
                    if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                        paddleLeft = true;
                        paddleRight = false;
                    }

                }
                else {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER) start();
                }
            }
            public void keyReleased(KeyEvent e) {
                paddle.stop();
            }
        });
        platforms = new Platform[platformsX][platformsY];
        for (int i = 0; i < platforms.length; i++) {
            for (int j = 0; j < platforms[0].length; j++) {
                int pWidth = gameField.width/platformsX;
                int pHeight = (gameField.height/fraction)/platformsY;
                platforms[i][j] = new Platform(i*pWidth, j*pHeight, pWidth, pHeight);
            }
        }
        paddle = new Paddle(this, (int)((gameField.getWidth() - Paddle.swidth)/2), (int)(gameField.getHeight() - Paddle.sheight), Paddle.swidth, Paddle.sheight);
        ball = new Ball(this, gameField.width/2, gameField.height/2, Ball.sradius);
        score = 0;
    }

    public void loseBall() {
        pause();
        ballCount--;
        if (ballCount <= 0) lost = true;
        ball.setVector(6,6);
        paddle.setVector(0,0);
        ball.setPosition(gameField.width/2, gameField.height/2);
        paddle.setX((int)((gameField.getWidth() - Paddle.swidth)/2));
        paddle.setY(gameField.height - Paddle.sheight);
        powerups.clear();
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

    public int getBallCount(){
        return ballCount;
    }

    public void setBallCount(int val){
        ballCount = val;
    }

    public Dimension getGameDimension() {
        return gameField;
    }

    public ArrayList<Power> getpower() {
        return powerups;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void setPower(Power pow) {
        powerOn = true;
        powerups.add(pow);
    }


    public Paddle getPaddle() {
        return paddle;
    }

    public boolean getPaddleRight(){
        return paddleRight;
    }

    public boolean getPaddleLeft(){
        return paddleLeft;
    }

    public void setPaddleRight(boolean x){
        paddleRight = x;
    }

    public void setPaddleLeft(boolean x){
        paddleLeft = x;
    }
    
    public Ball getBall() {
        return ball;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int x) {
        score = x;
    }

    public Platform[][] getPlatforms() {
        return this.platforms;
    }

    private static int numDestroyed;
    public int getPlatformSize(){
        numDestroyed = 0;
        for(Platform[] ps : platforms){
            for(Platform p : ps){
                if(p.isDestroyed())
                    numDestroyed++;
            }
        }
        return (platforms.length * platforms[0].length) - numDestroyed;
    }

    public int getBlocksDestroyed(){
        return platforms.length * platforms[0].length - getPlatformSize();
    }

    public void setShield(boolean x){
        shieldOn = x;
    }
    public boolean getShield(){
        return shieldOn;
    }

    public void changePlatform(int i, int j) {
        int pWidth = gameField.width/platformsX;
        int pHeight = (gameField.height/fraction)/platformsY;
        platforms[i][j] = new Platform(i*pWidth, j*pHeight, pWidth, pHeight);
        if((platforms[i][j]).getColor().equals(transparent)) {
            changePlatform(i,j);
        }
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

    public Thread gameThread = new Thread(new Runnable() {
        public void run() {
            isRunning = true;
            ball.setVector(6,6);
            paddle.setVector(0,0);
            while (isRunning) {
                if (!isPaused) {
                    ball.tick();
                    paddle.tick();
                    Iterator<Power> powiter = powerups.iterator();
                    while (powiter.hasNext()) {
                        if (powerups.size() >= 1) {
                            Power pow = powiter.next();
                            boolean ret = pow.tick(); // checks if powerup hits the bottom
                            if (ret) {
                                powiter.remove();
                            }
                        }
                    }
                }
                repaint();
                try {
                        Thread.sleep(30);
                    } catch (Exception e) {}
            }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {}
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
        for(Power pow: powerups) {
            pow.render(g);
        }
        
        if(shieldOn) shield.render(g);

        for (Platform[] ps : platforms) {
            for (Platform p : ps) {
                p.render(g);
            }
        }

        g.setColor(new Color(0,0,0));
        g.drawString("Score: "+score, 600, -9);


        g.setColor(new Color(0,0,0));
        g.drawString("Level: " + level, gameField.width - 100, -25);

        g.setColor(Color.black);
        g.drawRect(0,0,gameField.width,gameField.height);

        if (won) {
            pause();
            level++;
            g.drawString("Press enter to go to next level", gameField.width/2, gameField.height/2);
            won = false;
            ball.setVector(6,6);
            paddle.setVector(0,0);
            ball.setPosition(gameField.width/2, gameField.height/2);
            paddle.setX((int)((gameField.getWidth() - Paddle.swidth)/2));
            paddle.setY(gameField.height - Paddle.sheight);
            platformsY++;
            if (fraction > 2) {
                fraction--;
            }
            platforms = new Platform[platformsX][platformsY];
            for (int i = 0; i < platforms.length; i++) {
                for (int j = 0; j < platforms[0].length; j++) {
                    int pWidth = gameField.width/platformsX;
                    int pHeight = (gameField.height/fraction)/platformsY;
                    platforms[i][j] = new Platform(i*pWidth, j*pHeight, pWidth, pHeight);
                }
            }
            ballCount++;
        }

        if (lost) {
            g.drawString("You lost!", gameField.width/2, gameField.height/2);
            isRunning = false;
        }
    }

} 
