package utcn.oop.sokoban;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * The Model class of MVC Realize the bussines logic of the application
 * 
 * @author PopCistianVad
 *
 */
public class Model {

	/**
	 * Matrix that store the format of the map. Eg: 41111 10091 13201 00000
	 */
	private int[][] map = null;

	/**
	 * Number of rows of the map
	 */
	private int mapHeight;

	/**
	 * Number of columns of map
	 */
	private int mapWidth;

	/**
	 * The number of moves that a player made
	 */
	private static int steps = 0;

	/**
	 * The number of boxes moved
	 */
	private static int objectsMoved = 0;

	/**
	 * Read from the file in parameter and return a matrix with the level
	 * 
	 * @param fileName
	 *            The name of the file where to search
	 * @return A matrix with the format of the map
	 */
	public int[][] readFromFile(String fileName) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			mapHeight = Integer.parseInt(br.readLine());
			mapWidth = Integer.parseInt(br.readLine());
			map = new int[mapHeight][mapWidth];

			String delimiters = "\\s+";
			for (int i = 0; i < mapHeight; i++) {
				String line = br.readLine();
				String[] tokens = line.split(delimiters);
				for (int j = 0; j < mapWidth; j++) {
					map[i][j] = Integer.parseInt(tokens[j]);
				}
			}
			br.close();

		} catch (Exception exception) {
			System.out.println("Exceptie la citirea din fieser, patroane !!");
			exception.printStackTrace();
		}

		return map;
	}

	/**
	 * Method that returns an array with the dimensions of a map
	 * 
	 * @param fileName
	 *            The name of the file where to search
	 * @return An array with the height of the map on the first position and
	 *         it's width on the second
	 */
	public int[] findMapSize(String fileName) {
		int mapHeight;
		int mapWidth;

		int[] vect = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			mapHeight = Integer.parseInt(br.readLine());
			mapWidth = Integer.parseInt(br.readLine());
			vect = new int[2];
			vect[0] = mapHeight;
			vect[1] = mapWidth;

			br.close();
		} catch (Exception exception) {
			System.out.println("Exceptie la citirea din fieser, patroane !!");
			exception.printStackTrace();
		}
		return vect;
	}

	/**
	 * Method which determine the current height of the player
	 * 
	 * @return An integer representing the height of the player
	 */
	public int currentHeight() {
		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j < mapWidth; j++) {
				if ((map[i][j] == 9) || (map[i][j] == 7))
					return i;
			}
		}

		return 0;
	}

	/**
	 * Method which determine the current width of the player
	 * 
	 * @return An integer representing the width of the player
	 */
	public int currentWidth() {
		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j < mapWidth; j++) {
				if ((map[i][j] == 9) || (map[i][j] == 7))
					return j;
			}
		}
		return 0;
	}

	/**
	 * Method that return an array with the modifications indicated made Every
	 * single move is a rearangement of three positions: 0, 1, 2 The position 2
	 * is where the player is located The position 0 and 1 are modified depends
	 * of objects that are located there. EG: 2 represents the player 1
	 * represents a box 0 represents an empty space After the move, 2 goes to
	 * 1,.. 1 to 0 and 0 to 2
	 * 
	 * @param newMap
	 *            The current map that will be modified
	 * @param pos0h
	 *            Height of the first position
	 * @param pos0w
	 *            Width of the first position
	 * @param pos1h
	 *            Height of the second position
	 * @param pos1w
	 *            Width of the second position
	 * @param pos2h
	 *            Height of the third position
	 * @param pos2w
	 *            Width of the third positon
	 * @return A matrix rearanged
	 */
	public int[][] rearange(int[][] newMap, int pos0h, int pos0w, int pos1h, int pos1w, int pos2h, int pos2w) {
		String move;
		if ((newMap[pos1h][pos1w] == 0) && (newMap[pos2h][pos2w] == 9)) {
			move = "s09";
		} else if ((newMap[pos1h][pos1w] == 3) && (newMap[pos2h][pos2w] == 9)) {
			move = "s39";
		} else if ((newMap[pos1h][pos1w] == 3) && (newMap[pos2h][pos2w] == 7)) {
			move = "s37";
		} else if ((newMap[pos1h][pos1w] == 0) && (newMap[pos2h][pos2w] == 7)) {
			move = "s07";
		} else if (newMap[pos1h][pos1w] != 1) {
			move = Integer.toString((newMap[pos0h][pos0w])) + Integer.toString((newMap[pos1h][pos1w]))
					+ Integer.toString(newMap[pos2h][pos2w]);
		} else {
			move = " ";
		}

		switch (move) {
		case "s09":
			newMap[pos1h][pos1w] = 9;
			newMap[pos2h][pos2w] = 0;
			steps++;
			break;
		case "s07":
			newMap[pos1h][pos1w] = 9;
			newMap[pos2h][pos2w] = 3;
			steps++;
			break;
		case "s37":
			newMap[pos1h][pos1w] = 7;
			newMap[pos2h][pos2w] = 3;
			steps++;
			break;
		case "s39":
			newMap[pos1h][pos1w] = 7;
			newMap[pos2h][pos2w] = 0;
			steps++;
			break;

		case "029":
			newMap[pos0h][pos0w] = 2;
			newMap[pos1h][pos1w] = 9;
			newMap[pos2h][pos2w] = 0;
			steps++;
			objectsMoved++;
			break;
		case "329":
			newMap[pos0h][pos0w] = 8;
			newMap[pos1h][pos1w] = 9;
			newMap[pos2h][pos2w] = 0;
			steps++;
			objectsMoved++;
			break;
		case "089":
			newMap[pos0h][pos0w] = 2;
			newMap[pos1h][pos1w] = 7;
			newMap[pos2h][pos2w] = 0;
			steps++;
			objectsMoved++;
			break;

		case "389":
			newMap[pos0h][pos0w] = 8;
			newMap[pos1h][pos1w] = 7;
			newMap[pos2h][pos2w] = 0;
			steps++;
			objectsMoved++;
			break;

		case "027":
			newMap[pos0h][pos0w] = 2;
			newMap[pos1h][pos1w] = 9;
			newMap[pos2h][pos2w] = 3;
			steps++;
			objectsMoved++;
			break;
		case "327":
			newMap[pos0h][pos0w] = 8;
			newMap[pos1h][pos1w] = 9;
			newMap[pos2h][pos2w] = 3;
			steps++;
			objectsMoved++;
			break;
		case "087":
			newMap[pos0h][pos0w] = 2;
			newMap[pos1h][pos1w] = 7;
			newMap[pos2h][pos2w] = 3;
			steps++;
			objectsMoved++;
			break;
		case "387":
			newMap[pos0h][pos0w] = 8;
			newMap[pos1h][pos1w] = 7;
			newMap[pos2h][pos2w] = 3;
			steps++;
			objectsMoved++;
			break;
		default:
			break;
		}

		return newMap;
	}

	/**
	 * Realize the necessary move
	 * 
	 * @param newMap
	 *            Current map
	 * @param currentHeight
	 *            Current height of the map
	 * @param currentWidth
	 *            Current width of the map
	 * @param direction
	 *            Parameter that determine the direction of the moving: 0 for
	 *            Left, 1 for Right, 2 for Up, 3 for Down
	 * @return A matrix representing the map after a move made
	 */
	public int[][] move(int[][] newMap, int currentHeight, int currentWidth, int direction) {

		switch (direction) {
		default:
		case 0:
			newMap = rearange(newMap, currentHeight, currentWidth - 2, currentHeight, currentWidth - 1, currentHeight,
					currentWidth);
			break;
		case 1:
			newMap = rearange(newMap, currentHeight, currentWidth + 2, currentHeight, currentWidth + 1, currentHeight,
					currentWidth);
			break;
		case 2:
			newMap = rearange(newMap, currentHeight - 2, currentWidth, currentHeight - 1, currentWidth, currentHeight,
					currentWidth);
			break;
		case 3:
			newMap = rearange(newMap, currentHeight + 2, currentWidth, currentHeight + 1, currentWidth, currentHeight,
					currentWidth);
			break;
		}

		return newMap;
	}

	/**
	 * Method that determined if a level is finished or not
	 * 
	 * @param newMap
	 *            The matrix form of the map
	 * @param height
	 *            The height of the map1
	 * @param width
	 *            The width of the map
	 * @return 1 in case the level is done, 0 otherwise
	 * 
	 */
	public boolean checkIfDone(int[][] newMap, int height, int width) {
		int count = 0;
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				if (newMap[i][j] == 2)
					count++;
			}
		if (count == 0)
			return true;
		return false;
	}

	/**
	 * Getter for the number of objects moved
	 * 
	 * @return Integer representing the number of objects moved
	 */
	public int getObjectsMoved() {
		return this.objectsMoved;
	}

	/**
	 * Getter for the number of steps made
	 * 
	 * @return Integer representing the number of steps made
	 */
	public int getSteps() {
		return this.steps;
	}
}
