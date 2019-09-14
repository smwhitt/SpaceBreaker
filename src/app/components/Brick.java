package app.components;

import app.app;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Samantha Whitt
 * used for creating bricks on the screen including their lives
 * sets power ups with certain bricks
 * example: create a star in the game with 1 life (needs 1 hit to break it)
 */
public class Brick {
    private ImageView myBrick;
    private PowerUp myPowerUp;
    private int brickLives;
    private int myBrickType;

    /**
     * constructor for Brick that creates an ImageView corresponding to the
     * provided brick type (how many lives the brick has)
     * example: Brick newBrick = new Brick(1);
     * @param brickType
     */
    public Brick(int brickType) {
        myBrickType = brickType;

        if (brickType == 1) {
            Image star_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.STAR_IMAGE));
            myBrick = new ImageView(star_img);
            brickLives = 1;
        } else if (brickType == 2) {
            Image planet_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.PLANET_IMAGE));
            myBrick = new ImageView(planet_img);
            brickLives = 2;
            // associates a random power up as a property of the planet
            int randomPowerUp = new Random().nextInt(5);
            myPowerUp = new PowerUp(randomPowerUp);
        } else if (brickType == 3) {
            Image asteroid_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.ASTEROID_IMAGE));
            myBrick = new ImageView(asteroid_img);
            brickLives = 3;
        }
    }

    /**
     * used for adding to scene
     * @return brick's ImageView
     */
    public ImageView getImage() {
        return myBrick;
    }

    /**
     * will be null if brickType == 1 or == 3
     * @return brick's power up
     */
    public PowerUp getPowerUp() {
        return myPowerUp;
    }

    /**
     * @return brick's type
     */
    public int getBrickType() {
        return myBrickType;
    }

    /**
     * @return brick's current x location
     */
    public double getX() {
        return myBrick.getX();
    }

    /**
     * sets brick's current x location
     * @param location
     */
    public void setX(double location) {
        myBrick.setX(location);
    }

    /**
     * @return brick's current y location
     */
    public double getY() {
        return myBrick.getY();
    }

    /**
     * sets brick's current y location
     * @param location
     */
    public void setY(double location) {
        myBrick.setY(location);
    }

    /**
     * @return brick's current x location at its midpoint
     */
    public double getMidX() {
        return getX()+(getWidth()/2);
    }

    /**
     * @return brick's current y location at its midpoint
     */
    public double getMidY() {
        return getY()+(getHeight()/2);
    }

    /**
     * @return brick's ImageView width
     */
    public double getWidth() {
        return myBrick.getBoundsInLocal().getWidth();
    }

    /**
     * @return brick's ImageView height
     */
    public double getHeight() {
        return myBrick.getBoundsInLocal().getHeight();
    }

    /**
     * if 0, brick should "break" and disappear from the scene
     * @return brick's lives
     */
    public int getBrickLives() {
        return brickLives;
    }

    /**
     * sets how many lives a brick has
     * @param newLives
     */
    public void setBrickLives(int newLives) {
        brickLives = newLives;
    }

}
