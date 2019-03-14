package codes;

/**
 *The map class creates a 2D visual representation of the game space
 *@author Team 7
 *@version 1.0
 *@since Feb. 19th, 2019
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map{
	private char[][] charMap;
	private int height = 0, width = 0;

	/**
	 * Constructs a 2D character array from a specified textfile throws
	 * FileNotFoundException if textfile is not valid # in textfile represents a
	 * wall 0 in textfile represents space
	 * 
	 * @param String textPath
	 * @return character double array representing a 2D map
	 */
	public Map(String textPath) throws FileNotFoundException {
		File inFile = new File(textPath);
		Scanner input = new Scanner(inFile);
		ArrayList<String> stringMap = new ArrayList<String>();

		while (input.hasNextLine()) {
			stringMap.add(input.nextLine());
			height++;
		}
		input.close();

		width = stringMap.get(0).length();
		charMap = new char[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (stringMap.get(y).charAt(x) == '0') {
					charMap[y][x] = ' ';
				} else {
					charMap[y][x] = stringMap.get(y).charAt(x);
				}
			}
		}

	}

	/**
	 * @return the 2D character array
	 */
	public char[][] getCharMap() {
		return charMap;
	}

	/**
	 * sets the 2D character array
	 */
	public void setCharMap(char[][] charMap) {
		this.charMap = charMap;
	}

	/**
	 * @return the height of the 2D map
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * sets the height of the 2D map
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return width of 2D map
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * sets the width of the map
	 */
	public void setWidth(int width) {
		this.width = width;
	}


}
