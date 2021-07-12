package tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Game;

public class Background {
	
	private BufferedImage image;
	
	private double xPosi;
	private double yPosi;
	private double dx;
	private double dy;
	
	private double bewegGeschwindigkeit;
	
	public Background(String s) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Background(String s, double bewegGeschwindigkeit) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
			this.bewegGeschwindigkeit = bewegGeschwindigkeit;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y) {
		this.xPosi = (x * bewegGeschwindigkeit) % image.getWidth();
		this.yPosi = (y * bewegGeschwindigkeit) % image.getHeight();
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		xPosi += dx;
		yPosi += dy;
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int)xPosi, (int)yPosi, Game.WIDTH, Game.HEIGHT,  null);
		if(xPosi < -Game.WIDTH){
			xPosi=0;
		}
		if(xPosi < 0) {
			g.drawImage( image, (int)xPosi + Game.WIDTH,	(int)yPosi,Game.WIDTH, Game.HEIGHT,	null);
		}
	}
	
	//Getter-Methode
	public double getBackgroundPosiX(){
		return xPosi;
	}
}







