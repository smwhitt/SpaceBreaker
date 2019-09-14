package app.components;

import app.app;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Samantha Whitt
 * used for creating a sun on the screen
 * example: show a sun for end screen
 */
public class Sun {
    ImageView mySun;

    /**
     * constructor for ShootingStar that creates a new Sun
     * example: Sun newSun = new Sun();
     */
    public Sun() {
        Image sun_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.SUN_IMAGE));
        mySun = new ImageView(sun_img);
    }

    /**
     * used for adding to scene
     * @return sun's ImageView
     */
    public ImageView getImage() {
        return mySun;
    }

    /**
     * sets sun's current x location
     * @param location
     */
    public void setX(double location) {
        mySun.setX(location);
    }

    /**
     * sets sun's current y location
     * @param location
     */
    public void setY(double location) {
        mySun.setY(location);
    }

    /**
     * @return sun's ImageView width
     */
    public double getWidth() {
        return mySun.getBoundsInLocal().getWidth();
    }
}
