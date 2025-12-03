package gameProject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Block {
	
	private int x, y;
	private int width, height;
	private int speedY;
	
	private Color color;
	//
	public Block(int x, int y, int width, int height, int speedY)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speedY = speedY;
		
		Color c;
		do {
			c = new Color (random.nextInt(256), random.nextInt(256), random.nextInt(256));
		} while(c.equals(Color.CYAN));
		this.color = c;
		
	}
	
	
	public void update()
	{
		y += speedY;
	}
	
	public boolean isOffScreen(int panel_height)
	{
		return y > panel_height;
	}
	
	
	//-------Blocks appears in random color----------
	Random random = new Random();
	
	
	
	
	public void draw(Graphics2D g2d)
	{
		
		g2d.setColor(color);
		g2d.fillRect(x, y, width, height);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	

}
