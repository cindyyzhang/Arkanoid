import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{

	public static JFrame frame;
	public static Game game;
	boolean startHover = false;
	boolean instructionsHover = false;
	boolean instruct = false;

	//double buffering
	Image dbImage;
	Graphics dbg;

	//Menu Buttons
	Rectangle startButton = new Rectangle(450, 250, 130, 25);
	Rectangle instructionsButton = new Rectangle(450, 300, 130, 25);

	Dimension screenSize = new Dimension(900, 700);

	public Main() {
		this.setTitle("Arkanoid");
		this.setSize(screenSize);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(Color.WHITE);
		//this.addKeyListener(new KeyHandler());
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseHandler());
	}

	public void startGame(){
		dispose();

		frame = new JFrame("Arkanoid");
		frame.setSize(900,700);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);

		game = new Game(frame);
		game.setSize(frame.getSize());
		frame.add(game);
	}

	public static void main(String[] args){
		Main m = new Main();
	}

	@Override
	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		draw(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}

	private Color purple = new Color(220,168,247);
	private Color pink = new Color(239,168,247);
	public void draw(Graphics g){
		//Menu
		g.setFont(new Font("Mistral", Font.PLAIN, 40));
		g.setColor(Color.BLACK);
		g.drawString("Arkanoid", 450, 200);
		if(!startHover)
			g.setColor(purple);
		else
			g.setColor(pink);
		g.fillRect(startButton.x, startButton.y, startButton.width, startButton.height);
		g.setFont(new Font("Sans Serif", Font.BOLD, 14));
		g.setColor(Color.BLACK);
		g.drawString("START GAME", startButton.x+10, startButton.y+17);
		if(!instructionsHover)
			g.setColor(purple);
		else
			g.setColor(pink);
		g.fillRect(instructionsButton.x, instructionsButton.y, instructionsButton.width, instructionsButton.height);
		g.setColor(Color.BLACK);
		g.drawString("INSTRUCTIONS", instructionsButton.x+10, instructionsButton.y+17);

		/*Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);*/
		if(instruct) {
			g.setFont(new Font("Serif", Font.BOLD, 16));
			g.drawString("Instructions", 80, 180);
			g.setFont(new Font("Serif", Font.PLAIN, 15));
			g.drawString("After pressing the start button, press enter to", 80, 200);
			g.drawString("begin ball movement.", 80, 220);
			g.drawString("Use the right and left arrow keys to control", 80, 240);
			g.drawString("the paddle.", 80, 260);
			g.drawString("You begin the game with three lives. If the ball", 80, 280);
			g.drawString("hits the bottom, you lose a life.", 80, 300);
			g.drawString("Each level contains invisible blocks. These blocks", 80, 320);
			g.drawString("will only appear after being hit once and need to", 80, 340);
			g.drawString("be hit twice to disappear.", 80, 360);
			g.drawString("Catching a heart allows you to gain 1 life. Catching", 80, 380);
			g.drawString("a bomb causes you to lose 1 life. Green arrows", 80, 400);
			g.drawString("(power-ups) slow down the ball and extend the paddle,", 80, 420);
			g.drawString("while red arrows (power-downs) speed up the ball and", 80, 440);
			g.drawString("shorten the paddle. Catching a shield prevents the", 80, 460);
			g.drawString("ball from hitting the ground for 4 seconds.", 80, 480);
			g.drawString("A level is cleared once all the blocks on the board", 80, 500);
			g.drawString("have disappeared after being hit.", 80, 520);
		}

		repaint();
	}

	public class MouseHandler extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e){
			int mx = e.getX();
			int my = e.getY();
			if(mx > startButton.x && mx < startButton.x + startButton.width && my > startButton.y && my < startButton.y + startButton.height){
			   	startGame();
			}
			if(mx > instructionsButton.x && mx < instructionsButton.x + instructionsButton.width && my > instructionsButton.y && my < instructionsButton.y + instructionsButton.height){
			   	instruct = true;
			}
		}
		@Override
		public void mouseMoved(MouseEvent e){
			int mx = e.getX();
			int my = e.getY();
			if(mx > startButton.x && mx < startButton.x + startButton.width && my > startButton.y && my < startButton.y + startButton.height){
			   	startHover = true;
			}
			else
				startHover = false;
			if(mx > instructionsButton.x && mx < instructionsButton.x + instructionsButton.width && my > instructionsButton.y && my < instructionsButton.y + instructionsButton.height){
			   	instructionsHover = true;
			}
			else
				instructionsHover = false;
		}	
	}
}