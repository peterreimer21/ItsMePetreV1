package entity;

import java.util.ArrayList;
import javax.imageio.ImageIO;

import Audio.SFX;
import tilemap.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Spieler extends MapObject {
	
	// spieler attribute
	private int leben;
	private int maxLeben;
	private boolean tot;  // wird nicht benutzt 
	private boolean flackern;
	private long flackernTimer;
	
	private SFX jumpSFX;
	private SFX schadenSFX;
	private SFX spielertotSFX;
	
	// Animation
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		2, 5, 1, 2 //anzahl animationen pro reihe im gif-bild
	};
	
	// animation aktionen
	private static final int STEHEN = 0;
	private static final int GEHEN = 1;
	private static final int SPRINGEN = 2;
	private static final int FALLEN = 3;
	
	// Grenze f�r den Spieler
	private int xmin;
	private int xmax;
	private int ymin;
	private int ymax;
	
	public Spieler(TileMap tileMap) {
		super(tileMap);
		
		// Grenzen der Map damit der Spieler nicht rausgeht an den Seiten
		xmin = 0;
		xmax = tileMap.getWidth()-14;
		ymin = 0;
		ymax = tileMap.getHeight()-14;
		
		width = 32;
		height = 32;
		kollisionWidth = 20;
		kollisionHeight = 20;
		
		bewegGeschwindigkeit = 0.20;
		maximaleGeschwindigkeit = 2.0;
		stopGeschwindigkeit = 0.5;
		fallGeschwindigkeit = 0.15;
		maximaleFallGeschwindigkeit = 4.0;
		springGeschwindigkeit = -4.7;
		stopSpringGeschwindigkeit = 0.2;
	
		richtungLinks = false;
		
		leben = 3;
		maxLeben = 99;
		
		jumpSFX = new SFX("/SFX/jump.wav");
		schadenSFX = new SFX("/SFX/schaden.wav");
		spielertotSFX = new SFX("/SFX/spielertot.wav");
		
		// Sprites f�r den Spieler/Character laden
		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
					"/Sprites/Player/spielersprites.gif"
				)
			);
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 4; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					bi[j] = spritesheet.getSubimage(
						j * width,
						i * height,
						width,
						height
					);
				}
				sprites.add(bi);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		momentaneAktion = STEHEN;
		animation.setFrames(sprites.get(STEHEN));
		animation.setVerzogerung(600);
	}
	
	private void getNachstePosition() {
		
		// Bewegung
		if(links) {
			dx -= bewegGeschwindigkeit;
			if(dx < -maximaleGeschwindigkeit) {
				dx = -maximaleGeschwindigkeit;
			}
		}
		else if(rechts) {
			dx += bewegGeschwindigkeit;
			if(dx > maximaleGeschwindigkeit) {
				dx = maximaleGeschwindigkeit;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopGeschwindigkeit;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopGeschwindigkeit;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// Springen
		if(springen && !fallen) {
			jumpSFX.play();
			dy = springGeschwindigkeit;
			fallen = true;	
		}
		
		// wenn spieler f�llt
		if(fallen) {
			if(dy > 0) {
				dy += fallGeschwindigkeit;
			}
			else {
				dy += fallGeschwindigkeit;
			}
			
			// Damit nicht die leertaste gedr�ckt gehalten werden kann
			if(dy > 0){ 
				springen = false;
			}
			
			if(dy < 0 && !springen) {
				dy += stopSpringGeschwindigkeit;
			}
			
			if(dy > maximaleFallGeschwindigkeit){ 
				dy = maximaleFallGeschwindigkeit;
			}
		}
	}
	
	// abfrage f�r die Maximale und Minimale des Spielers
		private void fixGrenzen() {
			if (xtemp < xmin){ 
				xtemp = xmin;
			}
			if (xtemp > xmax){
				xtemp = xmax;
			}
			if (ytemp < ymin){ 
				ytemp = ymin;
			}
			if (ytemp > ymax){ 
				ytemp = ymax;
			}
		}
		
	public void angriffUberprufen(ArrayList<Gegner> enemies) {
		// Gegner loopen um angriff zu �berpr�fen
		for(int i = 0; i < enemies.size(); i++) {
			Gegner enemy = enemies.get(i);
			if (!enemy.nichtSichtbar()) {
				//gegner kolision �berpr�fen
				if(kollisionPrufen(enemy)) {
					treffer();
				}
			}
		}	
	}

	public void treffer() {
		if(flackern){
			return;
		}
		leben -= 1;
		schadenSFX.play();
		if(leben < 0){
			leben = 0;
		}
		if(leben == 0){
			spielertotSFX.play();
			tot = true;
		}
		flackern = true;
		flackernTimer = System.nanoTime();
	}
	
	public void update() {
		// Position updaten
		getNachstePosition();
		tileMapKollisionUberprufen();
		fixGrenzen();
		setPosition(xtemp, ytemp);
		
		// Animationsbild setzen
		if(dy > 0) {
			if(momentaneAktion != FALLEN) {
				momentaneAktion = FALLEN;
				if(dx == 0){
					if(momentaneAktion != STEHEN) {
						momentaneAktion = SPRINGEN;
						animation.setFrames(sprites.get(STEHEN));
						animation.setVerzogerung(-1);
						width = 32;
					}
				}
				else {
					animation.setFrames(sprites.get(FALLEN));
					animation.setVerzogerung(100);
					width = 32;
				}
			}
		}
		else if(dy < 0) {
			if(momentaneAktion != SPRINGEN) {
				momentaneAktion = SPRINGEN;
				if(dx == 0){
					if(momentaneAktion != STEHEN) {
						momentaneAktion = SPRINGEN;
						animation.setFrames(sprites.get(STEHEN));
						animation.setVerzogerung(-1);
						width = 32;
					}
				}
				else {
					animation.setFrames(sprites.get(SPRINGEN));
					animation.setVerzogerung(-1);
					width = 32;
				}
			}
		}
		else if(links || rechts) {
			if(momentaneAktion != GEHEN) {
				momentaneAktion = GEHEN;
				animation.setFrames(sprites.get(GEHEN));
				animation.setVerzogerung(80);
				width = 32;
			}
		}
		else {
			if(momentaneAktion != STEHEN) {
				momentaneAktion = STEHEN;
				animation.setFrames(sprites.get(STEHEN));
				animation.setVerzogerung(600);
				width = 32;
			}
		}
		if(flackern) {
				long elapsed = (System.nanoTime() - flackernTimer) / 1000000;
				if(elapsed > 1500) {
					flackern = false;	
		}
	}
		
		animation.update();
		
		// Richtung wechseln
		if(rechts) richtungLinks = false;
		if(links) richtungLinks = true;	
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();

		// Spieler/Character zeichnen
		if(flackern) {
			long vergangeneZeit =	(System.nanoTime() - flackernTimer) / 1000000;
			if(vergangeneZeit / 100 % 2 == 0) {
				return;
			}
		}
		super.draw(g);
	}
	
	// Getter-Methoden
	public int getLeben() { return leben; }
	public int getMaxLeben() { return maxLeben; }
		
	// Setter-Methoden
	public void setLeben(){ leben=3;}
	
	// spieler-reseten
	public void reset(){
		rechts = false;
		links = false;
		richtungLinks = false;
	}
}