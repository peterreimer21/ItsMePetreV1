package spielmodus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import Audio.Audio;
import Audio.SFX;
import main.Game;

public class EinstellungenModus extends SpieleModus{

	private Color titleEinstellungenColor;
	private Font titleEinstellungenFont;
	
	private Font einstellungenFont;
	
	private Color textEinstellungenColor;
	private Font textEinstellungenFont;
	
	private int momentaneChoice = 0;
	private int xPosi;
	
	private Audio hintergrundmusik;
	private SFX einstellungenauswahlSFX;
	
	boolean audioOn = false;
	
	String einstellungen = "Einstellungen";
	String musik = "Musik";
	
	private String[] textoptions = {
			"Musik",
	};
	
	private String[] options = {
			"An",
			"Aus",
	};
	
	public EinstellungenModus(SpieleModusManager smm){
		this.smm = smm;
		
		einstellungenauswahlSFX = new SFX("/SFX/menuoption.wav");
		
		// Font f�r Titel
		titleEinstellungenColor = new Color( 0, 150, 0);
		titleEinstellungenFont = new Font("Ar Julian", Font.BOLD, 30);
		
		// Font f�r Text
		textEinstellungenColor = new Color( 0, 0, 0);
		textEinstellungenFont = new Font("Ar Julian", Font.BOLD, 25);
		
		// Font f�r Hauptmen�
		einstellungenFont = new Font("Cooper Black", Font.BOLD, 25);
		
		hintergrundmusik = new Audio("/Musik/bgmusikgrass.wav");
	}

	public void update() {
		smm.getBackground().update();
	}

	public void draw(Graphics2D g) {
		
		smm.getBackground().draw(g);
		
		 //Titel zeichnen
		g.setColor(titleEinstellungenColor);
		g.setFont(titleEinstellungenFont);
		g.drawString(einstellungen, xPosi = zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"Einstellungen", titleEinstellungenFont),60);
		
		int musikabstand = 0;
		g.setColor(textEinstellungenColor);
		g.setFont(textEinstellungenFont);
		// Text zeichnen
		for (int i = 0; i < textoptions.length; i++) {
			g.drawString(textoptions[i], xPosi = zentrieren(0,0,Game.WIDTH/2,Game.HEIGHT,textoptions[i],textEinstellungenFont), 60 * i + 140);
			musikabstand = xPosi*2;
		}
			
		// Optionen zeichnen
		g.setColor(Color.RED);
		g.setFont(einstellungenFont);
		
		for (int i = 0; i < options.length; i++) {
			if(i == momentaneChoice) {
				g.setColor(Color.GREEN);
			}
			else {
				g.setColor(Color.RED);
			}
				g.drawString(options[i], xPosi = zentrieren(Game.WIDTH/3,0,Game.WIDTH + musikabstand + ( (i%2) * Game.WIDTH/3),Game.HEIGHT,options[i],einstellungenFont), 60 * (int)(i/2) + 140);
		}
	}
		
	public int zentrieren(int X,int Y,int WIDTH, int HEIGHT, String s, Font f){
		Rectangle r = new Rectangle(X,Y,WIDTH, HEIGHT);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D r2D = f.getStringBounds(s, frc);
		int rWidth = (int) Math.round(r2D.getWidth());
		int rX = (int) Math.round(r2D.getX());
		int xPosi = (r.width / 2) - (rWidth / 2) - rX;
		return xPosi;
	}
	
	// Men�auswahl
	private void select() {
		if(momentaneChoice == 0) {
			 hintergrundmusik.starteHintergrundMusik();
		}
		else if(momentaneChoice == 1) {
			 hintergrundmusik.stoppeHintergrundMusik();
		}
	}

	// Men�kontrolle
	public void keyPressed(int key) {
		if(key == KeyEvent.VK_ENTER){
			select();
		}
		
		if(key == KeyEvent.VK_ESCAPE){
			smm.setModus(SpieleModusManager.MENUMODUS);
		}
		
		if(key == KeyEvent.VK_LEFT) {
			if(momentaneChoice == 1) {
				momentaneChoice--;
				einstellungenauswahlSFX.play();
			}
		}
		if(key == KeyEvent.VK_RIGHT) {
			if(momentaneChoice == 0) {
				momentaneChoice++;
				einstellungenauswahlSFX.play();
			} 
		}
	}

	public void keyReleased(int key) {}
}
