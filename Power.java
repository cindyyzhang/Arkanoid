import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

import javax.swing.*; 
import java.io.*; 
import java.awt.*; 
import java.awt.image.*; 
import javax.imageio.*; 

public class Power {
	
	public static int sradius = 9;
	private Game instance;
	private Dimension vector = new Dimension(0,0);
	private Point pos = new Point(0,0);
	private int radius;
	private Color color;
	public BufferedImage image;
	public static int origX, origY;

	public Power(Game inst, Color col, int x, int y, int radius) {
		instance = inst;
		pos = new Point(x,y);
		this.radius = radius;
		color = col;
		if(this.color.equals(pink)){
			try 
	    	{                
	       		 image = ImageIO.read(new File("heart.png")); 
	   		} 
	    	catch (IOException e) 
	    	{ 
	        	//Not handled. 
	    	}     	
	    }
	    if(this.color.equals(black)){
			try 
	    	{                
	       		 image = ImageIO.read(new File("bomb.png")); 
	   		} 
	    	catch (IOException e) 
	    	{ 
	        	//Not handled. 
	    	}     	
	    }
	    if(this.color.equals(green)){
			try 
	    	{                
	       		 image = ImageIO.read(new File("powerup.png")); 
	   		} 
	    	catch (IOException e) 
	    	{ 
	        	//Not handled. 
	    	}     	
	    }
	    if(this.color.equals(blue)){
			try 
	    	{                
	       		 image = ImageIO.read(new File("shield.png")); 
	   		} 
	    	catch (IOException e) 
	    	{ 
	        	//Not handled. 
	    	}     	
	    }
	    if(this.color.equals(red)){
			try 
	    	{                
	       		 image = ImageIO.read(new File("powerdown.png")); 
	   		} 
	    	catch (IOException e) 
	    	{ 
	        	//Not handled. 
	    	}     	
	    }
	}

	public void setVector(int xMove, int yMove) {
		vector = new Dimension(xMove, yMove);
	}

	public Point getPosition() {
		return pos;
	}

	public void setPosition(int x, int y) {
		pos = new Point(x,y);
	}

	public Color getColor(){
		return color;
	}

	Timer timer = new Timer();

	public boolean tick() { //like move, returns whether powerup hits bottom
		setVector(0, 4);
		if (instance.getPaddle() != null) {
			if (instance.getPaddle().collidesWith(new Rectangle(pos.x - radius + vector.width, pos.y - radius + vector.height, radius*2, radius*2))) {
				if(color.equals(green)) {
					instance.getPaddle().hitBox.width += 40; //increase paddle length

					if(instance.getBall().getVector().width > 4 || instance.getBall().getVector().width < -4){
						instance.getBall().getVector().setSize(instance.getBall().getVector().width*2.0/3.0, instance.getBall().getVector().height*2.0/3.0);
					}

					new java.util.Timer().schedule( 
        				new java.util.TimerTask() {
            				@Override
            				public void run() {
               					instance.getPaddle().hitBox.width -= 40;
               					if(instance.getBall().getVector().width < 6 && instance.getBall().getVector().width > -6){
               						instance.getBall().getVector().setSize(instance.getBall().getVector().width*3.0/2.0, instance.getBall().getVector().height*3.0/2.0);
								}
           					}
       					 }, 
       					 5000 
					);
					return true;
				
				}	
				if(color.equals(black)) {
					(instance.getBall()).setVector(0,10);
					(instance.getBall()).setPosition(0,instance.getGameDimension().height);
				}

				if(color.equals(pink)){
					instance.setBallCount(instance.getBallCount() + 1);
					return true;
				}

				if(color.equals(blue)){
					instance.setShield(true);
					new java.util.Timer().schedule( 
        				new java.util.TimerTask() {
            				@Override
            				public void run() {
               					instance.setShield(false);
           					}
       					}, 
       					4000 
					);
					return true;
				}

				if(color.equals(red)) {
					instance.getPaddle().hitBox.width -= 30; //decreases paddle length

					if(instance.getBall().getVector().width < 9 && instance.getBall().getVector().width > -9){
						instance.getBall().getVector().setSize(instance.getBall().getVector().width*3.0/2.0, instance.getBall().getVector().height*3.0/2.0);
					}

					new java.util.Timer().schedule( 
        				new java.util.TimerTask() {
            				@Override
            				public void run() {
               					instance.getPaddle().hitBox.width += 30;
               					if(instance.getBall().getVector().width > 6 || instance.getBall().getVector().width < -6){
               						instance.getBall().getVector().setSize(instance.getBall().getVector().width*2.0/3.0, instance.getBall().getVector().height*2.0/3.0);
								}
           					}
       					 }, 
       					 5000 
					);
					return true;
				}
			}
		}
		pos.move(pos.x + vector.width, pos.y + vector.height);
		if (pos.y + radius >= instance.getGameDimension().height && vector.height > 0) {
		 	return true;
		}
		return false;
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
        g.drawImage(image, pos.x, pos.y, null); 
	}
}