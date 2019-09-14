package app.components;

import app.app;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Samantha Whitt
 * used for creating a wormhole on the screen
 * example: show a wormhole at the left and/or right end of screen
 */
public class Wormhole {
    private ImageView myWormhole;

    /**
     * constructor for wormhole that creates a new one
     * example: Wormhole newWormhole = new Wormhole();
     */
    public Wormhole(String side) {
        Image wormhole_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.WORMHOLE_IMAGE));
        myWormhole = new ImageView(wormhole_img);
        if (side.equals("left")) {
            myWormhole.setX(0);
            myWormhole.setScaleX(-1);
        } else if (side.equals("right")) {
            myWormhole.setX(app.SCENE_WIDTH-getWidth());
        }
        myWormhole.setY(400);

    }

    /**
     * used for adding to scene
     * @return wormhole's ImageView
     */
    public ImageView getImage() {
        return myWormhole;
    }

    /**
     * @return wormhole's ImageView width
     */
    public double getWidth() {
        return myWormhole.getBoundsInLocal().getWidth();
    }
}
