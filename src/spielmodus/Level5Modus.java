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

public class Level5Modus extends SpieleModus{
	
	private TileMap tileMap;
	private Background bg;
	
	private HUD hud;
	private Spieler spieler;
	
	//spinne
	private Spinne spinne1;
	private Spinne spinne2;
	private Spinne spinne3;
	
	//frosch
	private Frosch frosch1;
	private Frosch frosch2;
	private Frosch frosch3;
	
	private ArrayList<Gegner> enemies;
	
	public Level5Modus(SpieleModusManager smm) {
		this.smm = smm;
		
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/cavetileset.gif");
		tileMap.loadMap("/Maps/level1-5.map");
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
		frosch1 = new Frosch(tileMap);
		frosch2 = new Frosch(tileMap);
		frosch3 = new Frosch(tileMap);
		enemies.add(spinne1);
		enemies.add(spinne2);
		enemies.add(spinne3);
		enemies.add(frosch1);
		enemies.add(frosch2);
		enemies.add(frosch3);
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
		spieler.setPosition(80, 214);
		spinne1.setPosition(518, 246);
		spinne2.setPosition(1718,310);
		spinne3.setPosition(1337, 310);
		frosch1.setPosition(846, 246);
		frosch2.setPosition(1902, 246);
		frosch3.setPosition(1488, 278);
		entityReset();
 	}
	
	public void entityReset(){
		spieler.reset();
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).reset();	
		}
	}
	
	public boolean levelGewonnen(){
		int anzahlSpalteSpieler = (int)spieler.getX()/32;
		int anzahlReiheSpieler = (int)spieler.getY()/32;
		int[][] map = tileMap.getMap();
		if (map[anzahlReiheSpieler][anzahlSpalteSpieler] == 13 || map[anzahlReiheSpieler][anzahlSpalteSpieler] == 23) {
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
