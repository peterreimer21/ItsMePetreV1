package entity.enemytype;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import entity.Animation;
import entity.Gegner;
import tilemap.TileMap;

public class Fledermaus extends Gegner{

	private BufferedImage[] sprites;
	
	public Fledermaus(TileMap tileMap){
		super(tileMap);
			
		bewegGeschwindigkeit = 0.5;
		maximaleGeschwindigkeit = 0.5;
		
		width = 32;
		height = 32;
		kollisionWidth = 25;
		kollisionHeight = 25;
		
		leben = maxLeben = 1;
		schaden = 1;
		
		//Sprites laden
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/fledermaus.gif"
					)	
			);
			sprites = new BufferedImage[7];
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
		animation.setVerzogerung(175);
		
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
				richtungLinks = false;
			}
			else if(links && dx == 0) {
				rechts = true;
				links = false;
				richtungLinks = true;
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
