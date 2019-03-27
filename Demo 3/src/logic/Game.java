package logic;


/**
 * This game class handles map generation, shoot,and detect collisions. 
 * 
 * @author Group 7, adapted from Almas Baimagambetov: https://www.youtube.com/
	 watch?v=l2XhUHW8Oa4&list=PLurZmf6mNWh4oNzAph6vk14xj9NdS-RCP&index=2&t=0s
 * @version 1.0
 * @since 2019-03-06
 */
import visuals.*;
import drivers.*;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class Game {
	
    // Assumes 2 players
	private static int playerCount = 2;
	private static boolean gameStart = false;
	private boolean gameOver = false;
	private boolean roundOver = false;
	private AnimationTimer timer;
	private Map gamemap;
	private int p1score = 0;
	private int p2score = 0;
	private static boolean p1Shooting = false;
	private static boolean p2Shooting = false;


    //Arraylist of all Game Entities
    private static ArrayList<Tank> tanks = new ArrayList<>();
    private static ArrayList<Bullet> bullets = new ArrayList<>();
	private ArrayList<Wall> walls = new ArrayList<>();
	
	//Display variables
	private GUI visual = new GUI();
	
	//Not encapsulated
	public GUI getVisual(){
		return visual;
	}

	public static boolean getGameStart(){
		return gameStart;
	}

	public static void setGameStart(boolean start){
		gameStart = start;
	}

	public ArrayList<Tank> getTanks(){
		ArrayList<Tank> temp = new ArrayList<>();
		for(Tank tank : tanks)
			temp.add(tank);
		return temp;
	}

	public int getPlayerCount(){
		return playerCount;
	}

	/*public void setPlayerCount(int playerCount){
		this.playerCount = playerCount;
	}*/

	public void setP1Shooting(boolean isP1Shooting){
		p1Shooting = isP1Shooting;
	}

	public void setP2Shooting(boolean isP2Shooting){
		p2Shooting = isP2Shooting;
	}

	public boolean getP1Shooting(){
		return p1Shooting;
	}

	public void setGameOver(boolean state){
		this.gameOver = state;
	}
	
	/**
	 *Starts the game
	 */
    public void start(){
		try {
			createMap();
		}
		catch (Exception e) {
			System.out.println("Was unable to load in the map");
		}
		visual.displayTally(p1score, p2score);
		//Adds two player tanks to the map
		Tank player1 = new Tank();
		Tank player2 = new Tank();
		randomSpawn(player1);
		randomSpawn(player2);
			
		// Game Loop
		timer = new AnimationTimer() {
			@Override
			// Runs each frame
			public void handle(long now) { 
				onUpdate();
			}
		};
		timer.start();
    }
	
	/**
	 *Provide option for restart
	 */
    public void restart(){
		if(!gameOver){
			timer.stop();
			roundOver = false;
			int winner = determineWinner();
			updateScore(winner);
			visual.clear();
			tanks.clear();
			bullets.clear();
			walls.clear();
			start();
		}
		else{
			endScreen();
		}
	}

	public void endScreen(){
		timer.stop();
		gameOver = false;
		visual.clear();
		int winner = determineWinner();
		visual.createRestartButton();
		visual.announceWinner(winner);
		p1score = 0;
		p2score = 0;
		visual.getRestartBtn().setOnAction(e -> {
        tanks.clear();
        bullets.clear();
        walls.clear();
		start();
		});
	}
	
	public int determineWinner(){
		int winner = 0;
		for (int x = 0; x < tanks.size(); x++){
            if (tanks.get(x).isAlive()) {
                winner = x + 1;            
            }
        }
		return winner;
	}

	public void updateScore(int winner){
		for(Tank tank : tanks){
			if(tank.isDead()){
				switch(winner){
					case 0:
					break;
					case 1:
					p1score++;
					break;
					case 2:
					p2score++;
					break;		
				}
			}
		}
	}

	 /**
	 *Adds the view of a GameEntity to the Pane
	 *@param GameEntity entity, double x, double y
	 */
    private void addGameEntity(GameEntity entity, double x, double y){
        entity.getView().setTranslateX(x);
        entity.getView().setTranslateY(y);
        visual.addVisualGameEntity(entity);
    }
	
	/**
	 *Adds a Bullet to the Pane
	 *@param Bullet bullet, double x, double y
	 */
    private void addBullet(Bullet bullet, double x, double y){
        bullets.add(bullet);
        addGameEntity(bullet, x, y);
    }

	/**
	 *Adds a Tank to the Pane
	 @param Tank tank, double x, double y
	 */
    private void addTank(Tank tank, double x, double y){
        tanks.add(tank);
        addGameEntity(tank, x, y);
    }
	
	/**
	 *Adds a Wall to the Pane
	 @param Wall wall, double x, double y
	 */
	private void addWall(Wall wall, double x, double y){
        walls.add(wall);
        addGameEntity(wall, x, y);
    }
	
	/**
	 *Adds a horizontally orientated wall to the Pane
	 *@param int x, int y, double width, double height
	 */
	private void addHorizontalWall(int x, int y, double width, double height){
		if(y == (gamemap.getWidth() - 1)){
			addWall(new Wall(width + 1.0,10.0,1), x * width, (MainGUI.HEIGHT-100));
		}else{
			addWall(new Wall(width + 1.0,10.0,1),x *width, y *height);
		}
	}
	
	/**
	 *Adds a vertically orientated wall to the Pane
	 *@param int x, int y, double width, double height
	 */
	private void addVerticalWall(int x, int y, double width, double height){
		
		if(x == (gamemap.getHeight()-1)){
			addWall(new Wall(10.0,height + 10.0,0),MainGUI.WIDTH,y * height);
		}else{
			addWall(new Wall(10.0,height + 2.0 ,0),x * width,y * height);
			}
	}
	
	
	/**
	 * Creates the map of TankRoyale on JavaFX
	 * | are vertical walls
	 * # are horizontal walls
	 * ^ are corners
	 */
	 
	public void createMap() throws FileNotFoundException{
		gamemap = new Map("/resources/gui/maze.txt");
		char[][] map = gamemap.getCharMap();
		//Adjusting the height or width of the text file to fit the size of the javafx screen
		double height = (MainGUI.HEIGHT-100)/gamemap.getHeight();
		double width = MainGUI.WIDTH/gamemap.getWidth();
		for (int y = 0; y < gamemap.getHeight(); y++) {
			for (int x = 0; x < gamemap.getWidth(); x++) {
				switch(map[y][x]) {
					case ' ':
						break;
					case '^':
						addVerticalWall(x,y,width,height);
						addHorizontalWall(x,y,width,height);
					break;
					case '|':
						addVerticalWall(x,y,width,height);
						break;
					case '#':
						addHorizontalWall(x,y,width,height);
						break;
					
			}
		}
	}
	}
	
	private boolean checkPoint(Tank tank){
		for(Wall wall: walls){
			if(tank.isColliding(wall))
				return true;
		}
		return false;
	}
	
	private void spawn(Tank tank){
		double x = (double)rng(40, (int)MainGUI.WIDTH-40);
		double y = (double)rng(40, (int)MainGUI.HEIGHT-140);
		addTank(tank, x , y );
	}

    /**
	 *Future method to provide valid spawn points
	 */
    private void randomSpawn(Tank tank){
		spawn(tank);
		while(checkPoint(tank)){
			visual.removeTank(tank);
            tanks.clear();
			spawn(tank);
		}
    }

	/**
	 *Updates the game state
	 */
    public void onUpdate(){
        if(!roundOver){
            detectCollisions();
            clearDeadBullets();
			checkRoundOver();
			checkGameOver();
			shooting();

            // A type of lambda expression: parameter -> expression
            bullets.forEach(bullet -> bullet.update());
			bullets.forEach(bullet -> bullet.reduceLifeTime());
			//tanks.forEach(tank -> tank.update());
			for(Tank tank: tanks){
				if(!checkPoint(tank))
					 tank.update();
				else{
					tank.stop();
				}
			}
        }
        else {
            restart();
        }      

    }
	
	
	/**
	 *Detects Collisions between bullets,tanks, and walls walls.
	 */
    private void detectCollisions(){
		
        for(Bullet bullet : bullets){
			//Detects collision of bullet with tank
            for(Tank tank : tanks){
                if(bullet.isColliding(tank)){
                    bullet.setAlive(false);
                    tank.setAlive(false);
                    //Removes collided entities from the layout
					visual.removeAll(bullet,tank);
                }
				else if(bullet.getLifeTime() == 0){
                    bullet.setAlive(false);
                    visual.removeBullet(bullet);
            }
			}
			
			// Detects collision of bullet with wall
	        for(Wall wall : walls){
				// Work in progress, sometimes bullet glitches through
               if(bullet.isColliding(wall)){
                    ricochet(wall, bullet);
               }
       	    }
        }
		
		//Detects collision with walls and tanks
        for (GameEntity wall : walls) {
            for(Tank tank : tanks){
                if (tank.isColliding(wall)) {
                    tank.setVelocity(new Point2D(0,0));
					tank.stop();
                    tank.getView().setTranslateX(tank.getView().getTranslateX() - tank.getFacing().getX()*tank.getMoveDir() );
                    tank.getView().setTranslateY(tank.getView().getTranslateY() - tank.getFacing().getY()*tank.getMoveDir());

                }
            }
        }
		}


	/**
	 *Richochets bullets off of a wall
	 *@param Wall wall, Bullet bullet
	 */
    private void ricochet(Wall wall, Bullet bullet){
        if (bullet.isColliding(wall)) {
            if(wall.getAlignment()== 0 || wall.getAlignment() == 0){
                bullet.setVelocity(new Point2D(-1*bullet.getVelocity().getX(), bullet.getVelocity().getY()));
            }
            else if(wall.getAlignment() == 1 || wall.getAlignment() == 1){
                bullet.setVelocity(new Point2D(bullet.getVelocity().getX(), -1*bullet.getVelocity().getY()));
            }
        }
    }



    /**
	 *Removes dead entities from the arraylists
	 *Dead tanks must be replaced, so that the arraylist order doesn't change
     *and tanks have to be removed from the arraylist so that the non-visual bounds of it are removed completely from the Pane
	 */
    private void clearDeadBullets(){
        bullets.removeIf(bullet -> bullet.isDead());
        
        for(int x = 0; x < tanks.size(); x++){
            if(tanks.get(x).isDead()){
                Tank temp = new Tank();
                temp.setAlive(false);
                tanks.set(x, temp);
            }
        }
    }

	/**
	 *Checks if both tanks are still alive
	 */
    private void checkRoundOver(){
        int count = 0;
        for(GameEntity tank : tanks){
            if(tank.isAlive()){
                count++;
            }
        }
        if (count <= 1) {
            roundOver = true;
        }
	}
	
	public void checkGameOver(){
		if(p1score == 9 || p2score == 9)
			gameOver = true;
	}

	public void shoot(Tank tank){
			Bullet bullet = tank.shoot();
			double x = tank.getView().getTranslateX() + tank.getFacing().normalize().multiply(40).getX() + 15;
			double y = tank.getView().getTranslateY() + tank.getFacing().normalize().multiply(40).getY() + 10;
			addBullet(bullet,x,y);
	}

	public void shooting(){
		if(p1Shooting == true){
			p1Shooting = false;
			shoot(tanks.get(0));
		}
		if(p2Shooting == true){
			p2Shooting = false;
			shoot(tanks.get(1));
		}
	}
	
	public static int rng(int min, int max) {
		if (min > max) { 
		// Argument Error Trap
			int temp = min;
			min = max;
			max = temp;
		}
		int number = (int) (Math.random() * (max - min + 1) + min);
		return number;
	}
}