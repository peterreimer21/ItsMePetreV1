package spielmodus;

import java.awt.Graphics2D;
import java.util.ArrayList;

import tilemap.Background;

public class SpieleModusManager{
	
	// Aktuelles Level --> level(1-6)modus
	private int momentanerLevel = 1;
	
	// Aktueller Modus f�r alle LevelModuse	(LevelModus)
	private int momentanerModus;
	
	// Men� 
	private MenuModus menumodus;
	
	// Level 1-6 St�ck
	private Level1Modus level1modus;
	private Level2Modus level2modus;	
	private Level3Modus level3modus;
	private Level4Modus level4modus;	
	private Level5Modus level5modus;
	private Level6Modus level6modus;	
	
	// ArrayList f�r alle GameModuss
	private ArrayList<SpieleModus> spieleModus;
	
	// Background nur f�r das Men�
	private Background bg;
	
	// Alle Moduse als int
	public static final int MENUMODUS = 0;
	public static final int CREDITSMODUS = 1;
	public static final int EINSTELLUNGENMODUS = 2;
	public static final int LEVELAUSWAHLMODUS = 3;
	public static final int LEVEL1MODUS = 4;
	public static final int LEVEL2MODUS = 5;
	public static final int LEVEL3MODUS = 6;
	public static final int LEVEL4MODUS = 7;
	public static final int LEVEL5MODUS = 8;
	public static final int LEVEL6MODUS = 9;
	public static final int PAUSEMODUS = 10;
	public static final int LEVELGEWONNENMODUS = 11;
	public static final int GAMEOVERMODUS = 12;
	public static final int SPIELGEWONNENMODUS = 13;
	
	// Konstruktor
	public SpieleModusManager() {
		// Background setzen f�r alle Men�Moduse (LevelModuse bekommen anderen Hintergrund)
		bg = new Background("/Backgrounds/menubg/grassbg.gif", 0.1);
		bg.setVector(-0.1, 0);
		
		spieleModus = new ArrayList<SpieleModus>();
		
		// Als erstes Men�Modus �ffnen,...
		momentanerModus = MENUMODUS;
		
		// dannach die restlichen GameModuss der ArrayList spieleModus hinzuf�gen 
		spieleModus.add(menumodus = new MenuModus(this));
		spieleModus.add(new CreditsModus(this));
		spieleModus.add(new EinstellungenModus(this));
		spieleModus.add(new LevelauswahlModus(this));
		spieleModus.add(level1modus = new Level1Modus(this));
		spieleModus.add(level2modus = new Level2Modus(this));
		spieleModus.add(level3modus = new Level3Modus(this));
		spieleModus.add(level4modus = new Level4Modus(this));
		spieleModus.add(level5modus = new Level5Modus(this));
		spieleModus.add(level6modus = new Level6Modus(this));
		spieleModus.add(new PauseModus(this));
		spieleModus.add(new LevelGewonnenModus(this));
		spieleModus.add(new GameOverModus(this));
		spieleModus.add(new SpielGewonnenModus(this));
	}
	
	public void update() {
		spieleModus.get(momentanerModus).update();
	}
	
	public void draw(Graphics2D g) {
		spieleModus.get(momentanerModus).draw(g);
	}
	
	public void keyPressed(int k) {
		spieleModus.get(momentanerModus).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		spieleModus.get(momentanerModus).keyReleased(k);
	}
	
	//Getter-Methode
	public Background getBackground(){
		return bg;
	}
	
	public SpieleModus getMenuModus(){
		return menumodus;
	}
	
	public Level1Modus getLevel1Modus(){
		return level1modus;
	}
	
	public Level2Modus getLevel2Modus(){
		return level2modus;
	}
	
	public Level3Modus getLevel3Modus(){
		return level3modus;
	}
	
	public Level4Modus getLevel4Modus(){
		return level4modus;
	}
	
	public Level5Modus getLevel5Modus(){
		return level5modus;
	}
	
	public Level6Modus getLevel6Modus(){
		return level6modus;
	}
	
	public int getmomentanerLevel(){
		return momentanerLevel; 
	}
	
	// Setter-Methoden
	public void setModus(int modus) {
		momentanerModus = modus;
	}
	
	public void setMomentanerLevel(int momentanerLevel){
		this.momentanerLevel = momentanerLevel;
	}
	
	public void setPauseMenuMomentanerLevelModus(){
		if (momentanerLevel == 1) {
			setModus(SpieleModusManager.LEVEL1MODUS);
		}
		else if (momentanerLevel == 2) {
			setModus(SpieleModusManager.LEVEL2MODUS);
		}
		else if (momentanerLevel == 3) {
			setModus(SpieleModusManager.LEVEL3MODUS);
		}
		else if (momentanerLevel == 4) {
			setModus(SpieleModusManager.LEVEL4MODUS);
		}
		else if (momentanerLevel == 5) {
			setModus(SpieleModusManager.LEVEL5MODUS);
		}
		else if (momentanerLevel == 6) {
			setModus(SpieleModusManager.LEVEL6MODUS);
		}
	}
	
	public void setNachstenLevelModus(){
		momentanerLevel++;
		setMainMenuLetztenLevelModus();
	}
	public void setMainMenuLetztenLevelModus(){
		if (momentanerLevel == 1) {
			level1modus.getspieler().setLeben();
			level1modus.lvlReset_setPosition();
			level1modus.entityReset();
			setModus(SpieleModusManager.LEVEL1MODUS);
		}
		else if (momentanerLevel == 2) {
			level2modus.getspieler().setLeben();
			level2modus.lvlReset_setPosition();
			level2modus.entityReset();
			setModus(SpieleModusManager.LEVEL2MODUS);
		}
		else if (momentanerLevel == 3) {
			level3modus.getspieler().setLeben();
			level3modus.lvlReset_setPosition();
			level3modus.entityReset();
			setModus(SpieleModusManager.LEVEL3MODUS);
		}
		else if (momentanerLevel == 4) {
			level4modus.getspieler().setLeben();
			level4modus.lvlReset_setPosition();
			level4modus.entityReset();
			setModus(SpieleModusManager.LEVEL4MODUS);
		}
		else if (momentanerLevel == 5) {
			level5modus.getspieler().setLeben();
			level5modus.lvlReset_setPosition();
			level5modus.entityReset();
			setModus(SpieleModusManager.LEVEL5MODUS);
		}
		else if (momentanerLevel == 6) {
			level6modus.getspieler().setLeben();
			level6modus.lvlReset_setPosition();
			level6modus.entityReset();
			setModus(SpieleModusManager.LEVEL6MODUS);
		}
	}
}
