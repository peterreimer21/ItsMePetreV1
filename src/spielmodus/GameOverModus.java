package spielmodus;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import main.Game;

public class GameOverModus extends SpieleModus {
	
	public GameOverModus(SpieleModusManager smm) {
		this.smm = smm;	
	}
	
	public void update() {}
	
	public void draw(Graphics2D g) {
		//  Game Over bild
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		// Titel 
		g.setColor(new Color(255,0,0));
		g.setFont(new Font("Ar Julian", Font.BOLD, 35));
		g.drawString("Game Over", zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"Game Over", new Font("Ar Julian", Font.BOLD, 35)), 60);
		
		g.setColor(new Color(255,255,255,125));
		g.setFont(new Font("Impact", Font.BOLD, 20));
		g.drawString("Zuruck zum Hauptmenu", zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"Zuruck zum Hauptmenu", new Font("Impact", Font.BOLD, 20)), 150);
		g.drawString("ESCAPE", zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"ESCAPE", new Font("Impact", Font.BOLD, 20)), 200);
	}
	
	// Zentrum finden
	public int zentrieren(int X,int Y,int WIDTH, int HEIGHT, String s, Font f){
		Rectangle rec = new Rectangle(X,Y,WIDTH, HEIGHT);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D r2D = f.getStringBounds(s, frc);
		Math.round(r2D.getHeight());
		int rWidth = (int) Math.round(r2D.getWidth());
		int recX = (int) Math.round(r2D.getX());
		int xPosi = (rec.width / 2) - (rWidth / 2) - recX;
		return xPosi;
	}

	public void keyPressed(int key) {
		if(key == KeyEvent.VK_ESCAPE) {
			smm.setModus(SpieleModusManager.MENUMODUS);
		}
	}

	public void keyReleased(int key) {}
}