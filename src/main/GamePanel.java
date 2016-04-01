package main;

import gameState.GameStateManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	// dimensions of game window
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	//possible scaling if needed
	public static final int SCALE = 2;
	
	
	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	
	//image - canvas to draw on
	private BufferedImage image;
	private Graphics2D g;
	
	//import game state manager
	private GameStateManager gsm;
	
	
	// constructor - add scale to height and width of game frame
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
		
	}
	
	
	// when game is done loading, create new thread and start it.
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
			
		}
	}
	
	//this will initialise the image
	
	public void init() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		// initiate the graphics
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		//create new gamestatemanager - gamestatemanager class
		gsm =  new GameStateManager();
		
		
	}
	
	// thread run method
	public void run() {
		// call init
		init();
		
		// three timers for thread
		long start;
		long elapsed;
		long wait;
		
		
		// game loop
		
		while(running) {
			// starts time stamp for thread
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			// time elapsed for thead
			elapsed = System.nanoTime() - start;
			
			// total time waiting for thread
			wait = targetTime - elapsed / 1000000;
			if (wait < 0) wait = 5;
			
			// try catch to wait a specific amount of time
			try {
				Thread.sleep(wait);
			}
			//stack trace for exception shows any potential problems
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	// update the gamestatemanager
	private void update() {
		gsm.update();
	}
	//draw the gamestatemanager
	private void draw() {
		gsm.draw(g);
	}
	
	// gets gamepanels graphics object and draws canvas image - to scale to 
    // stretch out to full screen is necessary.
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT *SCALE, null);
		g2.dispose();
		
	}
	
	// three key functions to allow interaction in game.
	
	public void keyTyped(KeyEvent key) {}
	// gets the 'int' type of code for gamestatemanager
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
	

}
