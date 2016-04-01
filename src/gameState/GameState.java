package gameState;

// gamestate class not useable itself
// extend off it - its subclasses
public abstract class GameState {

	// reference to gameStateManager
	//so it can change states and call setstate
	protected GameStateManager gsm;

	// 5 functions that can now be referenced from the different states that are/will be created
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);

}
