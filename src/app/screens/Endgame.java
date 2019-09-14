package app.screens;

import app.app;
import app.components.Sun;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * @author Samantha Whitt
 * final screen of the game showing game completed
 * example: create a congratulations screen at the end
 */
public class Endgame {
    Scene myEnd;
    Sun mySun;

    /**
     * constructor for Endgame that creates a new ending scene with stats
     * example: Endgame newEndgame= new Endgame(width, height, color, score, time);
     * @param sceneWidth
     * @param sceneHeight
     * @param background
     * @param score
     * @param time
     */
    public Endgame(int sceneWidth, int sceneHeight, Paint background, int score, int time) {
        myEnd = startEnd(sceneWidth, sceneHeight, background, score, time);
    }

    /**
     * creates the Endgame scene and adds text messages with stats and a sun
     * @param sceneWidth
     * @param sceneHeight
     * @param background
     * @param score
     * @param time
     * @return Endgame's scene
     */
    public Scene startEnd(int sceneWidth, int sceneHeight, Paint background, int score, int time) {
        Group root = new Group();
        Text congrats = new Text();
        congrats.setText("\n\n\n CONGRATULATIONS");
        SpecialFont.makeHeader(congrats);
        congrats.setX(55);
        congrats.setY(30);

        Text out = new Text();
        out.setText("YOU WON, \n SPACE BREAKER");
        SpecialFont.makeHeader(out);
        out.setX(75);
        out.setY(200);

        Text stats = new Text();
        SpecialFont.setStats(stats, score, time);
        SpecialFont.makePlainText(stats);
        stats.setX(130);
        stats.setY(250);

        mySun = new Sun();
        mySun.setX(sceneWidth/2 - mySun.getWidth()/2);
        mySun.setY(350);

        root.getChildren().addAll(congrats, out, stats, mySun.getImage());
        return new Scene(root, sceneWidth, sceneHeight, background);
    }

    /**
     * used for switching stage's scene
     * @return Endgame's scene
     */
    public Scene getScene() {
        return myEnd;
    }
}
