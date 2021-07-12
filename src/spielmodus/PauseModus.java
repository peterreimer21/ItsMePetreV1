package spielmodus;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;

public class PauseModus extends SpieleModus {
	
	private BufferedImage steuerungBI;
	
	public PauseModus(SpieleModusManager smm) {
		this.smm = smm;	
			try {
				steuerungBI = ImageIO.read(getClass().getResourceAsStream("/Menubilder/steuerung.gif"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void update() {}
	
	public void draw(Graphics2D g) {
		//Tranparenz
		g.setColor(new Color(255,255,255,1));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g.setColor(new Color(255,255,255,3));
		g.fillRect(60, 0, 300, Game.HEIGHT);

		g.setColor(new Color(255,255,255,7));
		g.fillRect(80, 0, 260, Game.HEIGHT);
		
		// Titel 
		g.setColor(new Color(0,200,0));
		g.setFont(new Font("Ar Julian", Font.BOLD, 35));
		g.drawString("Spiel pausiert", zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"Spiel pausiert", new Font("Ar Julian", Font.BOLD, 35)), 60);
		
		// Steuerung in der Pause
		g.setColor(new Color(0,0,0));
		g.setFont(new Font("Ar Julian", Font.PLAIN, 25));
		g.drawString("Steuerung:", zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"Steuerung:", new Font("Ar Julian", Font.PLAIN, 25)), 120);
		
		// Steuerungsbild zeichen
		g.drawImage(steuerungBI,110,150,null);
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
		if (key == KeyEvent.VK_ESCAPE) {
			smm.setPauseMenuMomentanerLevelModus();
		}
		else if(key == KeyEvent.VK_ENTER) {
			smm.setModus(SpieleModusManager.MENUMODUS);
		}
	}

	public void keyReleased(int key) {}
}