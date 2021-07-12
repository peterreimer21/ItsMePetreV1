package entity;

import tilemap.Tile;
import tilemap.TileMap;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Game;

public abstract class MapObject {
	
	// Tiles und Map
	protected TileMap tileMap;
	protected int tileGrosse;
	// Breite und H�he der Map
	protected double breiteMap;
	protected double hoheMap;
	
	// Position
	protected double xPosi;
	protected double yPosi;
	// Geschwingigkeit
	protected double dx;
	protected double dy;
	
	// Dimension
	protected int width;
	protected int height;
	
	// Kollisionsbox
	protected int kollisionWidth;
	protected int kollisionHeight;
	
	// Kollision
	protected int momentaneReihe;
	protected int momentaneSpalte;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean obenLinks;
	protected boolean obenRechts;
	protected boolean untenLinks;
	protected boolean untenRechts;
	
	// Animationen
	protected Animation animation;
	protected int momentaneAktion;
	protected int vorherigeAktion;
	protected boolean richtungLinks;
	
	// Bewegung - Steuerung
	protected boolean links;
	protected boolean rechts;
	protected boolean oben;
	protected boolean unten;
	protected boolean springen;
	protected boolean fallen;
	
	// Bewegung - Attribute
	protected double bewegGeschwindigkeit;
	protected double maximaleGeschwindigkeit;
	protected double stopGeschwindigkeit;
	protected double fallGeschwindigkeit;
	protected double maximaleFallGeschwindigkeit;
	protected double springGeschwindigkeit;
	protected double maximaleSpringGeschwindigkeit;
	protected double stopSpringGeschwindigkeit;
	
	// Konstructor
	public MapObject(TileMap tileMap) {
		this.tileMap = tileMap;
		this.tileGrosse = tileMap.getTileGrosse(); 
	}
	
	// Kollsion checken
	public boolean kollisionPrufen(MapObject mapobject) {
		Rectangle rec1 = getRectangle();
		Rectangle rec2 = mapobject.getRectangle();
		return rec1.intersects(rec2);
	}
	
	// kollisionwidth und andere wegen collision �ndern
	public Rectangle getRectangle() {
		return new Rectangle(
			(int)xPosi - kollisionWidth,
			(int)yPosi - kollisionHeight,
			kollisionWidth,
			kollisionHeight
		);
	}
	
	public void seitenErrechnen(double x, double y) {
		int leftTile = (int)(x - kollisionWidth / 2) / tileGrosse;
		int rightTile = (int)(x + kollisionWidth / 2 - 1) / tileGrosse;
		int topTile = (int)(y - kollisionHeight / 2) / tileGrosse;
		int bottomTile = (int)(y + kollisionHeight / 2 - 1) / tileGrosse;
		
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		obenLinks = tl == Tile.BLOCKIERT;
		obenRechts = tr == Tile.BLOCKIERT;
		untenLinks = bl == Tile.BLOCKIERT;
		untenRechts = br == Tile.BLOCKIERT;
	}
	
	public void tileMapKollisionUberprufen(){
		
		momentaneSpalte = (int)xPosi / tileGrosse;
		momentaneReihe = (int)yPosi / tileGrosse;
		
		xdest = xPosi + dx;
		ydest = yPosi + dy;
		
		xtemp = xPosi;
		ytemp = yPosi;
		
		seitenErrechnen(xPosi, ydest);
		if(dy < 0) {
			if(obenLinks || obenRechts) {
				dy = 0;
				ytemp = momentaneReihe * tileGrosse + kollisionHeight / 2;
			}
			else {
				ytemp += dy;
			}
		}
		if(dy > 0) {
			if(untenLinks || untenRechts) {
				dy = 0;
				fallen = false;
				ytemp = (momentaneReihe + 1) * tileGrosse - kollisionHeight / 2;
			}
			else {
				ytemp += dy;
			}
		}
		
		seitenErrechnen(xdest, yPosi);
		if(dx < 0) {
			if(obenLinks || untenLinks) {
				dx = 0;
				xtemp = momentaneSpalte * tileGrosse + kollisionWidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		if(dx > 0) {
			if(obenRechts || untenRechts) {
				dx = 0;
				xtemp = (momentaneSpalte + 1) * tileGrosse - kollisionWidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		if(!fallen) {
			seitenErrechnen(xPosi, ydest + 1);
			if(!untenLinks && !untenRechts) {
				fallen = true;
			}
		}
	}
		
	public void draw(Graphics2D g){
		// geht nach rechts, wird nach rechts gezeichnet
		if(richtungLinks) {
			g.drawImage(
				animation.getImage(),
				(int)(xPosi + breiteMap - width / 2),
				(int)(yPosi + hoheMap - height / 2),
				null
			);
		}
		// geht nach links, wird nach links gezeichnet
		else {
			g.drawImage(
				animation.getImage(),
				(int)(xPosi + breiteMap - width / 2 + width),
				(int)(yPosi + hoheMap - height / 2),
				-width, // Bild umdrehen, wegen dem minus
				height,
				null
			);	
		}
	}
	
	// Getter-Methoden
	public int getX() { return (int)xPosi; }
	public int getY() { return (int)yPosi; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getKollisionWidth() { return kollisionWidth; }
	public int getKollisionHeight() { return kollisionHeight; }
	
	// Setter-Methoden
	public void setLinks(boolean b) { links = b; }
	public void setRechts(boolean b) { rechts = b; }
	public void setHoch(boolean b) { oben = b; }
	public void setRunter(boolean b) { unten = b; }
	public void setSpringen(boolean b) { springen = b; }
	
	public void setPosition(double x, double y) {
		this.xPosi = x;
		this.yPosi = y;
	}
		
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPosition() {
		breiteMap = tileMap.getXPosi();
		hoheMap = tileMap.getYPosi();
	}
	
	// Gegner reset wenn nicht sichtbar
	public boolean nichtSichtbar() {
		if (xPosi + breiteMap + width/2 < 0 ||
		    xPosi + breiteMap - width/2 > Game.WIDTH ||
		    yPosi + hoheMap + height/2 < 0 ||
		    yPosi + hoheMap - height/2  > Game.HEIGHT){
			return true;
		}
		return false;
	}
	
	// mapobjects-reseten
	public void reset(){
		rechts = true;
		links = false;
		richtungLinks = true;
	}
}
















