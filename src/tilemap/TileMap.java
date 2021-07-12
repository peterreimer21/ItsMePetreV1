package tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.Game;

public class TileMap {
	
	// Position
	private double xPosi;
	private double yPosi;
	
	// Grenze f�r das Zeichnen der TileMap
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	// Map
	private int[][] map;
	private int tileGrosse;
	
	// Anzahl der Reihen und Spalten in der .Map Datei
	private int anzahlReihen;
	private int anzahlSpalten;
	private int width;
	private int height;
	
	// TileSet .gif Bild
	private BufferedImage tileset;
	private int anzahlTilesSpalte;
	private int anzahlTilesReihe;
	// Array f�r die Tiles
	private Tile[][] tiles;
	
	// Anzahl Tiles
	private int rowOffset;
	private int colOffset;
	private int anzahlReihenZuZeichnen;
	private int anzahlSpaltenZuZeichnen;
	
	
	// Spalten und Reihenanzahl herausfinden die gezeichnet werden k�nnen
	public TileMap(int tileGrosse) {
		this.tileGrosse = tileGrosse;	// 1 Tile --> 32*32 Pixel
		anzahlReihenZuZeichnen = Game.HEIGHT / tileGrosse ;
		anzahlSpaltenZuZeichnen = Game.WIDTH / tileGrosse ;
	}
	
	public void loadTiles(String s){
		
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s)); // Tileset Bild einlesen
			anzahlTilesSpalte = tileset.getWidth() / tileGrosse; // Tileset Bild : Bildbreite / 32 --> Anzahl der Tiles in einer Reihe
			anzahlTilesReihe = tileset.getHeight() / tileGrosse; // Tileset Bild : Bildh�he / 32 --> Anzahl der Tiles in einer Spalte
			tiles = new Tile[anzahlTilesReihe] [anzahlTilesSpalte]; // Array machen f�r Methode subimages
			
			BufferedImage subimage;
			for (int Reihe = 0; Reihe < 3; Reihe++) {
				for (int Spalte = 0; Spalte < anzahlTilesSpalte; Spalte++) {
					subimage = tileset.getSubimage(Spalte * tileGrosse, Reihe * tileGrosse, tileGrosse, tileGrosse);
					tiles[Reihe][Spalte] = new Tile(subimage, Tile.NORMAL);
				}
			}
			
			for (int Reihe = 3; Reihe < anzahlTilesReihe; Reihe++) {
				for (int Spalte = 0; Spalte < anzahlTilesSpalte; Spalte++) {
					subimage = tileset.getSubimage(Spalte * tileGrosse, Reihe * tileGrosse, tileGrosse, tileGrosse);
					tiles[Reihe][Spalte] = new Tile(subimage, Tile.BLOCKIERT);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String s){
		
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			anzahlSpalten = Integer.parseInt(br.readLine());
			anzahlReihen = Integer.parseInt(br.readLine());
			
			map = new int[anzahlReihen][anzahlSpalten]; // Gr��e der Map
			width = anzahlSpalten * tileGrosse;
			height= anzahlReihen * tileGrosse;
			
			xmin = Game.WIDTH - width;
			xmax = 0;
			ymin = Game.HEIGHT - height;
			ymax = 0;
			
			String delims = "\\s+";
			for (int Reihe = 0; Reihe < anzahlReihen; Reihe++) {
				
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int Spalte = 0; Spalte < anzahlSpalten; Spalte++) {
					map[Reihe][Spalte] = Integer.parseInt(tokens[Spalte]);
				}
			}
			in.close();
			br.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y){
		this.xPosi += (x - this.xPosi);
		this.yPosi += (y - this.yPosi);
		fixGrenzen();
		
		colOffset = (int) - this.xPosi / tileGrosse;
		rowOffset = (int) - this.yPosi / tileGrosse;
		//System.out.println(colOffset);
		//System.out.println(rowOffset);
 	}

	// Abfrage f�r die Maximale und Minimale Position der TileMap
	private void fixGrenzen() {
		if (xPosi > xmax){
			xPosi = xmax;
		}
		else if (xPosi < xmin){ 
			xPosi = xmin;
		}
		
		if (yPosi > ymax){ 
			yPosi = ymax;
		}
		else if (yPosi < ymin){ 
			yPosi = ymin;
		}
	}
	
	public void draw(Graphics2D g){
		for (int Reihe = rowOffset; Reihe < rowOffset + anzahlReihenZuZeichnen+1; Reihe++) {
			if (Reihe >= anzahlReihen) 
				break;
			
			for (int Spalte = colOffset; Spalte < colOffset + anzahlSpaltenZuZeichnen+1; Spalte++) {
				if(Spalte >= anzahlSpalten)
					break;
				if (map[Reihe][Spalte] == 0)
					continue;
				
				int rc = map[Reihe][Spalte];
				int reihe = rc / anzahlTilesSpalte;
				//System.out.println(reihe);
				int spalte = rc % anzahlTilesSpalte;
				g.drawImage(tiles[reihe][spalte].getImage(),(int)xPosi + Spalte * tileGrosse, (int)yPosi + Reihe * tileGrosse, null);
			}
		}
	}
	
	// get-Methoden
	public int getTileGrosse(){ return tileGrosse; }
	public double getXPosi(){ return xPosi; }
	public double getYPosi(){ return yPosi; }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public int getAnzahlReihen() { return anzahlReihen; }
	public int getAnzahlSpalten() { return anzahlSpalten; }
	
	public int getType(int Reihe, int Spalte){
		int rc = map[Reihe][Spalte];
		int r = rc / anzahlTilesSpalte;
		int c = rc % anzahlTilesSpalte;
		return tiles[r][c].getTyp();
	}
	
	public Tile[][] getTiles(){
		return tiles;
	}
	
	public int[][] getMap(){
		return map;
	}
}
