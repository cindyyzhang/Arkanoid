import java.awt.*;
public class Platform {
	
	private Rectangle hitBox;
	private boolean isDestroyed = false;

	public Platform(int x, int y, int width, int height) {
		hitBox = new Rectangle(x,y,width,height);
	}

	public boolean collidesWith(Rectangle object) {
		if (isDestroyed) {
			return false;
		}
		return hitBox.intersects(object);
	}

	public void destroy() {
		isDestroyed = true;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void render(Graphics g) {
		if (!isDestroyed) {
			g.setColor(new Color(144,193,237));
			g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
			g.setColor(new Color(0,0,0));
			g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		}
	}

}