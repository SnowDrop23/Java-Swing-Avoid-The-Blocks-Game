package gameProject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player {
	
	private int x, y, player_width, player_height;
	
	public Player(int x, int y, int player_width, int player_height) 
	{
		this.x = x;
		this.y = y;
		this.player_width = player_width; 
		this.player_height = player_height;
	}

	
	public Rectangle getBounds() {
		return new Rectangle(x, y, player_width, player_height);
	}
	 
	public void moveLeft(int step, int leftBoundary) //step = 8, LB = 0
	{
		x -= step;
		if(x < leftBoundary) x = leftBoundary;
	}
	
	public void moveRight(int step, int rightBoundary) //step = 8, RB = Panel_width
	{
		x += step;
		if(x + player_width > rightBoundary) x = rightBoundary - player_width;
	}

	
	//--------Player panel: Color & Size-------------
	public void draw(Graphics2D g2d)
	{
		g2d.setColor(Color.CYAN);
		g2d.fillRect(x, y, player_width, player_height);
	}
}
