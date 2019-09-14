package app.screens;

import app.app;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * @author Samantha Whitt
 * final screen of the game showing game over if failed
 * example: create a stats screen at game over
 */
public class Fail {
    Scene myFail;

    /**
     * constructor for Fail that creates a new ending scene with stats
     * example: Fail newFail= new Fail(width, height, color, score, time, false);
     * @param sceneWidth
     * @param sceneHeight
     * @param background
     * @param score
     * @param time
     * @param ranOutOfTime
     */
    public Fail(int sceneWidth, int sceneHeight, Paint background, int score, int time, boolean ranOutOfTime) {
        myFail = startFailGame(sceneWidth, sceneHeight, background, score, time, ranOutOfTime);
    }

    /**
     * creates the Fail screen and adds text messages with stats
     * only shows text of player failing because of the sun if player ran out of time
     * @param sceneWidth
     * @param sceneHeight
     * @param background
     * @param score
     * @param time
     * @param ranOutOfTime
     * @return Fail's scene
     */
    public Scene startFailGame(int sceneWidth, int sceneHeight, Paint background, int score, int time, boolean ranOutOfTime) {
        Group root = new Group();
        Text fail = new Text();
        fail.setText("\n\n\n GAME OVER");
        SpecialFont.makeHeader(fail);
        fail.setX(105);
        fail.setY(30);

        Text stats = new Text();
        SpecialFont.setStats(stats, score, time);
        SpecialFont.makeHeader(stats);
        stats.setX(55);
        stats.setY(150);

        if (ranOutOfTime) {
            Text ranOut = new Text();
            ranOut.setText("that sun was just too hot \n better luck next time, Space Breaker");
            SpecialFont.makePlainText(ranOut);
            ranOut.setX(50);
            ranOut.setY(325);
            Image sun_img = new Image(this.getClass().getClassLoader().getResourceAsStream(app.SUN_IMAGE));
            ImageView sun = new ImageView(sun_img);
            sun.setX(200-(sun.getBoundsInLocal().getWidth()/2));
            sun.setY(380);
            root.getChildren().addAll(ranOut, sun);
        }
        root.getChildren().addAll(fail, stats);
        return new Scene(root, sceneWidth, sceneHeight, background);
    }

    /**
     * used for switching stage's scene
     * @return Fail's scene
     */
    public Scene getScene() {
        return myFail;
    }
}
