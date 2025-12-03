package gameProject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements KeyListener{
	
	private final int Panel_height = 500;
	private final int Panel_width = 600;
	private Clip hitClip;   // preloaded collision sound

	
	//----------Player Information----------
	private Player player;
	int player_width = 90;
	int player_height = 20;
	int speed = 5;
	
	private Image bgImage;
	private boolean game_over;
	
	
	// ---------- Blocks & score ----------
	private ArrayList<Block> blocks;
	private Timer game_timer;
	private Timer spawn_timer;
	private Random random;
	private int score;
	

	private int speedLevel = 0;   // increases every 100 score

	public GamePanel() 
	{
		setPreferredSize(new Dimension(Panel_width, Panel_height));
		
		//----------Background image----------
		try {
			bgImage = new ImageIcon(getClass().getResource("bgimg (2).png")).getImage();
			
		} catch(Exception e) {
			System.out.println("Error loading background image");
		}
		
		
		// ----------Load hit sound once----------
		try {
			//System.out.println("Loading hit sound...");
		    AudioInputStream audioInput = AudioSystem.getAudioInputStream(
		            getClass().getResource("sound.wav")
		    );
		    hitClip = AudioSystem.getClip();
		    hitClip.open(audioInput);   // decode + load into memory
		    hitClip.stop();
		    hitClip.setFramePosition(0);
		} catch (Exception e) {
		    System.out.println("Error loading hit sound: " + e.getMessage());
		}

		
		
		int startX = Panel_width/2 - player_width/2;
		int startY = Panel_height - player_height - 20;
		int pw = player_width;
		int ph = player_height;
		player = new Player(startX, startY, pw, ph);
		
		//----------For keylistener----------
		setFocusable(true);
		//requestFocusInWindow();
		addKeyListener(this);
		
		
		//--------Initialize blocks & score-------------
		blocks = new ArrayList<> ();
		random = new Random();
		score = 0;
		game_over = false;
		
		
		//--------Timer that updates blocks & repaint --------------
		game_timer = new Timer (16, e -> {
			if(!game_over) 
			{
				update_blocks();
				repaint();
			}
		});
		//game_timer.start();
		
		//------Timer that spawns new blocks---------
		spawn_timer = new Timer(800, e -> spawn_block());
		//spawn_timer.start();	
		
		
	}
	
	
	
	
	
	//----Initialize random blocks-------
	private void spawn_block() {
		//random width between 40-80
		int w = 40 + random.nextInt(41);
		int h = 15;
		int x = random.nextInt(Panel_width - w);
		int y = -h;
		//int speedY = 3 + random.nextInt(4);
		int baseSpeed = 2 + random.nextInt(4);      
		int speedY = baseSpeed + speedLevel;  
			
		blocks.add(new Block(x, y, w, h, speedY));
	}
	
	
	private void update_blocks() {
		
		Rectangle player_bounds = player.getBounds();
		
		
		for(int i = 0; i < blocks.size(); i++) {
			Block b = blocks.get(i);
			b.update();
				
			//If blocks goes off screen, remove it & add score
			if(b.isOffScreen(Panel_height)) {
				blocks.remove(i);
				i--;
				score += 10;
				
				//----
				if(score % 100 == 0)
				{
					speedLevel++;
				}
				continue;
			}
			
			
			//---- Collison check with player -------
			if(player_bounds.intersects(b.getBounds()))
			{
				playHitSound();
				endGame();
				return;
			}
		}
	}
	
	
	private void endGame() 
	{
		game_over = true;
		game_timer.stop();
		spawn_timer.stop();
		
		int choice = JOptionPane.showOptionDialog (
			this, 
			"Game Over!\nYour score: " + score + "\nPlay again?",
			"Game Over",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.INFORMATION_MESSAGE,
			null,
			new Object[] { "Play Again", "Exit"},
			"Play Again"
		);
		
		if(choice == JOptionPane.YES_OPTION) restartGame();
		else System.exit(0);
	}
	
	//---------Restart Game --------------
	public void restartGame() {
		
		//----------Recreate Player at Initial Position-----------------
		int startX = Panel_width/2 - player_width/2;
		int startY = Panel_height - player_height - 20;
		int pw = player_width;
		int ph = player_height;
		player = new Player(startX, startY, pw, ph);
		
		
		//---------- Reset blocks & score ---------------
		blocks.clear();
		score = 0;
		game_over = false;
		
		//------- restart game --------
		game_timer.start();
		spawn_timer.start();
		requestFocusInWindow();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//----------Draw background image----------
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bgImage, 0, 0, Panel_width, Panel_height, this);
		
		//-----Player----
		player.draw(g2d);
		
		//----blocks----
		for(Block b : blocks)
		{
			b.draw(g2d);
		}
		
		
		//----Score text------
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 18));
		g2d.drawString("Score: " + score, 10, 20);
		
	}
	
	
	private void playHitSound() {
	    if (hitClip == null) return;   // loading failed

	    try {
	        if (hitClip.isRunning()) {
	            hitClip.stop();        // stop if already playing
	        }
	        hitClip.setFramePosition(0); // rewind to start
	        hitClip.start();             // play instantly
	    } catch (Exception e) {
	        System.out.println("Error playing sound: " + e.getMessage());
	    }
	}

	
	//----------KeyListener Method: Moving Left or Right Key----------
	
	public void keyPressed(KeyEvent e)
	{
		if(game_over) return;
		int key = e.getKeyCode();
		int moveStep = 8;
		
		
		if(key == KeyEvent.VK_LEFT) //
		{
			player.moveLeft(moveStep, 0);
		}
		else if(key == KeyEvent.VK_RIGHT)
		{
			player.moveRight(moveStep, Panel_width);
		}
		
		repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent e) { 
		
	}
	@Override 
	public void keyTyped(KeyEvent e) { 
		
	}

}
