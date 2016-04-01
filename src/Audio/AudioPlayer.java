package Audio;

import javax.sound.sampled.*;

// this class sorts out the audio for the game music, and the SFX of the entities

public class AudioPlayer {
	// Clip variable
	private Clip clip;
	//constructor - passes through string s - path to the audio file 
	public AudioPlayer(String s) {
		// read in the file
		try {
			//gets the input stream
			AudioInputStream i =
				AudioSystem.getAudioInputStream(
					getClass().getResourceAsStream(s));
			
			// sets out the format that is required - gets decoded format
			AudioFormat baseFormat = i.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			// convert to decoded format
			AudioInputStream ais =
				AudioSystem.getAudioInputStream(decodeFormat, i); // gets i, the base format
			
			//clip is the datastructure that holds the audio
			clip = AudioSystem.getClip();
			clip.open(ais);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	// if clip is not there, don't play
	public void play() {
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	// if the clip is playing, stop
	public void stop() {
		if(clip.isRunning()) clip.stop();
	}
	//closes the clip after it has stopped
	public void close() {
		stop();
		clip.close();
	}
	
}
