package Audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {

	private Clip clip;
	
	public Audio(String pfad) {
		try {
			clip = AudioSystem.getClip();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(pfad));
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		starteHintergrundMusik();
	}
	
	public void starteHintergrundMusik(){
		if (!clip.isRunning()) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		}
	}
	
	public void stoppeHintergrundMusik(){
		clip.setMicrosecondPosition(0);
		clip.loop(0);
		clip.stop();
	}
}