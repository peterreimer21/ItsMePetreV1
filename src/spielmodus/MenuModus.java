package spielmodus;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import Audio.SFX;
import main.Game;

public class MenuModus extends SpieleModus {
	
	private int currentChoice = 0;
	
	private String[] options = {
		"Spielen",
		"Levelauswahl",
		"Credits",
		"Einstellungen",
		"Beenden"
	};
	
	private Color titelColor;
	private Font titelFont;
	
	private Color menuColor;
	private Font menuFont;
	
	private Color currentChoiceColor;
	private SFX menuoptionSFX;
	
	public MenuModus(SpieleModusManager smm) {
		this.smm = smm;
		
		menuoptionSFX = new SFX("/SFX/menuoption.wav");
		
		// Font f�r Titel
		titelColor = new Color(0, 150,0);
		titelFont = new Font("Ar Julian", Font.BOLD, 35);
					
		// Font f�r Hauptmen�
		menuColor = new Color( 50,100,150);
		menuFont = new Font("Ar Julian", Font.BOLD, 20);
					
		// Font f�r aktuelle Auswahl
		currentChoiceColor = new Color( 100, 0, 0);
	}
	
	public void update() {
		smm.getBackground().update();
	}
	
	public void draw(Graphics2D g) {
		
		// Hintergrund im Men� zeichnen
		smm.getBackground().draw(g);
		
		// Titel im Men� zeichnen
		g.setColor(Color.BLACK);
				
		// Titel im Men�(konfigurationen)
		g.setColor(titelColor);
		g.setFont(titelFont);
		g.drawString("It's me Petre", zentrieren(0,0,Game.WIDTH, Game.HEIGHT,"It's me Petre", titelFont), 60);
		
		// Men�optionen zeichnen(konfigurationen)
		g.setColor(menuColor);
		g.setFont(menuFont);
		
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(currentChoiceColor);
			}
			else {
				g.setColor(Color.RED);
				g.setFont(menuFont);
			}
		
			// Men� zentrieren und zeichnen
			g.drawString(options[i], zentrieren(0,0,Game.WIDTH,Game.HEIGHT,options[i], menuFont), 130 + i * 20);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Ar Julian", Font.BOLD, 11));
		g.drawString("V1", 0, Game.HEIGHT - 11);
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
	
	// Men�auswahl
	private void select() {
		int choice = currentChoice;
		currentChoice = 0;
		if(choice == 0) {
			smm.setMainMenuLetztenLevelModus();
		}
		if(choice == 1) {
			smm.setModus(SpieleModusManager.LEVELAUSWAHLMODUS);
		}
		if(choice == 2) {
			smm.setModus(SpieleModusManager.CREDITSMODUS);
			currentChoice = 0;
		}
		if(choice == 3) {
			smm.setModus(SpieleModusManager.EINSTELLUNGENMODUS);
			currentChoice = 0;
		}
		if(choice == 4) {
	        System.exit(0);
		}
	}
	
	// Men�kontrolle
	public void keyPressed(int key) {
		
		if(key == KeyEvent.VK_ENTER){
			select();
		}
		
		if(key == KeyEvent.VK_UP) {
			menuoptionSFX.play();
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = 4;
			}
		}
		
		if(key == KeyEvent.VK_DOWN) {
			menuoptionSFX.play();
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	
	public void keyReleased(int key) {}
}