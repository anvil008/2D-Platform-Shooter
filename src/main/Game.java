package main;

import javax.swing.JFrame;



public class Game {
	
	public static void main(String[] args) {
		
		// this class creates the JFrame window for the game,
		// and sets the contentpane for the gamepanel
		JFrame window = new JFrame("The Patriots");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	
		
		
	}

}
