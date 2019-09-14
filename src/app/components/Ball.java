package app.components;

import app.app;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Samantha Whitt
 * used for creating balls/bouncers on the screen
 * gets and updates position of the ball
 * example: create a ball in an animation with specific image
 */
public class Ball extends Node {
    private ImageView myBall;

    /**
     * constructor of the ball class -- creates new Ball
     * example: Ball newBall = new Ball();
     */
    public Ball() {
        Image ball_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.BOUNCER_IMAGE));
        myBall = new ImageView(ball_img);
    }

    /**
     * used for adding to the scene
     * @return ball's ImageView
     */
    public ImageView getImage() {
        return myBall;
    }

    /**
     * @return ball's current x location
     */
    public double getX() {
        return myBall.getX();
    }

    /**
     * sets the ball's current x location
     * if not in bounds of the scene, ball will not show
     * @param location
     */
    public void setX(double location) {
        myBall.setX(location);
    }

    /**
     * @return ball's current x location at its midpoint
     */
    public double getMidX() {
        return getX()+(getWidth()/2);
    }

    /**
     * @return ball's current y location
     */
    public double getY() {
        return myBall.getY();
    }

    /**
     * sets the ball's current y location
     * if not in bounds of the scene, ball will not show
     * @param location
     */
    public void setY(double location) {
        myBall.setY(location);
    }

    /**
     * @return ball's ImageView width
     */
    public double getWidth() {
        return myBall.getBoundsInLocal().getWidth();
    }

    /**
     * @return ball's ImageView height
     */
    public double getHeight() {
        return myBall.getBoundsInLocal().getHeight();
    }

    /**
     * resets ball to the middle of the scene
     * @param paddleX
     * @param paddleY
     */
    public void resetBallPosition(double paddleX, double paddleY) {
        myBall.setX(paddleX);
        myBall.setY(paddleY);
    }
}
