package app.screens;

import app.app;
import app.components.*;
import app.Setup;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * @author Samantha Whitt
 * Refactored Code: This design is not perfect but better than the previous one because I created an abstracted class
 * called Setup of which this class inherits all the setup methods from. It makes this class cleaner and shorter
 * because it takes away the responsibilities of setting up individual components so that it can simply deal with
 * putting the elements together. Ideally, addPowerUp() and the delete functions would be in their respective classes;
 * however, when trying to move them it caused many breaks in the game, so I decided to leave that for now. Overall
 * though by using inheritance, I was able to reduce the amount of code and add to the class hierarchy. I also
 * eliminated some code smells by making classes private, deleting duplicate code in creating new methods, and reducing
 * parameters in the superclass' new methods.
 *
 * Class Description: Handles everything relating to setting up the scene, updating it, and creating new ones:
 * includes ball, paddle, bricks, stats, power ups, and shooting stars
 * example: needs a new screen with all the components
 */
public class Level extends Setup {
    private Scene myLevel;
    private ArrayList<Brick> myBricks;
    private ArrayList<Brick> myStars;
    private ArrayList<Wormhole> myWormholes;
    private Ball myBouncer;
    private Paddle myPaddle;
    private Text livesCount = new Text();
    private Text time = new Text();
    private Text score = new Text();
    private static Group myRoot;

    /**
     * constructor for Level that initializes a new scene with all the components depending on the level
     * example: Fail newFail= new Fail(level, width, height, color, lives, time, score);
     * @param level
     * @param sceneWidth
     * @param sceneHeight
     * @param background
     * @param lives
     * @param time
     * @param score
     */
    public Level(String level, int sceneWidth, int sceneHeight, Paint background, int lives, int time, int score) {
        myBricks = super.getBricks();
        myStars = super.getStars();
        myWormholes = super.getWormholes();
        myBouncer = new Ball();
        myPaddle = new Paddle();
        myLevel = setUpScene(level, sceneWidth, sceneHeight, background, lives, time, score);
    }

    /**
     * adds new element to the scene
     * @param newImage
     */
    public static void addToRoot(ImageView newImage) {
        myRoot.getChildren().add(newImage);
    }

    /**
     * adds a powerUp on the screen at given location
     * @param newPower
     * @param x
     * @param y
     */
    public void addPowerUp(PowerUp newPower, double x, double y) {
        newPower.getImage().setX(x-(newPower.getWidth()/2));
        newPower.getImage().setY(y-(newPower.getHeight()/2));
        myRoot.getChildren().add(newPower.getImage());
    }

    /**
     * deletes powerUp from screen
     * @param powerUps
     * @param powerUp
     * @return updated arrayList of all the powerUps
     */
    public ArrayList<PowerUp> deletePowerUp(ArrayList<PowerUp> powerUps, PowerUp powerUp) {
        powerUp.getImage().setImage(null);
        powerUps.remove(powerUp);
        return powerUps;
    }

    /**
     * deletes brick from screen
     * @return updated arrayList of all the bricks
     */
    public ArrayList<Brick> deleteBrick(ArrayList<Brick> bricks, Brick brick) {
        brick.getImage().setImage(null);
        bricks.remove(brick);
        return bricks;
    }

    /**
     * deletes shooting star from screen
     * @param shootingStars
     * @param shootingStar
     * @return update arrayList of all the shooting stars
     */
    public ArrayList<ShootingStar> deleteShootingStar(ArrayList<ShootingStar> shootingStars, ShootingStar shootingStar) {
        shootingStar.getImage().setImage(null);
        shootingStars.remove(shootingStar);
        return shootingStars;
    }

    /**
     * @return level's scene
     */
    public Scene getScene() {
        return myLevel;
    }

    /**
     * used for resetting/updating lives stat on screen
     * @return level's current lives
     */
    public Text getLivesCount() {
        return livesCount;
    }

    /**
     * used for resetting/updating score stat on screen
     * @return level's current score
     */
    public Text getScore() {
        return score;
    }

    /**
     * used for resetting/updating score stat on screen
     * @return level's current remaining time
     */
    public Text getTime() {
        return time;
    }

    /**
     * @return level's width
     */
    public double getWidth() {
        return myLevel.getWidth();
    }

    /**
     * @return level's height
     */
    public double getHeight() {
        return myLevel.getHeight();
    }

    /**
     * @return level's ball
     */
    public Ball getBall() {
        return myBouncer;
    }

    /**
     * @return level's paddle
     */
    public Paddle getPaddle() {
        return myPaddle;
    }

    /**
     * creates the layout of the level with its paddle, ball, bricks, wormholes (if needed), and stats
     * @param level
     * @param width
     * @param height
     * @param color
     * @param lives
     * @param currTime
     * @param currScore
     * @return level's scene
     */
    private Scene setUpScene(String level, int width, int height, Paint color, int lives, int currTime, int currScore) {
        myRoot = new Group();

        myPaddle.resetPaddlePosition(width, height);
        myBouncer.resetBallPosition(myPaddle.getMidX()-(myBouncer.getWidth()/2), myPaddle.getY()-myBouncer.getHeight());

        super.setUpBricks(level);
        for (Brick brick: myBricks) {
            addToRoot(brick.getImage());
        }
        super.setUpWormholes(level);
        for (Wormhole wormhole: myWormholes) {
            addToRoot(wormhole.getImage());

        }
        addToRoot(myBouncer.getImage());
        addToRoot(myPaddle.getImage());

        ArrayList<Text> shownTexts = new ArrayList<>();
        shownTexts.add(SpecialFont.setSceneText(livesCount, "Lives: " + lives, 18, height-25));
        shownTexts.add(SpecialFont.setSceneText(time, "Time: " + Timer.getMinutes(currTime) + " min " + Timer.getSeconds(currTime) + " sec", 250, 25));
        shownTexts.add(SpecialFont.setSceneText(score, "SCORE: " + currScore, 18, 25));

        for (Text shownText: shownTexts) {
            SpecialFont.makePlainText(shownText);
            myRoot.getChildren().add(shownText);
        }
        return new Scene(myRoot, width, height, color);
    }
}
