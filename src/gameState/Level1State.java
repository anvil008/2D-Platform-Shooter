package gameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Audio.AudioPlayer;

//should import from the XboxController.jar 
import ch.aplu.xboxcontroller.*;

import main.GamePanel;
import tileMap.Background;
import tileMap.TileMap;
import Entities.Foe;
import Entities.Hud;
import Entities.Player;
import Entities.Rebel;
//import Entities.Player;
// extends the GameState as it is  a gamestate class

public class Level1State extends GameState {

	// create the tilemap
	private TileMap tileMap;
	private Background bg;

	// Creates Xbox controller
	XboxController con = new XboxController();

	// create new player
	private Player player;

	private ArrayList<Foe> foes;
	//HUD variable
	private Hud hud;
	
	//get audio
	private AudioPlayer backingmusic;

	// constructor - all gamestates must have gamestate manager
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;

		// call init, where we wil initiase the tilemap
		init();

	}

	// implement the 5 abstract functions: init, update, draw,
	// keypressed and keyreleased.

	// 1.function init
	public void init() {

		// tilemap is initialised.
		// tilesize of 30
		tileMap = new TileMap(30);
		// gets the resource image for the tilemap:
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/newmap.map");
		// sets the starting position of the tilemap
		tileMap.setPosition(0, 0);
		// gets the tilemap background for the resources folder
		bg = new Background("/Backgrounds/Level1Background.gif", 0.1);

		// initialises the player onto the tilemap
		player = new Player(tileMap);
		// set position of player when starting
		player.setPosition(100, 150);

		//initialise the foes
		populatefoes();
		
		//gets the audio file from the resouces
		backingmusic = new AudioPlayer("/Music/Level1Music.mp3");
		backingmusic.play();

	}

	//method for creating more foes
	private void populatefoes() {

		foes = new ArrayList<Foe>();

		Rebel r;

		//where the foes are going to spawn
		Point[] points = new Point[] { 
				new Point(200,100),
				new Point(300,100),
				new Point(700,100),
				new Point(860, 100),
				new Point(1525, 100), 
				new Point(1680, 100),
				new Point(2600, 100),
				new Point(2700,100),
				new Point(2800,100),
				new Point(2900,100),
				new Point(3000,100),
				
		};

		//loop through all the points, 
		//create a Rebel enemy and for every point position put the enemy there
		for (int i = 0; i < points.length; i++) {
			r = new Rebel(tileMap);
			r.setPosition(points[i].x, points[i].y);
			foes.add(r);
			
			hud = new Hud(player);
			
			

		}

	}

	// 2.function update
	public void update() {
		// update player
		player.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());

		// check collision with enemy
		player.checkCollision(foes);

		// atack the enemy
		player.checkAttack(foes);

		// update all foes
		for (int i = 0; i < foes.size(); i++) {
			foes.get(i).update();
			if (foes.get(i).isEnemyDead()) {
				foes.remove(i);
				i--;
			}
		}

	}

	// 3. function draw
	public void draw(Graphics2D g) {

		// clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		// draw background
		bg.draw(g);

		// draw tilemap
		tileMap.draw(g);

		// draw player
		player.draw(g);
		
		//draw HUD
		hud.draw(g);

		// draw foes
		for (int i = 0; i < foes.size(); i++) {
			foes.get(i).draw(g);
		}

	}

	// 4.function keyPressed
	public void keyPressed(int k) {

		// sets the controls for the player, when key is pressed, true value
		// will occur until released.
		// just made random key inputs for this for now
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(true);
		if (k == KeyEvent.VK_UP)
			player.setUp(true);
		if (k == KeyEvent.VK_DOWN)
			player.setDown(true);
		if (k == KeyEvent.VK_SPACE)
			player.setJumping(true);
		if (k == KeyEvent.VK_R)
			player.setBashing();
		if (k == KeyEvent.VK_F)
			player.setFiring();

		// Adds Xbox controll listener to the adapter to take commands
		con.addXboxControllerListener(new XboxControllerAdapter() {

			public void dpad(int direction, boolean pressed) {
				if (direction == 0 && pressed) {
					player.setUp(true);
				} else {
					player.setUp(false);
				}

				if (direction == 2 && pressed) {
					player.setRight(true);
				} else {
					player.setRight(false);
				}

				if (direction == 4 && pressed) {
					player.setDown(true);
				} else {
					player.setDown(false);
				}

				if (direction == 6 && pressed) {
					player.setLeft(true);
				} else {
					player.setLeft(false);
				}

			}

			public void buttonA(boolean pressed) {
				if (pressed) {
					player.setJumping(true);
				}
			}

			public void rightShoulder(boolean pressed) {
				if (pressed) {
					player.setFiring();
				}

			}

			public void leftShoulder(boolean pressed) {
				if (pressed) {
					player.setFiring();
				}

			}

		});

	}

	// 5.function keyReleased
	public void keyReleased(int k) {

		// sets the controls for the player, when key is released, current value
		// becomes false, and so goes
		// back to previous state: before the key(s) were pressed.
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(false);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(false);
		if (k == KeyEvent.VK_UP)
			player.setUp(false);
		if (k == KeyEvent.VK_DOWN)
			player.setDown(false);
		if (k == KeyEvent.VK_SPACE)
			player.setJumping(false);

	}
}