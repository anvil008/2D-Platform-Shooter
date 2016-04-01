package tileMap;

import java.awt.*;
import java.awt.image.*;

import java.io.*;
import javax.imageio.ImageIO;

import main.GamePanel;
// this TileMap class sets the tilemap of the game
public class TileMap {

	// position of the tilemap, using variables x and y
	private double x;
	private double y;

	// bounds - minimim and maximum bounds of tilemap
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;

	// smoothly scrolls the camera towards the player
	private double tween;

	// map - 2D Integer array
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;

	// tileset - a buffered image
	private BufferedImage tileset;
	private int numTilesAcross;
	
	//this represents the tileset, after we have imported the
	//tileset, going to put all tile images into this array
	private Tile[][] tiles;

	// drawing bounds - only draws tiles that are on the screen
	//which row to start drawing
	private int rowOffset;
	//which column to start drawing
	private int colOffset;
	//number of rows and columns to draw
	private int numRowsToDraw;
	private int numColsToDraw;

	// constructor - number of rows and columns to draw
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		// height of the panel / tileSize to give rows to draw just on the screen
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		// width of the panel / tileSize to give columns to draw just on the screen
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		//smooth scrolling
		tween = 0.07;

	}

	// this function loads the tileset into memory
	public void loadTiles(String s) {
// using try catch method incase of exception
		
		try {
			// gets the tilemap image - the resources
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			//number of tiles across the screen needed
			numTilesAcross = tileset.getWidth() / tileSize;
			// gets tile array - representation of tileset
			tiles = new Tile[2][numTilesAcross];

			//imports the tileset
			BufferedImage subimage;
			//for loop, using the resource image, locate subimage required for tiles
			for (int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize,
						tileSize);
                //create a new tile, first row, first column, with normal type
				// i.e. player is able to walk across it.
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				
				subimage = tileset.getSubimage(col * tileSize, tileSize,
						tileSize, tileSize);
				//gets subimage for first column, second row, wil be blocked tile
				// i.e. the player will be blocked and cannot go over it.
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// this function will load the map
	public void loadMap(String s) {
		// using try catch method incase of exception
		
		try {
			//inputstream used to get the resource
			InputStream in = getClass().getResourceAsStream(s);
			// use buffered reader to read in the text file, passing in the resource used
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			//can now read the map file
			
			//reads number of columns and rows on map
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			//create map array for rows and columns, and find out width and height of pixels
			map = new int[numRows][numCols];
	
			width = numCols * tileSize;
			height = numRows * tileSize;
			//makes sure map x and y position values have in the correct bounds
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
            //reading the map array
			String delims = "\\s++";
			//for loop, read al the lines in map
			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				//split map into tokens using delimeter (white space)
				String[] tokens = line.split(delims);
				// go through array and put it all into the map
				for (int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
         //exception used if map does not load
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//getter to get the tile size and return it
	public int getTileSize() {
		return tileSize;
	}

	// getter for the position x 
	public int getx() {
		return (int) x;
	}
	// getter for the position y
	public int gety() {
		return (int) y;
	}

	//getter to get the width
	public int getWidth() {
		return width;
	}
   //getter to get height
	public int getHeight() {
		return height;
	}

	// getter for getting the tile type - passing in a row and column
	public int getType(int row, int col) {
		//value of the row and colum
		int rc = map[row][col];
		// to get the row type, the rc / tiles accross
		int r = rc / numTilesAcross;
		// to get the column type, the rc / tiles accross
		int c = rc % numTilesAcross;
		// return the value and get the type
		return tiles[r][c].getType();
	}

	//set the position of the tile map - passing in x and y
	public void setPosition(double x, double y) {

		//makes sure camera follows player exactly and smoothly.
		//difference between the current position and new position, times tween
		//will gradually move camera smoothly.
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
        //keeps positions in the bounds
		fixBounds();

		// offset - draw the current tile on the screen
		// which column and row to start drawing
		colOffset = (int) -this.x / tileSize;
		rowOffset = (int) -this.y / tileSize;

	}

	// helper function - makes sure the bounds are not passed
	private void fixBounds() {
// makes sure x and y stay within their minimum and maximum bounds
		if (x < xmin)
			x = xmin;
		if (y < ymin)
			y = ymin;
		if (x > xmax)
			x = xmax;
		if (y > ymax)
			y = ymax;

	}

	// function draws the tilemap - passing in the graphics class
	public void draw(Graphics2D g) {

		// for loop - gets row and column offset, and makes sure that only the tilemap that is
		//currently on the screen is drawn, so cuts of any tilemap that is not currently seen.
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			if (row >= numRows)
				break;
			for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if (col >= numCols)
					break;

				// if the row and columns are equal to 0, draw nothing
				if (map[row][col] == 0)
					continue;

				// draws out the row and column tiles
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
               //draws the tile image - takes rows and column and gets the image resource
				g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize,
						(int) y + row * tileSize, null);
			}
		}

	}

}
