//package codes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends GameEntity{

    Bullet(){
        super(new Circle(5, 5, 5, Color.BLACK));
    }
}