package gameState;

// import the background class from tileMap
import java.awt.Graphics2D;
import tileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;
// extends the gamestate
public class MenuState extends GameState {

	// initialise background
	//new class bg
	private Background bg;

	// initialise currentChoice selected
	private int currentChoice = 0;

	// array to store menu options
	private String[] options = { "start", "options","level select", "quit" };

	// color of title
	private Color titleColor;
    //font for background title
	private Font titleFont;
	//font for options
	private Font font;

	// constructor - get the gamestatemanager reference
	public MenuState(GameStateManager gsm) {
     // reference to gamestatemanager
		this.gsm = gsm;

		// setting up the background
		try {
			// gets background image via browsing resources folder:
			bg = new Background("/Backgrounds/patriotmenu.gif", 1);
			// background vector - if background is to move.
			bg.setVector(0, 0);
            // in this case, RED 
			titleColor = new Color(128, 0, 0);
			// set font of title
			titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            //set font of options
			font = new Font("Arial", Font.PLAIN, 12);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	// implement the abstract methods:
	
	public void init() {
	}

	// implement the abstract method: update
	public void update() {
		// update the background
		bg.update();
	}

	public void draw(Graphics2D g) {
		// draw the background
		bg.draw(g);

		// draw title and position 
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("The Patriots", 80, 70);

		
		// draw the menu options
		g.setFont(font);

		// foreloop to go through options to allow current choice to 
		// be highlighted when currently selected
		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.RED);
			}
			// draws all the options on the panel
			g.drawString(options[i], 145, 140 + i * 15);
		}

	}
        //this function allows menu options to be selected
	// depending on the current menu item that is selected.
	private void select() {
		if (currentChoice == 0) {
			// start - loads up the first level
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if (currentChoice == 1) {
			// options
			
		}
		
		if (currentChoice == 2) {
			//level select
		}
		
		if (currentChoice == 3) {
			// quit
			System.exit(0);
		}
	}

	//handles key events
	public void keyPressed(int k) {
		// when player presses enter, calls select function 
		// and therefore selected the option.
		
		if (k == KeyEvent.VK_ENTER) {
			select();

		}
		
		// when up arrow is pressed, 
		// currentchoice goes up 
		if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		
		// when down arrow is pressed, 
		// currentchoice goes down
		if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice == options.length) {
				currentChoice = 0;
			}
		}

	}
// when key is released, does nothing 
	public void keyReleased(int k) {
	}

}