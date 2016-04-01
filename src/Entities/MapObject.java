package Entities;

import main.GamePanel;
import tileMap.TileMap;
import tileMap.Tile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
// super class - root of all game objects that will be used
// abstract - will not use the class itself, will extend off of it.
// this class will include how the objects are placed, moved, when colliding with other objects,
// to no longer be able to move in certain direction if blocked, and not to draw the object if
//if it not on screen.

public abstract class MapObject {


	// basic enemy type variables
	protected int foeHealth;
	protected int maxFoeHealth;
	protected boolean isDead;
	protected int foeDamage;
	
	// tile stuff - the tileMap and its size
	// everything has to be protected so that the subclasses can see them
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;

	// position and vector of objects
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;

	// dimensions of the objects - for reading in the sprite sheets
	protected int width;
	protected int height;

	// collision box - used to determine collision with tiles and other enemies
	protected int cwidth;
	protected int cheight;

	// collision - variables used for the objects
	// current rows and columns
	protected int currRow;
	protected int currCol;
	// destination of objects
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;

	// four corners used to determine whether hitting a block or not on the map.
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;

	// animation - used in animation class for the actions / movement of the
	// objets
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;

	// movement - determine what the object is actually doing
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;

	// movement attributes - the speed of object - physics of the object
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;

	// constructor
	// passes through tileMap - set the tileMap and get the tile size
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
	}

	// uses spacial rectangles to determine collisions with other objects
	// checks if objects can collisde with other map objects
	public boolean intersects(MapObject o) {
		// rectangle for this object
		Rectangle r1 = this.getRectangle();
		// rectangles 2 gets another rectangle from map object
		Rectangle r2 = o.getRectangle();
		// shows if map objects have collided
		return r1.intersects(r2);
	}

	// function gets rectangle of object size - width and height etc
	public Rectangle getRectangle() {
		return new Rectangle((int) x - cwidth, (int) y - cheight, cwidth,
				cheight);
	}

	// four corner method for determining tile collision
	// passes the positions x and y
	public void calculateCorners(double x, double y) {
		// column of left, right, top and bottom side of player in current
		// position
		int leftTile = (int) (x - cwidth / 2) / tileSize;
		int rightTile = (int) (x + cwidth / 2 - 1) / tileSize;
		int topTile = (int) (y - cheight / 2) / tileSize;
		int bottomTile = (int) (y + cheight / 2 - 1) / tileSize;

		// gets the types of each of these tiles surrounding current position of
		// player object
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);

		// sets the four booleans, sets the four corners to check if they are
		// blocked
		// example, if player jumped to top left, and it was blocked, then left
		// tile would
		// be set to blocked, and therefore prevent player from going top left
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;

	}

	// check whether or not player has ran into blocked tile or normal tile
	public void checkTileMapCollision() {

		// to determine current column and row player is on
		currCol = (int) x / tileSize;
		currRow = (int) y / tileSize;
		// destination positions (next positions)
		xdest = x + dx;
		ydest = y + dy;
		// using x temp and y temp as changes will be made when object is moved
		xtemp = x;
		ytemp = y;

		// calculate corners from y direction (up and down)
		calculateCorners(x, ydest);
		// if we are going upwards, check top two corners, and so if collision,
		// stop moving in top direction
		if (dy < 0) {
			if (topLeft || topRight) {
				dy = 0;
				// sets object just below tile just hit.
				ytemp = currRow * tileSize + cheight / 2;
			} else {
				// otherwise can keep going upwards
				ytemp += dy;
			}
		}
		// if we are going downwards, landed on tile, check two bottom corners
		if (dy > 0) {
			if (bottomLeft || bottomRight) {
				dy = 0;
				// hit the ground
				falling = false;
				// set y position a pixel above the tile player just landed on
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			} else {
				// otherwise can keep falling if not hit the ground
				ytemp += dy;
			}
		}

		// calculating corners from x direction (left and right)
		calculateCorners(xdest, y);
		// when player is going left, check left corners
		if (dx < 0) {
			if (topLeft || bottomLeft) {
				dx = 0;
				// if we hit blocked tile to left, stop moving left
				xtemp = currCol * tileSize + cwidth / 2;
			} else {
				// otherwise keep going left
				xtemp += dx;
			}
		}
		// when player is going right, check right corners
		if (dx > 0) {
			if (topRight || bottomRight) {
				dx = 0;
				// if we hit a blocked tile to the right, stop moving right
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			} else {
				// otherwise carry on moving right
				xtemp += dx;
			}
		}
		// check if player has ran below the ground
		if (!falling) {
			calculateCorners(x, ydest + 1);
			// if we are not on ground, start falling
			if (!bottomLeft && !bottomRight) {
				falling = true;
			}
		}

	}

	// getters to return the values of the objects
	// gets x and y
	public int getx() {
		return (int) x;
	}

	public int gety() {
		return (int) y;
	}

	// gets height and width of object
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	// gets collision height and collision width when objects collide
	public int getCWidth() {
		return cwidth;
	}

	public int getCHeight() {
		return cheight;
	}

	// function sets the position of the object
	public void setPosition(double x, double y) {
		// setters
		this.x = x;
		this.y = y;
	}

	// sets the vector of the object
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	// every map object has two different positions:
	// global positions: the regular x and y
	// local position: their x and y position + tilemap position
	public void setMapPosition() {
		// set the xmap and ymap accordingly
		// finds out how far xmap and ymap has moved

		// map position tells us where to draw character
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}

	// set actions for the objects
	public void setLeft(boolean b) {
		left = b;
	}

	public void setRight(boolean b) {
		right = b;
	}

	public void setUp(boolean b) {
		up = b;
	}

	public void setDown(boolean b) {
		down = b;
	}

	public void setJumping(boolean b) {
		jumping = b;
	}

	// return a boolean to determine whether object is on the screen or not
	// if it is not on screen, no need to draw it
	public boolean notOnScreen() {
		// final position of player object is beyond the bounds of the screen,
		// do not draw
		// object is beyond left side
		return x + xmap + width < 0 ||
		// object beyond right side
				x + xmap - width > GamePanel.WIDTH ||
				// object above screen
				y + ymap + height < 0 ||
				// object below the screen
				y + ymap - height > GamePanel.HEIGHT;
	}

	public void draw(Graphics2D g) {

		// if player is facing right, draw the image and get the animation
		if (facingRight) {
			g.drawImage(animation.getImage(),
					// at the position of the player according to map
					(int) (x + xmap - width / 2),
					(int) (y + ymap - height / 2), null);
		}
		// else if facing left, draw the sprite flipped
		else {
			g.drawImage(animation.getImage(),
					// width is shifted
					(int) (x + xmap - width / 2 + width),
					(int) (y + ymap - height / 2),
					// negative width indicates flipping of image
					-width, height, null);

		}
	}

}