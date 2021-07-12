package tilemap;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage image;
	private int typ;
	
	// Tiletypn
	public static final int NORMAL = 0;
	public static final int BLOCKIERT = 1;
	
	public Tile(BufferedImage image, int typ){
		this.image = image;
		this.typ = typ;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public int getTyp() {
		return typ;
	}
}
