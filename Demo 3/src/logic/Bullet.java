package logic;

/**
 * This class is used to generate a bullet or projectile to be shot by the tank.
 * 
 * @author Group 7, adapted from Almas Baimagambetov: https://www.youtube.com/
	 watch?v=l2XhUHW8Oa4&list=PLurZmf6mNWh4oNzAph6vk14xj9NdS-RCP&index=2&t=0s
 * @version 1.0
 * @since 2019-03-06
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Bullet extends KinematicEntity {
	//Makes the bullet temporary
	private int lifeTime = 300; // in milliseconds
	public static final char symbol = '*'; //The bullet for text based version
	private static int radius = 3;

	/**
	 * Constructs a bullet for the text based version of the game
	 * @param x the x coordinate in the array
	 * @param y the y coordinate in the array
	 */
	public Bullet(){
		super(new Circle(3, 3, radius, Color.BLACK));
	}

	/**
	 * Constructs a bullet for the text based version of the game
	 * @param x the x coordinate in the array
	 * @param y the y coordinate in the array
	 */
	public Bullet(int x, int y){
		super(x, y);
	}


	/**
	 * @return lifeTime of bullet
	 */
	public int getLifeTime(){
		return lifeTime;
	}

	/**
	 *set the lifetime of the bullet
	 *@param int lifeTime
	 */
	public void setLifeTime(int lifeTime){
		this.lifeTime = lifeTime;
	}

	/**
	 * reduces the Lifetime of the bullet
	 */
	public void reduceLifeTime(){
		lifeTime--;
	}
	}
