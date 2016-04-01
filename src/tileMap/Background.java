package tileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Image.*;
import javax.imageio.*;

import main.GamePanel;

public class Background {

	//create image variable
	private BufferedImage image;

	// private int width;
	// private int height;

	// background position variables
	private double x;
	private double y;
	// background scroll variables
	private double dx;
	private double dy;

	//scale at which the background moves
	private double moveScale;

	// get the resources
	public Background(String s, double ms) {
		//try catch to import the resource files into the game
		try {
			//makes it a resource, part of the internal resources folder
			image = ImageIO.read(getClass().getResourceAsStream(s));
			// width = image.getWidth();
			// height = image.getHeight();

			moveScale = ms;
        //catch exception and printstack trace ot find any problems
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	// sets up the position x and y positions of background
	public void setPosition(double x, double y) {
      //movescale added to stop background going off screen:
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;

	}

	// sets up dx and dy - background to scroll
	public void setVector(double dx, double dy) {

		this.dx = dx;
		this.dy = dy;

	}

	// method updates dx and dy - updates the automatic scroll
	public void update() {

		x += dx;
		y += dy;

	}

	// this will draw the actual background
	public void draw(Graphics2D g) {
		// draws the background image at position x and y.
		g.drawImage(image, (int) x, (int) y, null);
		// if x is less than zero, draw extra image to the right
		// in order to fill up the space
		if (x < 0) {
			g.drawImage(image, (int) x + GamePanel.WIDTH, (int) y, null);
		}
		// if x is more than zero, draw extra image to the left
		// in order to fill up the space
		if (x > 0) {
			g.drawImage(image, (int) x - GamePanel.WIDTH, (int) y, null);
		}
	}

}

