package gameProject;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuPanel extends JPanel {

	private Image bgImage;

	public MenuPanel(CardLayout layout, JPanel parent, GamePanel game_panel) {
		setPreferredSize(new Dimension(600, 500));
		setLayout(null);

		// -----------Loading Background image-----------------
		try {
			ImageIcon icon = new ImageIcon(getClass().getResource("bg5.png"));
			bgImage = icon.getImage();

		} catch (Exception e) {
			System.out.println("Error loading background image");
		}
		
		
		

		// --------Set Title----------
		/*
		 * JLabel title = new JLabel(); title.setFont(new Font("Arial", Font.BOLD, 32));
		 * title.setForeground(Color.CYAN);"Avoid The Blocks" title.setOpaque(true);
		 * title.setBackground(Color.BLACK); title.setBounds(100, 90, 400, 60);
		 * title.setHorizontalAlignment(SwingConstants.CENTER); //centers the text
		 * inside the black bar add(title);
		 */

		// ---------ENTER Button------------------
		JButton enter_btn = new JButton("ENTER");
		enter_btn.setFont(new Font("Arial", Font.BOLD, 22));
		enter_btn.setBackground(Color.CYAN);
		enter_btn.setFocusPainted(false);
		enter_btn.setBorderPainted(false);
		enter_btn.setOpaque(true);
		enter_btn.setBounds(220, 220, 160, 50);
		add(enter_btn);

		// -------------EXIT Button---------------
		JButton exit_btn = new JButton("EXIT");
		exit_btn.setFont(new Font("Arial", Font.BOLD, 22));
		exit_btn.setBackground(new Color(220, 20, 60));
		exit_btn.setFocusPainted(false);
		exit_btn.setBorderPainted(false);
		exit_btn.setOpaque(true);
		exit_btn.setBounds(220, 300, 160, 50);
		add(exit_btn);

		// --------------When user click ENTER -> switch to the game screen------------
		enter_btn.addActionListener(e -> {
			
			game_panel.restartGame();
			layout.show(parent, "Game");
			game_panel.requestFocusInWindow();
		});

		// ----------when user click EXIT -> close the game----------
		exit_btn.addActionListener(e -> {
			System.exit(0);
		});
	}
	


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// -------------Draw background image-----------------
		Graphics2D g2d = (Graphics2D) g;
		if (bgImage != null) {
			g2d.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	


}
