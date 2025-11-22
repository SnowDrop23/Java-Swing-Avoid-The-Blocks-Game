package gameProject;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Avoid The Blocks");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		//----------Create card layout----------
		CardLayout layout = new CardLayout();
		JPanel mainPanel = new JPanel(layout);
		
		///
		GamePanel game_panel = new GamePanel();
		MenuPanel menu_panel = new MenuPanel(layout, mainPanel, game_panel);
		
		
		mainPanel.add(menu_panel, "Menu");
		mainPanel.add(game_panel, "Game");
		
		frame.add(mainPanel);
		frame.pack();
		frame.setVisible(true);
	}

}
