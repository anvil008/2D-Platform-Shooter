package tileMap;

import java.awt.image.BufferedImage;
// this class has the image and type of tiles
public class Tile {
	
	// tiles have an image and a type
	private BufferedImage image;
	private int type;
	
	//tile types
	// either have normal tiles, or blocked tiles
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	// constructors takes in an image, and a type, and sets them
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}
	
	// gets the image and types and returns them
	public BufferedImage getImage() {return image; }
	public int getType() {return type; }

}
