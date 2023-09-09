package utcn.oop.platformer;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {

		JFrame window = new JFrame("Platformer");
		window.setContentPane(new GamePanel());
		window.pack();
		window.setVisible(true);
	}
}
