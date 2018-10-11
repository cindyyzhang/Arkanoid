import java.awt.*;
public class Platform {
	
	public Rectangle hitBox;
	private boolean isDestroyed = false;
	private boolean scoreAdded = false;

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

	public boolean isScoreAdded(){
        return scoreAdded;
    }

    public void setScoreAdded(){
    	scoreAdded = true;
    }
	
	private Color green = new Color(113,196,137);
	private Color red = new Color(244,117,102);
	private Color blue = new Color(136,176,239); 
	private Color purple = new Color(165,116,224); 
	private Color orange = new Color(247,200,136);
	private Color pink = new Color(237,166,233);
	private Color aqua = new Color(144,237,223);
	private Color yellow = new Color(239,255,168);
	private Color black = new Color(0,0,0);
	private Color transparent = new Color(1f,0f,0f,0f);

	private Color[] choices = new Color[] {red, green, pink, blue, black, purple, purple, orange, orange, aqua, aqua, yellow, transparent};
	private int choosecolor = (int)(Math.random()*13);
	private Color color = choices[choosecolor];
	
	public Color getColor() {
		return this.color;
	}

	public void render(Graphics g) {
		g.setColor(choices[choosecolor]);
		if (!isDestroyed) {
			g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
			if (!choices[choosecolor].equals(transparent)) {
				g.setColor(new Color(0,0,0));
				g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
			}
		}
	}

}