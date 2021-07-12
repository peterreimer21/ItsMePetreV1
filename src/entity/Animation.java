package entity;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] frames;
	private int currentFrame;
	
	private long startTime;
	private long verzogerung;
	
	private boolean einmalGespielt;
	
	public Animation() {
		einmalGespielt = false;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		einmalGespielt = false;
	}
	
	public void update() {
		if(verzogerung == -1){
			return;
		}
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		
		if(elapsed > verzogerung) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length) {
			currentFrame = 0;
			einmalGespielt = true;
		}
	}
	
	// Getter-Methoden
	public int getFrame() { return currentFrame; }
	public boolean getEinmalGespielt() { return einmalGespielt; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	
	// Setter-Methoden
	public void setVerzogerung(long verzogerung) { this.verzogerung = verzogerung; }
	public void setFrame(int currentFrame) { this.currentFrame = currentFrame; }
}
















