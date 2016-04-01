package gameState;

import java.util.ArrayList;

public class GameStateManager {
	
	//create an aray to hold all gamestates
	private GameState[] gameStates;
	
	// currentstate is the index of the gamestate list
	private int currentState;
	
	// signify index of states
	public static final int NUMGAMESTATES = 2;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	
	// constructor - initialise array of gamestates
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		
		// menustate is current state for now
		//level1state for example will be the next state if it is changed
		currentState = MENUSTATE;
		//load state
		loadState(currentState);
	}
		//load the states
		private void loadState(int state) {
			if(state == MENUSTATE)
				gameStates[state] = new MenuState(this);
			if(state == LEVEL1STATE)
				gameStates[state] = new Level1State(this);
			
		}
		// unload the states
		private void unloadState(int state) {
			gameStates[state] = null;
		}
		
	

	// ability of changing states
	public void setState(int state) {
	//when setting the next state, unload current state, then load 
	//next state
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//initialise state that is current
		//gameStates[currentState].init();	
	}
	
	// updates the currentstate
	public void update() {
		try {
		gameStates[currentState].update();
	} catch(Exception e) {}
	}
	// draws the currentstate
	public void draw(java.awt.Graphics2D g) {
		try {
		gameStates[currentState].draw(g);
	} catch(Exception e) {}
	}
	
	// for when keypressed is currentstate
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	//for when keyrealsed is currentstate
public void keyReleased(int k) {
	    gameStates[currentState].keyReleased(k);
	}

}

