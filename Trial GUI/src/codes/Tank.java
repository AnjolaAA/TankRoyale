package codes;

/**
 * This class is used to generate a bullet or project to be shot by the tank.
 * The class extends GameEntity which tracks the bullets x and y coordinates in
 * the generated grid/array.
 * 
 * @author Team 7
 * @version 1.0
 * @since 2019-02-19
 */
 
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tank extends KinematicEntity {
    private Point2D facing = new Point2D(1,0);
    private int moveDir = 1; //1 means it last moved forward, -1, means it last moved backward
    private boolean isRotateRight = false, isRotateLeft = false;


    public Tank(){
        super(new Rectangle(40,30, Color.rgb(Game.rng(0,255),Game.rng(0,255),Game.rng(0,255))));
    }

    public void update() {
        if(isRotateRight){
            rotate(5);
            isRotateRight = false;
        }
        else if (isRotateLeft){
            rotate(-5);
            isRotateLeft = false;
        }
        else{
            getView().setTranslateX(getView().getTranslateX() + getVelocity().getX());
            getView().setTranslateY(getView().getTranslateY() + getVelocity().getY());
        }
    }


    public void setFacing(Point2D facing) {
        this.facing = facing;
    }

    public Point2D getFacing() {
        return facing;
    }

    private void move(double direction, double magnitude) {
        setVelocity(getFacing().normalize().multiply(direction*magnitude));
    }

    public void moveForward() {
        moveDir = 1;
        move(1, 2);
    }

    public void moveBackward() {
        moveDir = -1;
        move(-1, 2);
    }

    //the angle of rotation measured in degrees.
    private double getRotate() {
        return getView().getRotate();
    }

    private double getRotateToX(){
        return Math.cos(Math.toRadians(getView().getRotate()));
    }

    private double getRotateToY(){
        return Math.sin(Math.toRadians(getView().getRotate()));
    }

    private void rotate(double degrees){
        //Realization that it rotates independent of the animation timer, which can cause problems, then fixed it
        getView().setRotate(getView().getRotate() + degrees);
        setFacing(new Point2D(getRotateToX(), getRotateToY()));


        //These two ways below did not actually rotate the object at all, personal note for myself - Harry
        //setVelocity(new Point2D(Math.cos(Math.toRadians(getView().getRotate())), Math.sin(Math.toRadians(getView().getRotate()))));

        //getView().setTranslateX(getView().getTranslateX() + getRotateToX());
        //getView().setTranslateY(getView().getTranslateY() + getRotateToY());
    }

    public void rotateRight() {
        //rotate(5.0);
        isRotateRight = true; // Makes rotation dependent on timer
    }

    public void rotateLeft() {
        //rotate(-5.0);
        isRotateLeft = true; // Makes rotation dependent on timer
    }

    public int getMoveDir() {
        return moveDir;
    }
	
	
}
