package spielmodus;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import main.Game;

public class SpielGewonnenModus extends SpieleModus {
	
	private BufferedImage spielgewonnenBI;
	
	public SpielGewonnenModus(SpieleModusManager smm) {
		this.smm = smm;	
		try {
			spielgewonnenBI = ImageIO.read(
					getClass().getResourceAsStream(
						"/Menubilder/allelevelgeschaft.gif"
					)
				);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {}
	
	public void draw(Graphics2D g) {
		//Tranparenz
		g.setColor(new Color(255,255,255,1));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
				
		g.setColor(new Color(255,255,255,3));
		g.fillRect(40, 0, 335, Game.HEIGHT);

		g.setColor(new Color(255,255,255,7));
		g.fillRect(60, 0, 295, Game.HEIGHT);
				
		// Titel im Men�(konfigurationen)
		g.setColor(new Color(0,150,0));
		g.setFont(new Font("Ar Julian", Font.BOLD, 35));
		g.drawString("Alle", zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"Alle", new Font("Ar Julian", Font.BOLD, 35)), 50);
		g.drawString("Level", zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"Level", new Font("Ar Julian", Font.BOLD, 35)), 80);
		g.drawString("geschafft", zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"geschafft", new Font("Ar Julian", Font.BOLD, 35)), 110);
		
		// Bild zeichen
		g.drawImage(spielgewonnenBI,110,100,null);
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
	
	// Men�kontrolle
	public void keyPressed(int key) {
		if(key == KeyEvent.VK_ESCAPE) {
			smm.setModus(SpieleModusManager.MENUMODUS);
		}
	}
	
	public void keyReleased(int key) {}
}