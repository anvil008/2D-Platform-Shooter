package Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import tileMap.TileMap;

public class Rebel extends Foe {

	// array of buffered image arrays to hold all sprite animations
	private BufferedImage[] sprites;

	// constructor - passes through the tilemap
	public Rebel(TileMap tm) {
		// call map object constructor - sets tilemap and tilesize
		super(tm);

		moveSpeed= 1.5;
		maxSpeed = 1.75;
		fallSpeed = 0.2;

		moveSpeed= 1;
		maxSpeed = 1.75;
		fallSpeed = 1;
		maxFallSpeed = 10.0;

		// set the width and height of spritesheets
		width = 64;
		height = 48;

		// real width and height of sprites
		cwidth = 54;
		cheight = 48;

		foeHealth = maxFoeHealth = 2;
		foeDamage = 1;

		//load sprites

		try {
			// reading in the spritesheet by reading the resource folder
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/rebel.gif"));

			//create sprite array
			sprites = new BufferedImage[8];

			// read in the individual sprites
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// create new animation after loading the sprites
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(75);

		//the starts by walking in the right direction and facing the right direction
		right = true;
		facingRight = true;

	}

	// determine where the next position of the rebel is
	private void getNextPosition(){

		// movement
		// if left, move left, and not go past maxspeed
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		// else if going right, move right, and cap it at the maxspeed
		else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}

		//falling
		if(falling){

			dy += fallSpeed;
		}

	}

	// update - does all the positioning configuration
	public void update(){

		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);


		//check flinching
		if(flinching){
			long elapsed = 
					(System.nanoTime() - flinchTimer) / 1000000;

			if(elapsed > 400){
				flinching = false;
			}
		}

		// if it hits a wall, go other direction
		if(right && dx == 0){
			right = false;
			left = true;
			facingRight = false;
		}
		else if (left && dx == 0 ){
			right = true;
			left = false;
			facingRight = true;
		}

		//update animation
		animation.update();


	}
	// this function will draw the actual player
	public void draw(Graphics2D g){

		// this gets called first in any map object for draw
		setMapPosition();


		super.draw(g);
	}

}