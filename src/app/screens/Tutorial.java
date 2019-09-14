package app.screens;

import app.app;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * @author Samantha Whitt
 * first screen of the game with instructions on how to play
 * example: create a beginning splash screen
 */
public class Tutorial {
    Scene myStart;

    /**
     * constructor for Tutorial that creates a beginning scene with instructions
     * example: Tutorial newTutorial= new Tutorial(width, height, color);
     * @param sceneWidth
     * @param sceneHeight
     * @param background
     */
    public Tutorial(int sceneWidth, int sceneHeight, Paint background) {
        myStart = startTutorial(sceneWidth, sceneHeight, background);
    }

    /**
     * creates the Tutorial scene with the title of the game and instructions as text
     * @param sceneWidth
     * @param sceneHeight
     * @param background
     * @return tutorial's scene
     */
    public Scene startTutorial(int sceneWidth, int sceneHeight, Paint background) {
        StackPane root = new StackPane();
        Text title = new Text();
        title.setText("\n\n SPACE BREAKER");
        SpecialFont.makeHeader(title);
        SpecialFont.makeTextFont(title);
        StackPane.setAlignment(title, Pos.TOP_CENTER);

        Text instructions = new Text();
        instructions.setText("\n\nYou're on a mission to collect all of space's\n" +
                "treasures. Stars take 1 hit, blue planets\ntake 2, and purple asteroids take 3. " +
                "Make sure\nto do so before the sun burns out in 3\nminutes and 08 seconds!" +
                "\nBe aware that as you travel, space gets faster,\n" +
                "so collect some power ups from the planets to \nhelp you on your journey." +
                "\n Also look out for wormholes that \nlet you warp and DON'T collect falling stars!" +
                "\n\nPress the SPACEbar to begin");
        SpecialFont.makePlainText(instructions);

        root.getChildren().addAll(title, instructions);
        return new Scene(root, sceneWidth, sceneHeight, background);
    }

    /**
     * used for switching stage's scene
     * @return tutorial's scene
     */
    public Scene getScene() {
        return myStart;
    }
}