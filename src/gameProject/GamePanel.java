package gameProject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements KeyListener{
	
	private final int Panel_height = 500;
	private final int Panel_width = 600;
	
	
	//----------Player Information----------
	private Player player;
	int player_width = 120;
	int player_height = 25;
	int speed = 5;
	
	private Image bgImage;
	
	
	public GamePanel() 
	{
		setPreferredSize(new Dimension(Panel_width, Panel_height));
		
		//----------Background image----------
		try {
			bgImage = new ImageIcon(getClass().getResource("bgimg (2).png")).getImage();
			
		} catch(Exception e) {
			System.out.println("Error loading background image");
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
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//----------Draw background image----------
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bgImage, 0, 0, Panel_width, Panel_height, this);
		player.draw(g2d); 
		
	}
	
	//----------KeyListener Method: Moving Left or Right Key----------
	
	public void keyPressed(KeyEvent e)
	{
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
