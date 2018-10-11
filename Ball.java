import java.awt.*;

public class Ball {
	
	public static int sradius = 9;
	private Game instance;
	private Dimension vector = new Dimension(0,0);
	private Point pos = new Point(0,0);
	private int radius;
	private Power pow;

	public Ball(Game inst, int x, int y, int radius) {
		instance = inst;
		pos = new Point(x,y);
		this.radius = radius;
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

	public void tick() {
		if (pos.x - radius <= 0 && vector.width < 0) vector.width *= -1;
		if (pos.x + radius >= instance.getGameDimension().width && vector.width > 0) vector.width *= -1;
		if (pos.y - radius <= 0 && vector.height < 0) vector.height *= -1;
		if (pos.y + radius + 12 >= instance.getGameDimension().height && vector.height > 0){ 
			if(instance.getShield() == true) vector.height *= -1;
		}
		if (pos.y + radius >= instance.getGameDimension().height && vector.height > 0){ 
			if(instance.getShield() == true) vector.height *= -1;
			else instance.loseBall();
		}
		if (instance.getPaddle() != null) {
			if (instance.getPaddle().collidesWith(new Rectangle(pos.x - radius + vector.width, pos.y - radius + vector.height, radius*2, radius*2))) {
				vector.height *= -1;
			}
		}
		pos.move(pos.x + vector.width, pos.y + vector.height);

		for (int i = 0; i < (instance.getPlatforms()).length; i++) {
            for (int j = 0; j < (instance.getPlatforms())[0].length; j++) {
            	Platform p = (instance.getPlatforms())[i][j];
                if (p.collidesWith(new Rectangle(pos.x - radius, pos.y - radius, radius, radius))) {
                	if(p.getColor().equals(green) || p.getColor().equals(red) || p.getColor().equals(black) || p.getColor().equals(blue) ||  p.getColor().equals(pink)) {
                		pow = new Power (instance, p.getColor(), p.hitBox.x, p.hitBox.y, 20);
                		instance.setPower(pow);
                	}

                	if(!p.getColor().equals(transparent)) {
                		p.destroy();
                		if(p.isScoreAdded() == false){
                			instance.setScore(instance.getBlocksDestroyed() * 5);
                			p.setScoreAdded(); //sets true
                		}
                	}

					if(p.getColor().equals(transparent)) {
                		instance.changePlatform(i,j);
                	}

                	vector.height *= -1;
                	boolean won = true;
                	for (Platform[] pls : instance.getPlatforms()) {
                		for (Platform pl : pls) {
                			if (!pl.isDestroyed()) won = false;
                		}
                	}
                	if (won) instance.playerWon();
                }
            }
        }
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

	public void render(Graphics g) {
		g.setColor(new Color(0,0,0));
		g.fillOval(pos.x - radius, pos.y - radius, radius*2, radius*2);
	}

}