import java.awt.*;

public class Paddle {
	
	public static int swidth = 90;
	public static int sheight = 20;
	private Rectangle hitBox;
	private Game instance;

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