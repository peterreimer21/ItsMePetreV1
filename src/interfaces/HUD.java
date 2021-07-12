package interfaces;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import entity.Spieler;

public class HUD {
	
	private Spieler spieler;
	
	// HUD
	private BufferedImage hudAlternativeImage;
	private BufferedImage anzahlHerzenImage;
	
	public HUD(Spieler spieler) {
		this.spieler = spieler;
		try {
			hudAlternativeImage = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
			anzahlHerzenImage = ImageIO.read(getClass().getResourceAsStream("/HUD/anzahlHerzen.gif"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		
		// HUD alternative
		g.drawImage(hudAlternativeImage, 0, 10, null);
		g.drawImage(anzahlHerzenImage, spieler.getLeben()*25-77, 10, null);	
		
	}
}