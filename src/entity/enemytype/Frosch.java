package entity.enemytype;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import entity.*;
import tilemap.TileMap;

public class Frosch extends Gegner{

	private BufferedImage[] sprites;
	
	public Frosch(TileMap tileMap){
		super(tileMap);
			
		fallGeschwindigkeit = 0.1;
		maximaleFallGeschwindigkeit = 2.0;
		springGeschwindigkeit = -5;
		stopSpringGeschwindigkeit = 0.2;
		
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
							"/Sprites/Enemies/frosch.gif"
					)	
			);
			sprites = new BufferedImage[9];
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
		animation.setVerzogerung(200);
		
		springen=true;
	}
	
	private void getNachstePosition() {
		// Springen
		if(springen && !fallen) {
			dy = springGeschwindigkeit;
			fallen = true;	
		}
		
		// Fallen
		if(fallen) {
			if(dy > 0) {
				dy += fallGeschwindigkeit;
			}
			else {
				dy += fallGeschwindigkeit;
			}
			
			if(dy < 0 && !springen) {
				dy += stopSpringGeschwindigkeit;
			}
			
			if(dy > maximaleFallGeschwindigkeit){ 
				dy = maximaleFallGeschwindigkeit;
			}
		}
	}

	
	public void update() {
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
		
		// update animation
		animation.update();
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
}

