package utcn.oop.sokoban;

import java.awt.event.*;

/**
 * Class that respresents the Controller of the MVC Model. Application starts
 * from here. All management decisions are made here.
 * 
 * @author PopCistianVad
 *
 */
public class Controller {
	/*
	 * Object of type View. Controller must somehow work with an object that
	 * represent the user interface
	 */
	private View view;

	/**
	 * Object of type Model. Controller implements the bussines logic through
	 * this object
	 */
	private Model model;

	/**
	 * Matrix that store the format of the map. Eg: 41111 10091 13201 00000
	 */
	private int[][] map;

	/**
	 * Number of rows of the map
	 */
	private int mapHeight;

	/**
	 * Number of columns of map
	 */
	private int mapWidth;

	/**
	 * The number of levels
	 */
	private int noLevels;

	/**
	 * Number of times when the level was finished
	 */
	private int noShowCongrats = 0;

	/**
	 * Create object of type Controller
	 * 
	 * @param view
	 *            View object represents a GUI
	 * @param model
	 *            Model object represent the working mechanism
	 * @param noLevels
	 *            Number of levels
	 */
	public Controller(View view, Model model, int noLevels) {
		this.noLevels = noLevels;
		this.view = view;
		this.model = model;

		this.view.menu.addPlayButtonListener(new RedirectToLevelsView());

		for (int i = 0; i < this.view.levelsview.getNoLevels(); i++) {
			this.view.levelsview.addLevelButtonListener(i, new RedirecToMap());
		}

	}

	/**
	 * Listener for when the Play button is hitted
	 * 
	 * @author PopCistianVad
	 *
	 */
	class RedirectToLevelsView implements ActionListener {

		/**
		 * Method which redirect user from the Menu page to SelectLevels page
		 * 
		 * @param e
		 *            object transmited when the button is pressed
		 */
		public void actionPerformed(ActionEvent e) {
			view.menu.setVisible(false);
			view.levelsview.setVisible(true);
		}
	}

	/**
	 * Listener for the button of a specific level. Eg: Level3 button from
	 * Select Levels view.
	 * 
	 * @author PopCistianVad
	 *
	 */
	class RedirecToMap implements ActionListener {
		/**
		 * Method which redirect user from the SelectLevel page to a specific
		 * level
		 * 
		 * @param e
		 *            object transmited when the button is pressed
		 */
		public void actionPerformed(ActionEvent e) {
			String level = "";
			int j = 0;
			for (int i = 0; i < noLevels; i++) {
				if (e.getSource() == view.levelsview.getLevel(i)) {
					j = i + 1;
					level = "res/levels/level" + j + ".txt";
				}
			}

			view.levelsview.setVisible(false);

			int[] vect = model.findMapSize(level);
			map = model.readFromFile(level);

			mapHeight = vect[0];
			mapWidth = vect[1];

			view.map = view.new Map(j, map, mapHeight, mapWidth);

			view.map.addMovingButtonsListener(new Moving()); //
			view.map.setVisible(true);
			view.map.addGotoLevelsButtonListener(new GotoLevels());

		}
	}

	/**
	 * Listener for the button to redirect to Select Levels view. When someone
	 * is playing and decide to abort the game and go to another level
	 * 
	 * @author PopCistianVad
	 *
	 */
	class GotoLevels implements ActionListener {
		/**
		 * Method which redirect user from a specific level to SelectLevel page
		 * 
		 * @param e
		 *            object transmited when the button is pressed
		 */
		public void actionPerformed(ActionEvent e) {
			view.map.setFocusableGotoLevels(true);
			view.map.setVisible(false);
			view.levelsview.setVisible(true);
		}
	}

	/**
	 * Listener for the moving button which controll the player
	 * 
	 * @author PopCistianVad
	 *
	 */
	class Moving implements KeyListener {

		/**
		 * Count the number of times when a box was moved
		 */
		int objMoved;

		/**
		 * Count the number of steps which a player made
		 */
		int steps;

		/**
		 * Unimplemented method requiered to be able the KeyListener class.
		 * Activate an object of type KeyEvent when an key is typed;
		 * 
		 * @param key
		 *            The object invoked
		 */
		public void keyTyped(KeyEvent key) {
		}

		/**
		 * Activate an object of type KeyEvent when an key is pressed;
		 * 
		 * @param key
		 *            The object invoked
		 */
		public void keyPressed(KeyEvent key) {
			int code = key.getKeyCode();
			int currentHeight = model.currentHeight();
			int currentWidth = model.currentWidth();

			if (code == KeyEvent.VK_LEFT) {
				map = model.move(map, currentHeight, currentWidth, 0);
				objMoved = model.getObjectsMoved();
				steps = model.getSteps();
				view.map.setContentPane(view.map.drawMap(objMoved, steps, map, mapHeight, mapWidth));
			}

			if (code == KeyEvent.VK_RIGHT) {
				map = model.move(map, currentHeight, currentWidth, 1);
				objMoved = model.getObjectsMoved();
				steps = model.getSteps();
				view.map.setContentPane(view.map.drawMap(objMoved, steps, map, mapHeight, mapWidth));
			}

			if (code == KeyEvent.VK_UP) {
				map = model.move(map, currentHeight, currentWidth, 2);
				objMoved = model.getObjectsMoved();
				steps = model.getSteps();
				view.map.setContentPane(view.map.drawMap(objMoved, steps, map, mapHeight, mapWidth));
			}

			if (code == KeyEvent.VK_DOWN) {
				map = model.move(map, currentHeight, currentWidth, 3);
				objMoved = model.getObjectsMoved();
				steps = model.getSteps();
				view.map.setContentPane(view.map.drawMap(objMoved, steps, map, mapHeight, mapWidth));
			}

			view.map.revalidate();
			view.map.repaint();
			view.map.setFocusable(true);
			view.map.requestFocus();
			if ((model.checkIfDone(map, mapHeight, mapWidth) == true) && (noShowCongrats == 0)) {
				view.map.showFinishedLevelDialog();
				noShowCongrats = 1;
			}
			view.map.addGotoLevelsButtonListener(new GotoLevels());
		}

		/**
		 * Unimplemented method requiered to be able the KeyListener class.
		 * Activate an object of type KeyEvent when an key is released;
		 * 
		 * @param key
		 *            The object invoked
		 */
		public void keyReleased(KeyEvent key) {
		}

	}

	/**
	 * Main method. From here the compilation starts
	 * 
	 * @param args
	 *            Information to send to the java app from cmd
	 */
	public static void main(String[] args) {
		int NUMBER_OF_LEVELS = 11;

		Model model = new Model();
		View view = new View(NUMBER_OF_LEVELS);
		Controller controller = new Controller(view, model, NUMBER_OF_LEVELS);
	}
}
