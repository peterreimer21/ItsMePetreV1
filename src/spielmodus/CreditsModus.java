package spielmodus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import main.Game;

public class CreditsModus extends SpieleModus{
	
	private String[] credits = {
			"Fatih Erol",
			"Kevin Hissel",
			"Peter Reimer",
		};
	
	// Titel
	private Color titleCreditsColor;
	private Font titleCreditsFont;
	
	// Credits
	private Font creditsFont;
	
	public CreditsModus(SpieleModusManager smm) {
		this.smm = smm;
	}
	
	public void update() {
		smm.getBackground().update();
	}
	
	public void draw(Graphics2D g) {
		
		// Hintergrund im Men� zeichnen
		smm.getBackground().draw(g);
		
		// Font f�r Hauptmen�
		titleCreditsColor = new Color(34,139,34);
		titleCreditsFont = new Font("Ar Julian", Font.BOLD, 30);
		
		// Font f�r Hauptmen�
		creditsFont = new Font("Ar Julian", Font.BOLD, 20);
		
		// Titel im Men� zeichnen(konfigurationen)
		g.setColor(titleCreditsColor);
		g.setFont(titleCreditsFont);
		g.drawString("Credits", zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"Credits", titleCreditsFont), 60);	
		
		// Credits-->Font und Farbe f�r die Namen
		g.setFont(creditsFont);
		g.setColor(Color.BLACK);
		// Men� zentrieren und Zeichnen
		for(int i = 0; i < credits.length; i++) {
			g.drawString( credits[i],zentrieren(0,0,Game.WIDTH, Game.HEIGHT, credits[i], creditsFont), 150 + (i * 30));
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
	
	public void keyPressed(int key) {
			if(key == KeyEvent.VK_ESCAPE){
				smm.setModus(SpieleModusManager.MENUMODUS);
			}
	}
	public void keyReleased(int key) {}	
}