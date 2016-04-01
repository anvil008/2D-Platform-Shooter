package Entities;

import java.awt.image.BufferedImage;

//Test Comment for Josh

//this class will handle the sprite animation for the game
public class Animation {

	// use an array of bufferedimages to hold all the frames
	private BufferedImage[] frames;
	//keep track of current frame
	private int currentFrame;

	//timer between frames
	private long startTime;
	// how long between each frame
	private long delay;

	// tells us whether animation has played already, i.e. attacking animations
	private boolean playedOnce;

	//constructor
	public Animation() {
		playedOnce = false;
	}

	// sets the frames - passing in array of bufferedimages
	public void setFrames(BufferedImage[] frames) {
		//sets up the frames
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}

	//give frames a delay
	public void setDelay(long d) { delay = d; }

	//set frame number, incase of manual use
	public void setFrame(int i) { currentFrame = i; }

	//update function - handles the logic for determining whether or not to
	// move to the next frame
	public void update() {
		// if delay is -1, no animation
		if(delay == -1) return;
		// otherwise calculate time since last frame
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		// if amount of time greater than delay, move onto next frame
		if(elapsed > delay) {
			//reset the timer
			currentFrame++;
			startTime = System.nanoTime();
		}
		// checks if frames go out of bounds, loop back to 0
		//animation has been played only once
		if(currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}

	}
	// gets current frame number
	public int getFrame() { return currentFrame; }
	// get the image neededto draw
	public BufferedImage getImage() { return frames[currentFrame]; }
	// return whether the animaton has been played once or not
	public boolean hasPlayedOnce() { return playedOnce; }

}