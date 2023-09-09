package utcn.oop.sokoban;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

/**
 * The View class of the MVC model
 * 
 * @author PopCistianVad
 *
 */
public class View {

	/**
	 * The first frame that an user sees
	 */
	public Menu menu;

	/**
	 * The frame that contain all levels and offer to the user the posibility of
	 * choice
	 */
	public LevelsView levelsview;

	/**
	 * The map frame where the user is playing
	 */
	public Map map;

	/**
	 * The width of a tile(square) in pixels
	 */
	private static final int TILE_WIDTH = 50;

	/**
	 * The height of a tile(square) in pixels
	 */
	private static final int TILE_HEIGHT = 50;

	/**
	 * Constructor that make the environment for the frames to appears
	 * 
	 * @param noLevels
	 *            The number of levels contained
	 */
	public View(int noLevels) {
		this.menu = new Menu();
		this.levelsview = new LevelsView(noLevels);
	}

	/**
	 * The first frame that an user sees.
	 * 
	 * @author PopCistianVad
	 *
	 */
	class Menu extends JFrame {

		/**
		 * The width of the frame
		 */
		private static final int WIDTH = 636;

		/**
		 * The height of the frame
		 */
		private static final int HEIGHT = 480;

		/**
		 * A button that redirects the user to the frame where from he can
		 * choice a level
		 */
		private JButton playButton;

		/**
		 * Constructor that creates the first frame
		 */
		Menu() {
			super();

			JLabel background = new JLabel();
			GridBagLayout gridBagLayout = new GridBagLayout();
			Dimension menuFrameSize = new Dimension(WIDTH, HEIGHT);

			try {
				background = new JLabel(new ImageIcon(ImageIO.read(new File("res/MainFrame.jpg"))));
			} catch (IOException e) {
				e.printStackTrace();
			}

			playButton = new JButton("Play");
			playButton.setBackground(Color.ORANGE);

			background.setLayout(gridBagLayout);
			background.add(playButton);

			this.setResizable(false);
			this.setPreferredSize(menuFrameSize);
			this.setContentPane(background);
			this.pack();
			this.setVisible(true);
			this.setTitle("Sokoban");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);

		}

		/**
		 * Method that assigns an ActionListener to playButton
		 * 
		 * @param listener
		 *            ActionListener to be assigned
		 */
		public void addPlayButtonListener(ActionListener listener) {
			playButton.addActionListener(listener);
		}
	}

	/**
	 * The second frame that an user sees. From here he can chose a level
	 * 
	 * @author PopCistianVad
	 *
	 */
	class LevelsView extends JFrame {

		/**
		 * An array with buttons with the levels
		 */
		private JButton[] level;// = null;

		/**
		 * The number of levels
		 */
		private int noLevels;

		/**
		 * The width of the frame
		 */
		private static final int WIDTH = 636;

		/**
		 * The height of the frame
		 */
		private static final int HEIGHT = 480;

		/**
		 * Constructs the frame with levels
		 * 
		 * @param noLevels
		 *            The number of levels
		 */
		LevelsView(int noLevels) {
			super();

			this.noLevels = noLevels;
			int noRows = ((int) (noLevels / 3)) + 1;

			GridLayout gridLayout = new GridLayout(noRows, 3);
			GridBagLayout gridBagLayout = new GridBagLayout();
			Dimension levelsViewFrameDimension = new Dimension(WIDTH, HEIGHT);
			GridBagConstraints constraint = new GridBagConstraints();

			JPanel displayLevels = new JPanel();
			JLabel background = new JLabel();

			try {
				background = new JLabel(new ImageIcon(ImageIO.read(new File("res/SelectLevels.jpg"))));
			} catch (IOException e) {
				e.printStackTrace();
			}

			gridLayout.setHgap(10);
			gridLayout.setVgap(10);

			level = new JButton[noLevels];
			for (int i = 0; i < this.noLevels; i++) {
				int j = i + 1;
				this.level[i] = new JButton("Level " + j);
				this.level[i].setBackground(Color.ORANGE);
				displayLevels.add(level[i]);
			}

			displayLevels.setLayout(gridLayout);
			displayLevels.setOpaque(false);
			background.setLayout(gridBagLayout);
			background.add(displayLevels, constraint);

			this.setResizable(false);
			this.setPreferredSize(levelsViewFrameDimension);
			this.setContentPane(background);
			this.pack();
			this.setVisible(false);
			this.setTitle("Sokoban");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
		}

		/**
		 * Setter to find the number of levels
		 * 
		 * @return The numbers of levels available
		 */
		public int getNoLevels() {
			return this.noLevels;
		}

		/**
		 * Returns a button with a specific level inside
		 * 
		 * @param i
		 *            The identifier for the button
		 * @return Object of type JButton
		 */
		public JButton getLevel(int i) {
			return level[i];
		}

		/**
		 * Method that assigns an ActionListener to every button of levels
		 * 
		 * @param listener
		 *            ActionListener to be assigned
		 * @param levelNumber
		 *            The level number where to add an actionListener
		 */
		public void addLevelButtonListener(int levelNumber, ActionListener listener) {
			this.level[levelNumber].addActionListener(listener);
		}

	}

	/**
	 * Frame representing the map, the playing view
	 * 
	 * @author PopCistianVad
	 *
	 */
	class Map extends JFrame {

		/**
		 * The height of the map, in pixels
		 */
		private int mapHeight;

		/**
		 * The width of the map, in pixels
		 */
		private int mapWidth;

		/**
		 * Boolean value telling us if a level has been finished or not
		 */
		private boolean finishedLevel = false;

		/**
		 * The current level
		 */
		private int level;

		/**
		 * The numbers of steps made
		 */
		private int steps = 0;

		/**
		 * The number of boxes moved
		 * 
		 */
		private int objectsMoved = 0;

		/**
		 * Button that redirects the player to the menu with levels
		 */
		JButton gotoLevels;

		/**
		 * Constructor that draws the map
		 * 
		 * @param level
		 *            The current level
		 * @param mapValues
		 *            The matrix associated with the current level
		 * @param mapHeightValues
		 *            The height of the map
		 * @param mapWidthValues
		 *            The width of the map
		 */
		Map(int level, int[][] mapValues, int mapHeightValues, int mapWidthValues) {
			super();

			this.level = level;
			this.mapHeight = mapHeightValues;
			this.mapWidth = mapWidthValues;

			String lev = "Level " + Integer.toString(this.level);
			JPanel content = new JPanel();
			content = drawMap(objectsMoved, steps, mapValues, mapHeight, mapWidth);
			this.setResizable(false);
			this.setContentPane(content);
			this.pack();
			this.setVisible(false);
			this.setTitle(lev);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			this.setFocusable(true);
			this.requestFocus();
		}

		/**
		 * Method that draws the components of the map
		 * 
		 * @param objMoved
		 *            The number of objects moved
		 * @param steps
		 *            The number of steps made
		 * @param mapValues
		 *            The matrix associated with the map
		 * @param mapHeight
		 *            Map's height
		 * @param mapWidth
		 *            Map's width
		 * @return Panel with the content of the frame
		 */
		public JPanel drawMap(int objMoved, int steps, int[][] mapValues, int mapHeight, int mapWidth) {
			this.objectsMoved = objMoved;
			this.steps = steps;

			GridLayout gridlayout = new GridLayout(mapHeight, mapWidth);
			GridLayout rightMenu = new GridLayout(5, 1);
			FlowLayout flowlayout = new FlowLayout();
			Dimension celDimension = new Dimension(TILE_WIDTH, TILE_HEIGHT);
			JPanel content = new JPanel();
			JPanel content1 = new JPanel();
			JPanel content2 = new JPanel();
			JPanel[][] map = new JPanel[mapHeight][mapWidth];
			Color backgroundcolor = new Color(35, 94, 50);
			String lev = "Level " + Integer.toString(this.level);
			String obMvd = "Objects moved: " + Integer.toString(this.objectsMoved);
			String stps = "Steps: " + Integer.toString(this.steps);
			JLabel showLevel = new JLabel(lev, SwingConstants.CENTER);
			JLabel showObjectsMoved = new JLabel(obMvd, SwingConstants.CENTER);
			JLabel showSteps = new JLabel(stps, SwingConstants.CENTER);
			Color buttoncolor = new Color(243, 172, 28);

			gotoLevels = new JButton("Levels");
			gotoLevels.setOpaque(true);
			gotoLevels.setBackground(buttoncolor);

			content.setLayout(flowlayout);
			content.setBackground(backgroundcolor);
			content1.setLayout(gridlayout);
			content2.setLayout(rightMenu);
			content2.setPreferredSize(new Dimension(150, 200));
			content2.setBackground(backgroundcolor);

			for (int i = 0; i < mapHeight; i++) {
				for (int j = 0; j < mapWidth; j++) {
					ImageIcon image = new ImageIcon();
					switch (mapValues[i][j]) {
					case 0:
						try {
							image = new ImageIcon("res/images/blank.jpg");
						} catch (Exception e) {
						}

						break;
					case 1:
						try {
							image = new ImageIcon("res/images/wall.jpg");
						} catch (Exception e) {
						}
						break;
					case 2:
						try {
							image = new ImageIcon("res/images/normal_box.jpg");
						} catch (Exception e) {
						}
						break;
					case 3:
						try {
							image = new ImageIcon("res/images/desiredBox.jpg");
						} catch (Exception e) {
						}
						break;
					case 4:
						try {
							image = new ImageIcon("res/images/outside.jpg");
						} catch (Exception e) {
						}
						break;
					case 7:
						try {
							image = new ImageIcon("res/images/player.jpg");
						} catch (Exception e) {
						}
						break;

					case 8:
						try {
							image = new ImageIcon("res/images/positionated_box.jpg");
						} catch (Exception e) {
						}
						break;

					case 9:
						try {
							image = new ImageIcon("res/images/player.jpg");
						} catch (Exception e) {
						}
						break;
					default:
						try {
							image = new ImageIcon("res/images/outside.jpg");
						} catch (Exception e) {
						}
						break;
					}

					JLabel label = new JLabel("", image, JLabel.CENTER);
					JPanel panel = new JPanel(new BorderLayout());
					panel.add(label, BorderLayout.CENTER);
					map[i][j] = panel;
					map[i][j].setPreferredSize(celDimension);
					content1.add(map[i][j]);
				}
			}

			if (this.finishedLevel == true) {
				System.out.println(finishedLevel);
				showFinishedLevelDialog();
			}

			content2.add(gotoLevels);
			content2.add(showLevel);
			content2.add(showObjectsMoved);
			content2.add(showSteps);
			content.add(content1);
			content.add(content2);

			return content;
		}

		/**
		 * Method that assigns an KeyListener to some keys of the keyboard
		 * 
		 * @param listener
		 *            KeyListener to be assigned
		 */
		public void addMovingButtonsListener(KeyListener listener) {
			this.addKeyListener(listener);
		}

		/**
		 * Method that assigns an ActionListener to gotoLevels button
		 * 
		 * @param listener
		 *            ActionListener to be assigned
		 */
		public void addGotoLevelsButtonListener(ActionListener listener) {
			this.gotoLevels.addActionListener(listener);
		}

		/**
		 * Method that shows a dialog when the level is finished
		 */
		public void showFinishedLevelDialog() {
			JFrame dialog = new JFrame();
			JOptionPane.showMessageDialog(dialog, "Congratulations !");
		}

		/**
		 * Getter for the current level
		 * 
		 * @return Returns the current level
		 */
		public int getLevel() {
			return this.level;
		}

		/**
		 * Setter for the current level
		 * 
		 * @param lev
		 *            The new level wished
		 */
		public void setLevel(int lev) {
			this.level = lev;
		}

		/**
		 * Method that set the gotoLevels focusable or not so that can be
		 * pressed
		 * 
		 * @param x
		 *            The boolean value of the setFocusable parameter
		 */
		public void setFocusableGotoLevels(boolean x) {
			this.gotoLevels.setFocusable(x);
			this.gotoLevels.requestFocus();
		}

	}
}