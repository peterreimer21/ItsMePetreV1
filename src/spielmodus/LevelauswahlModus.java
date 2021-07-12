package spielmodus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import Audio.SFX;
import main.Game;

public class LevelauswahlModus extends SpieleModus{
	
	private String[] lvl = {
			"Level 1",
			"Level 2",
			"Level 3",
			"Level 4",
			"Level 5",
			"Level 6",
		};
	
	private int currentChoice = 0;
	private int choice;
	
	private SFX levelauswahlSFX;
	
	// Font und Colors
	private Color titelColor;
	private Font titelFont;
	
	// Credits
	private Font textFont;
	
	public LevelauswahlModus(SpieleModusManager smm) {
		this.smm = smm;
		
		levelauswahlSFX = new SFX("/SFX/menuoption.wav");
	}
	
	public void update() {
		smm.getBackground().update();
	}
	
	public void draw(Graphics2D g) {
		
		// Hintergrund im Men� zeichnen
		smm.getBackground().draw(g);
		
		// Font f�r Hauptmen�
		titelColor = new Color(34,139,34);
		titelFont = new Font("Ar Julian", Font.BOLD, 30);
		
		// Font f�r Hauptmen�
		textFont = new Font("Ar Julian", Font.BOLD, 20);
		
		// Titel im Men� zeichnen(konfigurationen)
		g.setColor(titelColor);
		g.setFont(titelFont);
		g.drawString("Levelauswahl", zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"Levelauswahl", titelFont), 60);	
		
		// Credits-->Font und Farbe f�r die Namen
		g.setFont(textFont);
		g.setColor(Color.BLACK);
		
		// Men� zentrieren und Zeichnen
		for(int i = 0; i < 6; i++) {
			if(i == currentChoice) {
				g.setColor(Color.GREEN);
			}
			else {
				g.setColor(Color.RED);
			}
			// Zeichnen
			if (i>=3) {
				g.drawString( lvl[i],zentrieren(0,0,Game.WIDTH/2, Game.HEIGHT, lvl[i], textFont) * ((i-3) * 2) + 35, 180);
			}
			else{
				g.drawString( lvl[i],zentrieren(0,0,Game.WIDTH/2, Game.HEIGHT, lvl[i], textFont) * (i * 2) + 35, 130);
			}
		}
	}
	
	public int zentrieren(int X,int Y,int WIDTH, int HEIGHT, String string, Font font){
		Rectangle rec = new Rectangle(X,Y,WIDTH, HEIGHT);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D rec2D = font.getStringBounds(string, frc);
		int recWidth = (int) Math.round(rec2D.getWidth());
		int recX = (int) Math.round(rec2D.getX());
		int xPosi = (rec.width / 2) - (recWidth / 2) - recX;
		return xPosi;
	}
	
	public void keyPressed(int key) {
			if(key == KeyEvent.VK_ESCAPE){
				smm.setModus(SpieleModusManager.MENUMODUS);
			}
			else if(key == KeyEvent.VK_ENTER){
				select();
			}
			
			else if(key == KeyEvent.VK_UP) {
				if (currentChoice >= 3) {
					levelauswahlSFX.play();
					currentChoice -= 3;
				}
			}
			else if(key == KeyEvent.VK_DOWN) {
				if (currentChoice < 3) {
					levelauswahlSFX.play();
					currentChoice += 3;
				}
			}
			else if(key == KeyEvent.VK_RIGHT) {
				if (currentChoice >= 0 && currentChoice != 2 && currentChoice != lvl.length-1) {
					levelauswahlSFX.play();
					currentChoice++;
				}
			}
			else if(key == KeyEvent.VK_LEFT) {
				if (currentChoice <= 6 && currentChoice != 3 && currentChoice != 0) {
					levelauswahlSFX.play();
					currentChoice--;
				}
			}
	}
	
	private void select() {
		choice = currentChoice;
		currentChoice = 0;
		if (choice == 0) {
			smm.setMomentanerLevel(choice+1);
			smm.getLevel1Modus().lvlReset_setPosition();
			smm.setModus(SpieleModusManager.LEVEL1MODUS);
		}
		else if (choice == 1) {
			smm.setMomentanerLevel(choice+1);
			smm.getLevel2Modus().lvlReset_setPosition();
			smm.setModus(SpieleModusManager.LEVEL2MODUS);
		}
		else if (choice == 2) {
			smm.setMomentanerLevel(choice+1);
			smm.getLevel3Modus().lvlReset_setPosition();
			smm.setModus(SpieleModusManager.LEVEL3MODUS);
		}
		else if (choice == 3) {
			smm.setMomentanerLevel(choice+1);
			smm.getLevel4Modus().lvlReset_setPosition();
			smm.setModus(SpieleModusManager.LEVEL4MODUS);
		}
		else if (choice == 4) {
			smm.setMomentanerLevel(choice+1);
			smm.getLevel5Modus().lvlReset_setPosition();
			smm.setModus(SpieleModusManager.LEVEL5MODUS);
		}
		else if (choice == 5) {
			smm.setMomentanerLevel(choice+1);
			smm.getLevel6Modus().lvlReset_setPosition();
			smm.setModus(SpieleModusManager.LEVEL6MODUS);
		}
	}

	public void keyReleased(int key) {}
}