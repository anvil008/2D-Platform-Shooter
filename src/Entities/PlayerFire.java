package Entities;

import tileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;

public class PlayerFire extends MapObject {

	// field variables for firing bullets
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;

	// constructor showing direction of fire
	public PlayerFire(TileMap tm, boolean right) {

		super(tm);

		moveSpeed = 3.8;
		if (right)
			dx = moveSpeed;
		else
			dx = -moveSpeed;

		width = 30;
		height = 30;
		cwidth = 14;
		cheight = 14;

		// load sprites

		try {

			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Player/fireball.gif"));

			sprites = new BufferedImage[4];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width,
						height);
			}

			hitSprites = new BufferedImage[3];
			for (int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(i * width, height,
						width, height);
			}

			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setHit() {
		if (hit)
			return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx = 0;
	}

	// whether or not should take bullet out game
	public boolean shouldRemove() {
		return remove;
	}

	public void update() {

		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		// if dx is 0, so not hit anymore and is removed.
		if (dx == 0 && !hit) {
			setHit();
		}
		// updates and removes fire when it hits a tile.
		animation.update();
		if (hit && animation.hasPlayedOnce()) {
			remove = true;
		}

	}

	public void draw(Graphics2D g) {

		setMapPosition();

		// if facing right, draw normally
		if (facingRight) {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2),
					(int) (y + ymap - height / 2), null);
			// else if facing left, flip to look left
		} else {
			g.drawImage(animation.getImage(),
					(int) (x + xmap - width / 2 + width),

					(int) (y + ymap - height / 2), -width, height, null);
		}

	}

}
