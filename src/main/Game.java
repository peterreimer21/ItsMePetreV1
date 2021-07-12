package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import spielmodus.SpieleModusManager;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable, KeyListener {
	
	// Dimension --> Fenster
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = 416;
	public static final int HEIGHT = 256;
	
	// Spielthread
	private Thread thread;
	private boolean running;
	//private int FPS = 60;
	private long targetTime = 30;
	
	// Image
	private BufferedImage image;
	private Graphics2D g;
	
	// GameStateManager
	private SpieleModusManager ssm;
	
	public Game() {
		setFocusable(true);
		
		image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		ssm = new SpieleModusManager();
	}

	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	public void run() {
		long startZeit;
		long vergangeneZeit;
		long warten;
		
		// Spiel loopen
		while(running) {
			startZeit = System.nanoTime();
			update();
			draw();
			drawToScreen();
			
			vergangeneZeit = System.nanoTime() - startZeit;
			warten = targetTime - vergangeneZeit / 1000000;
			//System.out.println(warten);
			
			// Spiel verlangsamen
			if(warten < 0) {
				warten = 5;
			}
			
			try {
				Thread.sleep(warten);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update() {
		ssm.update();
	}
	
	private void draw() {
		ssm.draw(g);
	}
	
	private void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0,(int)dim.getWidth() ,(int)dim.getHeight() , null);
		g.dispose();
	}
	
	public void keyTyped(KeyEvent key) {}
	
	public void keyPressed(KeyEvent key) {
		ssm.keyPressed(key.getKeyCode());
	}
	
	public void keyReleased(KeyEvent key) {
		ssm.keyReleased(key.getKeyCode());
	}
	
	// Getter-Methoden
	public int getGameWidth(){return WIDTH;}
	public int getGameHeight(){return HEIGHT;}
	public double getGameDimWidth(){return dim.width;}
	public double getGameDimHeight(){return dim.height;}
}