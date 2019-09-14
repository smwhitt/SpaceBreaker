package app.components;

import app.app;
import app.screens.Level;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * @author Samantha Whitt
 * used for creating shooting stars on the screen
 * example: create a shooting star to eventually fall down the scene
 */
public class ShootingStar {
    ImageView myShootingStar;

    /**
     * constructor for ShootingStar that creates a new shooting star
     * example: ShootingStar newShootingStar = new ShootingStar();
     */
    public ShootingStar() {
        Image shootingStar_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.STAR_IMAGE));
        myShootingStar = new ImageView(shootingStar_img);
    }

    /**
     * used for adding to scene
     * @return shootingStar's ImageView
     */
    public ImageView getImage() {
        return myShootingStar;
    }

    /**
     * @return shootingStar's current x location
     */
    public double getX() {
        return myShootingStar.getX();
    }

    /**
     * sets shooting star's x location
     * @param location
     */
    public void setX(double location) {
        myShootingStar.setX(location);
    }

    /**
     * @return shootingStar's current y location
     */
    public double getY() {
        return myShootingStar.getY();
    }

    /**
     * sets shooting star's y location
     * @param location
     */
    public void setY(double location) {
        myShootingStar.setY(location);
    }

    /**
     * @return myShootingStar's ImageView width
     */
    public double getWidth() {
        return myShootingStar.getBoundsInLocal().getWidth();
    }

    /**
     * @return myShootingStar's ImageView height
     */
    public double getHeight() {
        return myShootingStar.getBoundsInLocal().getHeight();
    }

    /**
     * places shooting stars from any of three places (random) and adds to scene
     * @return
     */
    public static void shootStar(ShootingStar newStar) {
        int randomStarPos = new Random().nextInt(3);
        if (randomStarPos == 0) {
            newStar.setX(100);
        } else if (randomStarPos == 1) {
            newStar.setX(200);
        } else if (randomStarPos == 2) {
            newStar.setX(300);
        }
        Level.addToRoot(newStar.getImage());
    }

}
