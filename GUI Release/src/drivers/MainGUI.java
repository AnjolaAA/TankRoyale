package drivers;


/**
 * This class is used to run the GameGUI version of the game
 *
 * @Author Harry, Anjola, Mei, Joshua, Andre
 * @version 1.0
 * @since 2019-03-06
 */

import javafx.application.Application;
import javafx.stage.Stage;
import visuals.MenuGUI;

public class MainGUI extends Application {

    public static final double WIDTH = 1080.0, HEIGHT = 608.0; //16:9 Window Aspect Ratio

    /**
     * Main entry point of JavaFX application
     */
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tank Royale");
        stage.setResizable(false);

        MenuGUI menu = new MenuGUI();

        menu.start(stage);

        stage.show();
    }


    /**
     * Launches the game
     */
    public static void main(String[] args) {
        launch(args);
    }

}
