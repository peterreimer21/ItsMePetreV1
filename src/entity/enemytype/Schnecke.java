package entity.enemytype;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import entity.*;
import tilemap.TileMap;

public class Schnecke extends Gegner {

	private BufferedImage[] sprites;
	
	public Schnecke(TileMap tileMap){
		super(tileMap);
			
		bewegGeschwindigkeit = 0.3;
		maximaleGeschwindigkeit = 0.3;
		fallGeschwindigkeit = 0.5;
		maximaleFallGeschwindigkeit = 5.0;
		
		width = 32;
		height = 32;
		kollisionWidth = 20;
		kollisionHeight = 20;
		
		leben = maxLeben = 1;
		schaden = 1;
		
		//Sprites laden
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/schnecke.gif"
					)	
			);
			sprites = new BufferedImage[3];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
						i * width,
						0,
						width,
						height
				);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setVerzogerung(300);
		
		links = true;
		richtungLinks = true;
	}
	
	private void getNachstePosition() {
		// Bewegung
		if(links) {
			dx = +bewegGeschwindigkeit;
			if(dx < +maximaleGeschwindigkeit) {
				dx = +maximaleGeschwindigkeit;
			}
		}
		else if(rechts) {
			dx = -bewegGeschwindigkeit;
			if(dx > -maximaleGeschwindigkeit) {
				dx = -maximaleGeschwindigkeit;
			}
		}
		// Gegner f�llt
		if(fallen) {
			dy += fallGeschwindigkeit;
		}
	}
	
	public void update() {
		if (nichtSichtbar()) {
			return;
		}
		else{
			// update position
			getNachstePosition();
			tileMapKollisionUberprufen();
			setPosition(xtemp, ytemp);
			
			// flackern checken
			if (flackern) {
				long elapsed = (System.nanoTime() - flackernTimer) / 1000000;
				if(elapsed > 400) {
					flackern = false;
				}
			}
			
			// Wenn der Gegner eine Wand behr�hrt, geht er in die andere Richtung
			if(rechts && dx == 0) {
				rechts = false;
				links = true;
				richtungLinks = true;
			}
			else if(links && dx == 0) {
				rechts = true;
				links = false;
				richtungLinks = false;
			}
			// update animation
			animation.update();
		}
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
}

