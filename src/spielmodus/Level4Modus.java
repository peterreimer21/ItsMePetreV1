package spielmodus;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.Gegner;
import entity.Spieler;
import entity.enemytype.Frosch;
import entity.enemytype.Spinne;
import interfaces.HUD;
import main.Game;
import tilemap.Background;
import tilemap.TileMap;

public class Level4Modus extends SpieleModus{
	
	private TileMap tileMap;
	private Background bg;
	
	private HUD hud;
	private Spieler spieler;
	
	// spinne
	private Spinne spinne1;
	private Spinne spinne2;
	private Spinne spinne3;
	private Spinne spinne4;
	private Spinne spinne5;
	
	// frosch
	private Frosch frosch1;
	private Frosch frosch2;
	private Frosch frosch3;
	private Frosch frosch4;
	private Frosch frosch5;
	
	private ArrayList<Gegner> enemies;
	
	public Level4Modus(SpieleModusManager smm) {
		this.smm = smm;
		
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/cavetileset.gif");
		tileMap.loadMap("/Maps/level1-4.map");
		tileMap.setPosition(0, 0);
		
		bg = new Background("/Backgrounds/lvlbg/cavelevelbasis.gif");
		
		spieler = new Spieler(tileMap);
		
		hud = new HUD(spieler);
		
		enemies = new ArrayList<Gegner>();
		populateEnemies();
	}
	
	private void populateEnemies() {
		spinne1 = new Spinne(tileMap);
		spinne2 = new Spinne(tileMap);
		spinne3 = new Spinne(tileMap);
		spinne4 = new Spinne(tileMap);
		spinne5 = new Spinne(tileMap);
		frosch1 = new Frosch(tileMap);
		frosch2 = new Frosch(tileMap);
		frosch3 = new Frosch(tileMap);
		frosch4 = new Frosch(tileMap);
		frosch5 = new Frosch(tileMap);
		enemies.add(spinne1);
		enemies.add(spinne2);
		enemies.add(spinne3);
		enemies.add(spinne4);
		enemies.add(spinne5);
		enemies.add(frosch1);
		enemies.add(frosch2);
		enemies.add(frosch3);
		enemies.add(frosch4);
		enemies.add(frosch5);
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
		spieler.setPosition(80, 200);
		spinne1.setPosition(204, 438);
		spinne2.setPosition(1122, 534);
		spinne3.setPosition(1409, 502);
		spinne4.setPosition(2102, 374);
		spinne5.setPosition(2226, 246);
		frosch1.setPosition(365, 534);
		frosch2.setPosition(523, 534);
		frosch3.setPosition(720, 502);
		frosch4.setPosition(850, 502);
		frosch5.setPosition(1729, 374);
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
