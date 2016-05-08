package core;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class Sound {
	private Clip clip;
	private AudioInputStream stream;
	
	public static final Sound load(String file) {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream stream = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream(file));
			
			clip.open(stream);
			return new Sound(clip, stream);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
			// TODO: crash
		}
		return null;
	}
	
	public boolean isPlaying() {
		return clip.isRunning();
	}
	
	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void dispose() {
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: crash
		}
		clip.close();
	}
	
	public Sound(Clip clip, AudioInputStream stream) {
		this.clip = clip;
		this.stream = stream;
	}
}
