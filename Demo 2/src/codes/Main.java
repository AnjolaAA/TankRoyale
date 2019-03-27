package codes;


/**
 * This class is used to run the game
 * 
 * @author Group 7, adapted from Almas Baimagambetov: https://www.youtube.com/
	 watch?v=l2XhUHW8Oa4&list=PLurZmf6mNWh4oNzAph6vk14xj9NdS-RCP&index=2&t=0s
 * @version 1.0
 * @since 2019-03-06
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

    public static final double WIDTH = 1080.0, HEIGHT = 608.0; //16:9 Window Aspect Ratio
    private Game game = new Game();

    @Override
	
	/**
	 *Main entry point of JavaFX application
	 */
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tank Royale");
        stage.setResizable(false);

        game.start();
        Scene gameScene = new Scene(game.getRoot(), WIDTH, HEIGHT);

        stage.setScene(gameScene);
        gameScene.setOnKeyPressed(game.new PressHandler());
        gameScene.setOnKeyReleased(game.new ReleaseHandler());

        stage.show();
    }

	/**
	 *Launches the game
	 */
    public static void main(String[] args){
        launch(args);
    }

}