package Entities;

import tileMap.*;
import javax.imageio.ImageIO;

import Audio.AudioPlayer;

import tileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

//this class enhirits the use of MapObject
public class Player extends MapObject {

	// basic player type variables
	private int health;
	private int maxHealth;
	private int firegun;
	private int maxFire;
	private boolean dead;
	// the flinching of the character when an Foe hits it will indicate
	private boolean flinching;
	private long flinchTimer;

	// bullets when shooting the gun
	private boolean firing;
	private int bulletCost;
	private int bulletDamage;
	private ArrayList<PlayerFire> bullets;

	// bash gun
	private boolean bashing;
	private int bashDamage;
	private int bashRange;

	// animations

	// arraylist of buffered image arrays to hold all sprite animations
	private ArrayList<BufferedImage[]> sprites;
	// an array list called numframes - number of frames inside each animation
	// action.
	private final int[] numFrames = { 3, 9, 6, 13, 4, 10 }; // { 2, 8, 1, 2, 4,
															// 2, 5 };

	// animation actions

	// enums to determine indecies of the sprite animations
	// need one for every animation

	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int BULLET = 2;
	private static final int JUMPING = 3;
	private static final int DUCKING = 4;
	private static final int FALLING = 5;
	private static final int BASHING = 6;
	
	// gets hashmap of audio files
	// hashmap is datastorage with keyvalue pairs
	// String is key, audio player is value
	private HashMap<String, AudioPlayer> sfx;

	// call the mapobject - ets tilemap and tilesize
	// constructor - passes through the tilemap
	public Player(TileMap tm) {

		super(tm);
		// set the width and height of each frame
		width = 40;
		height = 48;
		// real width and height of sprites themselves
		cwidth = 37;
		cheight = 50;

		// player moving variables for smoothest performance
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		// player will start facing right by default
		facingRight = true;
		// player HUD - health and bullets
		health = maxHealth = 5;
		firegun = maxFire = 2500;

		bulletCost = 200;
		bulletDamage = 5;

		// initialise array to fire
		bullets = new ArrayList<PlayerFire>();

		// bashing attack damage
		bashDamage = 8;
		bashRange = 40;

		// load sprites
		try {

			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Player/player.gif"));
			// extract each animation action from image

			// create sprite arraylist
			sprites = new ArrayList<BufferedImage[]>();

			// for loop, for the 6 animation actions
			for (int i = 0; i < 6; i++) {

				// creates new buffered image array
				// numframes used to decide number of frame per anaimation
				BufferedImage[] bi = new BufferedImage[numFrames[i]];

				// read in the individual frames

				for (int j = 0; j < numFrames[i]; j++) {
					if (i != 2) {
						// get the subimage of particular animation frame
						bi[j] = spritesheet.getSubimage(j * width, i * height,
								width, height);

					} else {
						bi[j] = spritesheet.getSubimage(j * width * 2, i
								* height, width * 2, height);
					}
				}

				// adds the buffered image array to animations list
				sprites.add(bi);

			}

		}
		// catches exception when required
		catch (Exception e) {
			e.printStackTrace();
		}
		// create new animation after loading the sprites
		animation = new Animation();
		currentAction = IDLE;
		// animation starts at IDLE by default
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		
		
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("jump", new AudioPlayer("/SFX/jump.mp3"));
		sfx.put("firing", new AudioPlayer("/SFX/scratch.mp3"));
		
	}

	// getters to get health, maxhealth, fire and maxfire - mainly for HUD

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getFire() {
		return firegun;
	}

	public int getMaxFire() {
		return maxFire;
	}

	// keyboard input - setting the firing
	public void setFiring() {
		firing = true;
	}

	// keyboard input - setting the bashing
	public void setBashing() {
		bashing = true;
	}

	public void checkCollision(ArrayList<Foe> enemies) {

		// loop through enemies
		for (int i = 0; i < enemies.size(); i++) {

			Foe e = enemies.get(i);

			// check Foe collision
			if (intersects(e)) {
				hit(e.getDamage());
			}

		}
	}

	public void checkAttack(ArrayList<Foe> enemies) {

		// loop through enemies
		for (int i = 0; i < enemies.size(); i++) {

			// create Foe
			Foe e = enemies.get(i);

			//loop through the bullets
			for (int j = 0; j < bullets.size(); j++) {

				if (bullets.get(j).intersects(e)) {
					e.hit(bulletDamage);
					bullets.get(j).setHit();
				}
			}
		}
	}

	public void hit(int damage) {

		if (flinching)
			return;
		health -= damage;
		if (health < 0)
			health = 0;
		if (health == 0)
			dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	// determine where the next position of the player is, via reading the
	// keyboard input
	private void getNextPosition() {

		// movment
		// if left, move left, and not go past maxspeed
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
			// else if going right, move right, and cap it at the maxspeed
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
			// not going left , then stop the player
		} else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
				// not going right, then stop the player
			} else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}
		// cannot move while attacking, except in air

		if (
		// when bashing or firing with the player, and not jumping or
		// falling, cannot move
		(currentAction == BASHING || currentAction == BULLET)
				&& !(jumping || falling)) {
			dx = 0;
		}

		// jumping
		// if jumping and not falling, then jump
		if (jumping && !falling) {
			//get the sound for sfx jumping
			sfx.get("jump").play();
			dy = jumpStart;
			falling = true;

		}

		// falling
		if (falling) {
			// allow player to fall
			dy += fallSpeed;
			// when no longer jumping, false
			if (dy > 0)
				jumping = false;
			// longer you hold jump button, higher will jump
			if (dy < 0 && !jumping)
				dy += stopJumpSpeed;
			// only reach maximum full speed
			if (dy > maxFallSpeed)
				dy = maxFallSpeed;

		}

	}

	// update - does all the positioning configuration
	public void update() {

		// update position
		getNextPosition();
		// calling checkTilemapCollion from map object class
		checkTileMapCollision();
		// set the temp position of player
		setPosition(xtemp, ytemp);

		// check to see if bullet has been shot, else continue
		if (currentAction == BULLET) {
			if (animation.hasPlayedOnce())
				bashing = false;
		}

		// bullet attack
		firegun += 1;
		// when gun is bring fired constantly, is it set to maxfire
		if (firegun > maxFire)
			firegun = maxFire;
		// if bullets have ran out temporily, bullets will regenerate
		if (firing && currentAction != BULLET) {
			if (firegun > bulletCost) {
				firegun -= bulletCost;
				// sets up object for PlayerFire class
				PlayerFire fb = new PlayerFire(tileMap, facingRight);
				fb.setPosition(x, y);
				// adds the bullets to the object class
				bullets.add(fb);
			}
		}

		// update bullets
		for (int i = 0; i < bullets.size(); i++) {
			// updates the bullets, when bullets hit an object, or move out of the screen,they are removed
			bullets.get(i).update();
			if (bullets.get(i).shouldRemove()) {
				bullets.remove(i);
				i--;
			}
		}

		// set animation
		if (bashing) {
			// if not bashing, set to bashing!
			if (currentAction != BASHING) {
				currentAction = BASHING;
				// get bashing player frame
				animation.setFrames(sprites.get(BASHING));
				// delay of bashing
				animation.setDelay(50);
				width = 40; // width depends on GIF width!
			}
		} else if (firing) {
			// if not shooting, then shoot the gun
			if (currentAction != BULLET) {
				// gets the firing of the bullet
				sfx.get("firing").play();
				currentAction = BULLET;
				// get the shooting frame
				animation.setFrames(sprites.get(BULLET));
				// set the delay and width
				animation.setDelay(50);
				width = 80; // width depends on GIF width!
			}
		}

		// if current action is not falling, then start falling
		// else if (currentAction != DUCKING) {
		// currentAction = DUCKING;
		// // get the frame for falling
		// animation.setFrames(sprites.get(DUCKING));
		// // set the delay and width
		// animation.setDelay(100);
		// width = 30; // need GIF file
		// }

		// if current action is not jumping, then start jumping
		else if (dy < 0) {
			if (currentAction != JUMPING) {
				currentAction = JUMPING;
				// get thre frame for jumping
				animation.setFrames(sprites.get(JUMPING));
				// set the delay and width
				animation.setDelay(-1);
				width = 40; // needs GIF file
			}
		}
		// if moving left or right, start walking
		// else
		else if (left || right) {
			if (currentAction != WALKING) {
				currentAction = WALKING;
				// get the frame for walking
				animation.setFrames(sprites.get(WALKING));
				// set the delay and width of GIF
				animation.setDelay(50);
				width = 40; // needs GIF file
			}
		} else {
			// if not IDLE, then IDLE
			if (currentAction != IDLE) {
				currentAction = IDLE;
				// get the frame for IDLE
				animation.setFrames(sprites.get(IDLE));
				// get the delay and width of GIF
				animation.setDelay(400);
				width = 40; // needs GIF file
			}
		}

		// check to see if bullet has been shot, else continue
		if (currentAction == BULLET) {
			if (animation.hasPlayedOnce())
				firing = false;
		}

		// check done flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000) {
				flinching = false;
			}
		}

		// update the animation when keyboard input is processed
		animation.update();

		// set direction
		// if current action is not bashing or firing, player does not move
		if (currentAction != BASHING && currentAction != BULLET) {
			if (right)
				facingRight = true;
			if (left)
				facingRight = false;
		}

	}

	// this function will draw the actual player
	public void draw(Graphics2D g) {

		// this gets called first in any map object for draw
		setMapPosition();

		// draw bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}

		// draw player
		// when player gets hit, starts blinking to indicate damage to health
		// player flinches every 100 milliseconds
		if (flinching) {
			// for elapsed time, player will flicker / blink
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}

		// if player is facing right, draw the image and get the animation
		if (facingRight) {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2),
			// at the position of the player according to map
					(int) (y + ymap - height / 2), null);
			// else if facing left, flip to look left
		} else {
			g.drawImage(animation.getImage(),
			// width is shifted
					(int) (x + xmap - width / 2 + width),
					// negative width indicates flipping of image
					(int) (y + ymap - height / 2), -width, height, null);
		}

	}

}
