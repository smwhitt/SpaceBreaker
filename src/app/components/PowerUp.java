package app.components;

import app.app;
import app.screens.Level;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * @author Samantha Whitt
 * used for creating power ups on the screen where the brick was
 * "activates" power ups accordingly
 * example: when an object breaks, power up will be there as a property of the brick
 */
public class PowerUp {
    private ImageView myPowerUp;
    private int myPowerUpType;

    /**
     * constructor for PowerUp that creates an ImageView corresponding to the
     * provided power up type (what special power it does upon activation)
     * example: PowerUp newPowerUp = new PowerUp(1);
     * @param powerUpType
     */
    public PowerUp(int powerUpType) {
        myPowerUpType = powerUpType;
        if (powerUpType == 0) {
            Image live_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.LIVESPOWER_IMAGE));
            myPowerUp = new ImageView(live_img);
        } else if (powerUpType == 1) {
            Image paddle_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.PADDLEPOWER_IMAGE));
            myPowerUp = new ImageView(paddle_img);
        } else if (powerUpType == 2) {
            Image size_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.SIZEPOWER_IMAGE));
            myPowerUp = new ImageView(size_img);
        } else if (powerUpType == 3) {
            Image time_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.TIMEPOWER_IMAGE));
            myPowerUp = new ImageView(time_img);
        } else if (powerUpType == 4) {
            Image velocity_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.VELOCITYPOWER_IMAGE));
            myPowerUp = new ImageView(velocity_img);
        }
    }

    /**
     * used for adding to scene
     * @return powerUps's ImageView
     */
    public ImageView getImage() {
        return myPowerUp;
    }

    /**
     * sets powerUp's current x location
     * @param location
     */
    public void setX(double location) {
        myPowerUp.setX(location);
    }

    /**
     * @return powerUp's current y position
     */
    public double getY() {
        return myPowerUp.getY();
    }

    /**
     * sets powerUp's current x location
     * @param location
     */
    public void setY(double location) {
        myPowerUp.setY(location);
    }

    /**
     * @return powerUp's ImageView width
     */
    public double getWidth() {
        return myPowerUp.getBoundsInLocal().getWidth();
    }

    /**
     * @return powerUp's ImageView height
     */
    public double getHeight() {
        return myPowerUp.getBoundsInLocal().getHeight();
    }

    /**
     * @return powerUp's type
     */
    public int getPowerUpType() {
        return myPowerUpType;
    }

    /**
     * "activates" the powerUp corresponding to the powerUp type
     * example: if 0, it gives an extra life by updating the round's lives +1
     * example: if 1, increases width of paddle by stretching paddle's ImageView
     * @param powerUp
     * @param myPaddle
     * @param myBouncer
     * @param lives
     * @param timeAllowed
     * @param velocity
     * @return changed variable activated by the powerUp or 0 if nothing needed to be changed
     */
    public int activatePowerUp(PowerUp powerUp, Paddle myPaddle, Ball myBouncer, int lives, int timeAllowed, int velocity) {
        int res = 0;
        int powerUpType = powerUp.getPowerUpType();
        if (powerUpType == 0) {
            lives += 1;
            res = lives;
        } else if (powerUpType == 1) {
            myPaddle.getImage().setFitWidth(myPaddle.getWidth() + 15);
        } else if (powerUpType == 2) {
            myBouncer.getImage().setFitWidth(myBouncer.getWidth() + 5);
            myBouncer.getImage().setFitHeight(myBouncer.getHeight() + 5);
        } else if (powerUpType == 3) {
            timeAllowed += 10;
            res = timeAllowed;
        } else if (powerUpType == 4) {
            velocity -= 25;
            res = velocity;
        }
        return res;
    }
}
