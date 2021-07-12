package main;

import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class MainIMP extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainIMP(){
		createGame();
	}

	public static void main(String[] args){
		new MainIMP();
	}
	
	public void createGame() {
		setTitle("It's me Petre!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		// Cursor im Spiel unsichtbar machen(provisorisch)
		setCursor(getToolkit().createCustomCursor(
				new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),"null"));
		setResizable(false);
		setContentPane(new Game());
		setVisible(true);
	}
}