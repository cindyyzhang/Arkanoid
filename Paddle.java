import java.awt.*;

public class Paddle {
	
	public static int swidth = 90;
	public static int sheight = 20;
	public Rectangle hitBox;
	private Game instance;
	private Dimension vector = new Dimension(0,0);
	private Point pos = new Point(0,0);
	private static int speed = 12;
	private boolean stop = false;

	public Paddle(Game inst, int x, int y, int width, int height) {
		instance = inst;
		hitBox = new Rectangle(x, y, width, height);
	}

	public void moveOnX(int speed) {
		hitBox.x += speed;
		if (hitBox.x < 0) {
			hitBox.x = 0;
		}
		if (hitBox.x > instance.getGameDimension().width - instance.getPaddle().hitBox.width) {
			hitBox.x = instance.getGameDimension().width - instance.getPaddle().hitBox.width;
		}
	}

	public void setVector(int xMove, int yMove) {
		vector = new Dimension(xMove, yMove);
	}
	
	public Dimension getVector() {
		return vector;
	}

	public Point getPosition() {
		return pos;
	}

	public void setPosition(int x, int y) {
		pos = new Point(x,y);
	}

	public void stop(){
		stop = true;
	}

	public void tick() {
		
		if(instance.getPaddleRight()){
			stop = false;
			if(vector.width == 0){
				vector.width = speed;
			}
			if(vector.width == -speed)
				vector.width += speed*2;
			if(vector.width == speed)
				vector.width += 0;
			instance.setPaddleRight(false);
		}
		else if(instance.getPaddleLeft()){
			stop = false;
			if(vector.width == 0)
				vector.width -= speed;
			if(vector.width == -speed)
				vector.width += 0;
			if(vector.width == speed)
				vector.width -= speed*2;
			instance.setPaddleLeft(false);
		}
		else if(stop){
			vector.width = 0;
		}

		if (hitBox.x <= 10) {
			hitBox.x = 10;
			instance.setPaddleLeft(false);
		}
		if (hitBox.x >= instance.getGameDimension().width - instance.getPaddle().hitBox.width - 10) {
			hitBox.x = instance.getGameDimension().width - instance.getPaddle().hitBox.width - 10;
			instance.setPaddleRight(false);
		}
		hitBox.x = hitBox.x + vector.width;
	}

	public boolean collidesWith(Rectangle object) {
		return hitBox.intersects(object);
	}

	public void setX(int x) {
		hitBox.x = x;
	}

	public void setY(int y) {
		hitBox.y = y;
	}

	public void render(Graphics g) {
		g.setColor(new Color(200,200,200));
		g.fillRect(hitBox.x,hitBox.y,hitBox.width,hitBox.height);
	}

}