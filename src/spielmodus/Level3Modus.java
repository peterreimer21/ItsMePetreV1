package spielmodus;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.Gegner;
import entity.Spieler;
import entity.enemytype.Frosch;
import entity.enemytype.Schnecke;
import interfaces.HUD;
import main.Game;
import tilemap.Background;
import tilemap.TileMap;

public class Level3Modus extends SpieleModus{
	
	private TileMap tileMap;
	private Background bg;
	
	private HUD hud;
	private Spieler spieler;
	
	//frosch
	private Frosch frosch1;
	private Frosch frosch2;
	private Frosch frosch3;
	private Frosch frosch4;
	private Frosch frosch5;
	private Frosch frosch6;
	
	// schnecke
	private Schnecke schnecke1;
	private Schnecke schnecke2;
	private Schnecke schnecke3;
	private Schnecke schnecke4;
	private Schnecke schnecke5;
	
	private ArrayList<Gegner> enemies;
	
	public Level3Modus(SpieleModusManager smm) {
		this.smm = smm;
		
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-3.map");
		tileMap.setPosition(0, 0);
		
		bg = new Background("/Backgrounds/lvlbg/levelbasis.gif");
		
		spieler = new Spieler(tileMap);
		
		hud = new HUD(spieler);
		
		enemies = new ArrayList<Gegner>();
		populateEnemies();
	}
	
	private void populateEnemies() {
		frosch1 = new Frosch(tileMap);
		frosch2 = new Frosch(tileMap);
		frosch3 = new Frosch(tileMap);
		frosch4 = new Frosch(tileMap);
		frosch5 = new Frosch(tileMap);
		frosch6 = new Frosch(tileMap);
		schnecke1 = new Schnecke(tileMap);
		schnecke2 = new Schnecke(tileMap);
		schnecke3 = new Schnecke(tileMap);
		schnecke4 = new Schnecke(tileMap);
		schnecke5 = new Schnecke(tileMap);
		enemies.add(frosch1);
		enemies.add(frosch2);
		enemies.add(frosch3);
		enemies.add(frosch4);
		enemies.add(frosch5);
		enemies.add(frosch6);
		enemies.add(schnecke1);
		enemies.add(schnecke2);
		enemies.add(schnecke3);
		enemies.add(schnecke4);
		enemies.add(schnecke5);
	}

	public void update() {
		System.out.println("Level 3 Update");
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
		spieler.setPosition(96, 566);
		frosch1.setPosition(318, 566);
		frosch2.setPosition(845, 534);
		frosch3.setPosition(1069, 566);
		frosch4.setPosition(2335,566);
		frosch5.setPosition(2335, 566);
		frosch6.setPosition(1907, 566);
		schnecke1.setPosition(1272, 630);
		schnecke2.setPosition(1429, 630);
		schnecke3.setPosition(1646, 598);
		schnecke4.setPosition(2534, 534);
		schnecke4.setPosition(606, 438);
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
		System.out.println("Level 3 Draw");
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
