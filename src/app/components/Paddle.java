package app.components;

import app.app;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Samantha Whitt
 * used for creating the paddle on the screen
 * example: create a paddle at the bottom for the ball to bounce off of
 */
public class Paddle {
    private ImageView myPaddle;

    /**
     * constructor for Paddle that creates a new paddle
     * example: Paddle newPaddle = new Paddle();
     */
    public Paddle() {
        Image paddle_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.PADDLE_IMAGE));
        myPaddle = new ImageView(paddle_img);
    }

    /**
     * used for adding to scene
     * @return paddle's ImageView
     */
    public ImageView getImage() {
        return myPaddle;
    }

    /**
     * @return paddle's current x location
     */
    public double getX() {
        return myPaddle.getX();
    }

    /**
     * sets paddle's current x location
     * @param location
     */
    public void setX(double location) {
        myPaddle.setX(location);
    }

    /**
     * @return paddle's current x location at its midpoint
     */
    public double getMidX() {
        return getX()+(getWidth()/2);
    }

    /**
     * @return paddle's current y location at its midpoint
     */
    public double getMidY() {
        return getY()+(getWidth()/2);
    }

    /**
     * @return paddle's current y location
     */
    public double getY() {
        return myPaddle.getY();
    }

    /**
     * sets paddle's current y location
     * @param location
     */
    public void setY(double location) {
       myPaddle.setY(location);
    }

    /**
     * @return paddle's ImageView width
     */
    public double getWidth() {
        return myPaddle.getBoundsInLocal().getWidth();
    }

    /**
     * @return paddle's ImageView height
     */
    public double getHeight() {
        return myPaddle.getBoundsInLocal().getHeight();
    }

    /**
     * resets paddle to the middle of the scene
     * @param width
     * @param height
     */
    public void resetPaddlePosition(int width, int height) {
        myPaddle.setX(width / 2 - getWidth() / 2);
        myPaddle.setY(height * 0.9 - getHeight() / 2);
    }

}
