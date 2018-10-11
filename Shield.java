import java.awt.*;

public class Shield {
	
	public Rectangle hitBox;
	private Game instance;

	public Shield (Game inst) {
		instance = inst;
		hitBox = new Rectangle(0, 489, 701, 12);
	}

	public boolean collidesWith(Rectangle object) {
		return hitBox.intersects(object);
	}

	public void render(Graphics g) {
		g.setColor(new Color(136,176,239));
		g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
	}

}