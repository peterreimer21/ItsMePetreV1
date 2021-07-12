package spielmodus;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.Gegner;
import entity.MapObject;
import entity.Spieler;
import entity.enemytype.Schnecke;
import interfaces.HUD;
import main.Game;
import tilemap.Background;
import tilemap.TileMap;

public class Level1Modus extends SpieleModus{
	
	private TileMap tileMap;
	private Background bg;
	
	private HUD hud;
	private Spieler spieler;
	
	// Schnecken
	private Schnecke schnecke1;
	private Schnecke schnecke2;
	private Schnecke schnecke3;
	private Schnecke schnecke4;
	private Schnecke schnecke5;
	private Schnecke schnecke6;
	private Schnecke schnecke7;
	
	private ArrayList<Gegner> enemies;
	
	public Level1Modus(SpieleModusManager smm) {
		this.smm = smm;
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		
		bg = new Background("/Backgrounds/lvlbg/levelbasis.gif");
		
		spieler = new Spieler(tileMap);
		
		hud = new HUD(spieler);
		
		enemies = new ArrayList<Gegner>();
		populateEnemies();
	}
	
	private void populateEnemies() {
		enemies = new ArrayList<Gegner>();
		for (int i = 0; i < 7; i++) {
			enemies.add( new Schnecke(tileMap) );
		}
	}

	public void update() {
		if(spieler.getLeben() == 0){
			spieler.setLeben();
			smm.setModus(SpieleModusManager.GAMEOVERMODUS);	
		}
		
		// Wenn der Spieler das Level schafft
		if (levelGewonnen()){
			smm.setModus(SpieleModusManager.LEVELGEWONNENMODUS);
		}
				
		// Wenn der Spieler in den Abgrund f�llt
		if (spieler.getY()/32 == tileMap.getAnzahlReihen()-1) { lvlReset_setPosition();spieler.treffer();}
		
		// Spieler update 
		spieler.update();
		tileMap.setPosition(
				(Game.WIDTH / 2 - spieler.getX()),
				(Game.HEIGHT / 2 - spieler.getY())
		);
		
		// Alle Gegner aktualiesieren, wegen Angriff
		spieler.angriffUberprufen(enemies);
				
		// Alle Gegner aktualiesieren
		for(int i = 0; i < enemies.size(); i++) {
			Gegner gegner = enemies.get(i);
			
			gegner.update();
			
			if(gegner.istTot()) {
				enemies.remove(i);
				i--;
			}
			// Grenzen der TileMap, damit Gegner resetet werden falls sie rausfallen
			else if((int)gegner.getY()/32 == tileMap.getAnzahlReihen()-1||
			   (int)gegner.getX()/32 == tileMap.getAnzahlSpalten()-1)
			{
				enemies.remove(i);
			}
		}
	}

	public void lvlReset_setPosition() {
		tileMap.setPosition(0, 0);
		spieler.setPosition(112, 758);
		
		// Only Snails
		enemies.get(0).setPosition(725, 758);
		enemies.get(1).setPosition(656, 822);
		enemies.get(2).setPosition(982, 758);
		enemies.get(3).setPosition(1991, 854);
		enemies.get(4).setPosition(1760, 854);
		enemies.get(5).setPosition(756, 662);
		enemies.get(6).setPosition(580, 694);
		entityReset();
 	}
	
	public void entityReset(){
		spieler.reset();
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).reset();	
		}
	}
	
	public boolean levelGewonnen(){
		int anzahlSpaltenSpieler = (int)spieler.getX()/32;
		int anzahlReihenSpieler = (int)spieler.getY()/32;
		int[][] map = tileMap.getMap();
		if (map[anzahlReihenSpieler][anzahlSpaltenSpieler] == 13 || map[anzahlReihenSpieler][anzahlSpaltenSpieler] == 23) {
			return true;
		}
		return false;
	}
	
	public void draw(Graphics2D g) {
		
		// Hintergrund zeichnen
		bg.draw(g);
				
		// Map zeichnen (Graphic nur zeichnen)
		tileMap.draw(g);
		
		// Character zeichnen 
		spieler.draw(g);
		
		// Gegner zeichnen
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// Hud zeichnen
		hud.draw(g);
	}

	public void keyPressed(int key) {
		if(key == KeyEvent.VK_ESCAPE){ 
			spieler.reset();
			smm.setModus(SpieleModusManager.PAUSEMODUS); 
		}
		
		if(key == KeyEvent.VK_LEFT){ spieler.setLinks(true); }	
		if(key == KeyEvent.VK_RIGHT){ spieler.setRechts(true); }
		if(key == KeyEvent.VK_UP){ spieler.setHoch(true); }
		if(key == KeyEvent.VK_DOWN){ spieler.setRunter(true); }
		if(key == KeyEvent.VK_SPACE){ spieler.setSpringen(true); }
	}

	public void keyReleased(int key) {
		if(key == KeyEvent.VK_LEFT){ spieler.setLinks(false); }	
		if(key == KeyEvent.VK_RIGHT){ spieler.setRechts(false); }
		if(key == KeyEvent.VK_UP){ spieler.setHoch(false); }
		if(key == KeyEvent.VK_DOWN){ spieler.setRunter(false); }
		if(key == KeyEvent.VK_SPACE){ spieler.setSpringen(false); }
	}
	
	public Spieler getspieler(){
		return spieler;
	}
}
