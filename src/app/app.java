package app;

import app.components.*;
import app.screens.Endgame;
import app.screens.Fail;
import app.screens.Level;
import app.screens.Tutorial;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

/**
 * @author Samantha Whitt, Robert Duvall
 * Main app class handles running the game/application:
 * initializes rules, makes the ball moves, listens for key inputs,
 * acts according to how things move (collision), and special features
 * example: creating a new game application and running per step
 **/
public class app extends Application{
    public static final String TITLE = "Space Breaker";
    public static final int SCENE_WIDTH = 400, SCENE_HEIGHT = 500, FRAMES_PER_SECOND = 60,
            MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.BLACK;
    public static final String BOUNCER_IMAGE = "ball.gif", PADDLE_IMAGE = "paddle.gif", STAR_IMAGE = "star.png",
            PLANET_IMAGE = "earth.png", ASTEROID_IMAGE = "asteroid.png", SUN_IMAGE = "sun.png",
            LIVESPOWER_IMAGE = "livespower.gif", PADDLEPOWER_IMAGE = "paddlepower.gif",
            SIZEPOWER_IMAGE = "sizepower.gif", TIMEPOWER_IMAGE = "timepower.png", WORMHOLE_IMAGE = "wormhole.png",
            VELOCITYPOWER_IMAGE = "velocitypower.gif", LEVEL1 = "doc/level1.txt", LEVEL2 = "doc/level2.txt",
            LEVEL3 = "doc/level3.txt";
    public static final int POWERUP_SPEED = 50, SHOOTINGSTAR_SPEED = 50, PADDLE_SPEED = 15;

    private Tutorial myTutorial;
    private Level myScene;
    private Stage myStage;
    private Timer myTimer;
    private Endgame myEnd;
    private Fail myFail;
    private Ball myBouncer;
    private Paddle myPaddle;
    private ArrayList<Brick> bricks;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<ShootingStar> shootingStars;
    private Timeline animation;
    private boolean playing = false;
    private int bouncerSpeed = 50, dx = 1, dy = 1, currScene = 0, myLives, timeAllowed = 188, myScore = 0, myTime = 0,
            timeLeft, betweenSecondsCounter = 0;

    /**
     * initializes the stage including: title, width and height of the screen, and shows them
     * @param stage
     */
    @Override
    public void start (Stage stage) {
        myStage = stage;
        myTutorial = new Tutorial(SCENE_WIDTH, SCENE_HEIGHT, BACKGROUND);
        stage.setScene(myTutorial.getScene());
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.show();

        // press spacebar to begin
        myTutorial.getScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    /**
     * sets up the game by creating and switching into levels and initializing components of the level
     */
    public void setupGame() {
        playing = false;
        currScene += 1;
        bouncerSpeed += 25;
        myLives = 3;
        String levelName = "";
        if (currScene == 1) {
            levelName = LEVEL1;
        } else if (currScene == 2) {
            levelName = LEVEL2;
        } else if (currScene == 3) {
            levelName = LEVEL3;
        }

        myScene = new Level(levelName, SCENE_WIDTH, SCENE_HEIGHT, BACKGROUND, myLives, timeAllowed-myTime, myScore);
        myStage.setScene(myScene.getScene());
        myBouncer = myScene.getBall();
        myPaddle = myScene.getPaddle();
        bricks = myScene.getBricks();
        powerUps = new ArrayList<>();
        shootingStars = new ArrayList<>();

        // press spacebar to shoot ball off
        myScene.getScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    /**
     * attaches a game loop to the animation's timeline and continuously calls step()
     */
    public void gameLoop() {
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
        playing = true;
        // starts timer whenever animation starts
        myTimer = new Timer();
        myTimer.startTimer();
    }

    /**
     * changes properties of the app components over time constantly
     * checks to see whether or not to end animation/switch scenes etc.
     * @param elapsedTime
     */
    private void step (double elapsedTime) {
        warp(myPaddle, currScene);
        fallDown(elapsedTime);
        int[] speeds = collide(myBouncer, myPaddle, dx, dy, myScene);
        dx = speeds[0];
        dy = speeds[1];
        updateStats();
        fell();
        if (timeLeft <= 0) gameOver();

        // move paddle (according to keys) and ball
        myBouncer.setX(myBouncer.getX() + bouncerSpeed * elapsedTime * dx);
        myBouncer.setY(myBouncer.getY() + bouncerSpeed * elapsedTime * dy);
        myScene.getScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    /**
     * responds to a key being pressed
     * @param code
     */
    public void handleKeyInput(KeyCode code) {
        if (!playing) {
            if (code == KeyCode.SPACE) {
                if (currScene == 0) {
                    setupGame();
                } else if (currScene > 0) {
                    gameLoop();
                }
            }
        } else {
            if (code == KeyCode.RIGHT && myPaddle.getX() < myScene.getWidth() - myPaddle.getWidth()) {
                myPaddle.setX(myPaddle.getX() + PADDLE_SPEED);
            } else if (code == KeyCode.LEFT && myPaddle.getX() > 0) {
                myPaddle.setX(myPaddle.getX() - PADDLE_SPEED);
            }
            else if (code == KeyCode.L) {
                myLives += 1;
            } else if (code == KeyCode.S) {
                for (Brick star: myScene.getStars()) {
                    bricks = myScene.deleteBrick(bricks, star);
                }
            } else if (code == KeyCode.Z) {
                ArrayList<Brick> clearBricks = (ArrayList<Brick>) bricks.clone();
                for (Brick brick: clearBricks) {
                    bricks = myScene.deleteBrick(bricks, brick);
                }
                bricks.clear();
            } else if (code == KeyCode.T) {
                timeAllowed += 10;
            }
        }
    }

    /**
     * checks to see if ball collided with brick, wall, or paddle and acts accordingly
     * @param myBouncer
     * @param myPaddle
     * @param dx
     * @param dy
     * @param myScene
     * @return new dx and dy directions
     */
    public int[] collide(Ball myBouncer, Paddle myPaddle, int dx, int dy, Level myScene) {
        // check for brick collision
        for (Brick brick: bricks) {
            boolean collide = false;
            // hits left or right side of brick
            if ((myBouncer.getX()+myBouncer.getWidth() >= brick.getX() && myBouncer.getX()+myBouncer.getWidth() <= brick.getMidX() || myBouncer.getX() <= brick.getX()+brick.getWidth() && myBouncer.getX() >= brick.getMidX())
                    && myBouncer.getY()+myBouncer.getHeight() >= brick.getY() && myBouncer.getY() <= brick.getY()+brick.getHeight()) {
                dx *= -1;
                collide = true;
            }
            // hits top or bottom of brick
            else if ((myBouncer.getY()+myBouncer.getHeight() >= brick.getY() && myBouncer.getY()+myBouncer.getHeight() <= brick.getMidY() || myBouncer.getY() <= brick.getY()+brick.getHeight() && myBouncer.getY() >= brick.getMidY())
                    && myBouncer.getMidX() > brick.getX() && myBouncer.getMidX() < brick.getX()+brick.getWidth()) {
                dy *= -1;
                collide = true;
            }
            if (collide) {
                brick.setBrickLives(brick.getBrickLives()-1);
                // broke entire brick
                if (brick.getBrickLives() <= 0) {
                    if (brick.getBrickType() == 1) {
                        myScore += 50;
                    } else if (brick.getBrickType() == 2) {
                        myScore += 100;
                    } else if (brick.getBrickType() == 3) {
                        myScore += 150;
                    }
                    bricks = myScene.deleteBrick(bricks, brick);
                    // places power up for planets
                    if (brick.getBrickType() == 2) {
                        myScene.addPowerUp(brick.getPowerUp(), brick.getMidX(), brick.getMidY());
                        powerUps.add(brick.getPowerUp());
                    }
                    break;
                }
            }
        }
        // check if player completed level after breaking brick
        finishLevel();

        // check for wall collision
        if (myBouncer.getX() >= myScene.getWidth()-myBouncer.getWidth() || myBouncer.getX() <= 0) {
            dx *= -1;
        } else if (myBouncer.getY() >= myScene.getHeight()-myBouncer.getHeight() || myBouncer.getY() <= 0) {
            dy *= -1;
        }
        // check for paddle collision
        if (myBouncer.getY()+myBouncer.getHeight() >= myPaddle.getY() && myBouncer.getY()+myBouncer.getHeight() <= myPaddle.getY()+myPaddle.getHeight()) {
            // hits middle of paddle top
            if (myBouncer.getMidX() >= myPaddle.getX()+(myPaddle.getWidth()/4) && myBouncer.getMidX() <= myPaddle.getX()+(myPaddle.getWidth()*0.75)) {
                dy *= -1;
            }
            // hits left or right quarter of paddle top
            else if (myBouncer.getMidX() >= myPaddle.getX() && myBouncer.getMidX() < myPaddle.getX()+(myPaddle.getWidth()/4) ||
                    myBouncer.getMidX() > myPaddle.getX()+(myPaddle.getWidth()*0.75) && myBouncer.getX() <= myPaddle.getX()+myPaddle.getWidth()) {
                dx *= -1;
                dy *= -1;
            }
        }
        return new int[] {dx, dy};
    }

    /**
     * makes power ups and shooting stars fall down vertically from their height
     * @param elapsedTime
     */
    public void fallDown(double elapsedTime) {
        if (powerUps.size() > 0) {
            for (PowerUp powerUp: powerUps) {
                powerUp.setY(powerUp.getY() + POWERUP_SPEED * elapsedTime);
                if (paddleCollectPowerUp(powerUp)) {
                    break;
                }
            }
        }
        // every 10 seconds drop a falling star on Level 3
        if (currScene == 3 && (myTime+myTimer.getElapsedTime())%11 == 10) {
            betweenSecondsCounter++;
            if (betweenSecondsCounter == 60) {
                ShootingStar newStar = new ShootingStar();
                shootingStars.add(newStar);
                ShootingStar.shootStar(newStar);
                betweenSecondsCounter = 0;
            }
        }
        if (shootingStars.size() > 0) {
            for (ShootingStar shootingStar: shootingStars) {
                shootingStar.setY(shootingStar.getY() + SHOOTINGSTAR_SPEED * elapsedTime);
                if (paddleCollectShootingStar(shootingStar)) {
                    break;
                }
            }
        }
    }

    /**
     * checks to see whether or not the paddle "collected" shooting star
     * @param shootingStar
     * @return true -- paddle collected, false -- paddle didn't collect
     */
    private boolean paddleCollectShootingStar(ShootingStar shootingStar) {
        if (shootingStar.getY()+shootingStar.getHeight() < myPaddle.getY()) {
            return false;
        } else {
            if (shootingStar.getImage().getBoundsInParent().intersects(myPaddle.getImage().getBoundsInParent())) {
                myLives -= 1;
                shootingStars = myScene.deleteShootingStar(shootingStars, shootingStar);
            }
            // failed to collect -- fall all the way down
            else if (shootingStar.getY()+shootingStar.getHeight() >= myScene.getHeight()) {
                shootingStars = myScene.deleteShootingStar(shootingStars, shootingStar);
            }
            return true;
        }
    }

    /**
     * checks to see whether or not the paddle "collected" powerUp
     * @param powerUp
     * @return true -- paddle collected, false -- paddle didn't collect
     */
    private boolean paddleCollectPowerUp(PowerUp powerUp) {
        if (powerUp.getY()+powerUp.getHeight() < myPaddle.getY()) {
            return false;
        } else {
            if (powerUp.getImage().getBoundsInParent().intersects(myPaddle.getImage().getBoundsInParent())) {
                int returnVal = powerUp.activatePowerUp(powerUp, myPaddle, myBouncer, myLives, timeAllowed, bouncerSpeed);
                if (powerUp.getPowerUpType() == 0) {
                    myLives = returnVal;
                } else if (powerUp.getPowerUpType() == 3) {
                    timeAllowed = returnVal;
                } else if (powerUp.getPowerUpType() == 4) {
                    bouncerSpeed = returnVal;
                }
                powerUps = myScene.deletePowerUp(powerUps, powerUp);
            }
            // failed to collect -- fall all the way down
            else if (powerUp.getY()+powerUp.getHeight() >= myScene.getHeight()) {
                powerUps = myScene.deletePowerUp(powerUps, powerUp);
            }
            return true;
        }
    }

    /**
     * update stats on screen every step
     */
    private void updateStats() {
        myScene.getScore().setText("Score: " + myScore);
        myScene.getLivesCount().setText("Lives: " + myLives);
        timeLeft = timeAllowed-(myTimer.getElapsedTime()+myTime);
        myScene.getTime().setText("Time: " + Timer.getMinutes(timeLeft) + " min " + Timer.getSeconds(timeLeft) + " sec");
    }

    /**
     * checks to see if player fell (lose a life/stop animation) or if game over
     */
    public void fell() {
        if (myBouncer.getY() >= myScene.getHeight()-myBouncer.getHeight()) {
            myLives -= 1;
            updateStats();
            myTimer.stopTimer();
            animation.stop();
            playing = false;
            myTime += myTimer.getElapsedTime();
            if (myLives > 0) {
                myPaddle.resetPaddlePosition(SCENE_WIDTH, SCENE_HEIGHT);
                myBouncer.resetBallPosition(myPaddle.getMidX()-(myBouncer.getWidth()/2), myPaddle.getY()-myBouncer.getHeight());
            } else {
                gameOver();
            }
        }
    }

    /**
     * switches to gameOver screen and game ends
     */
    public void gameOver() {
        animation.stop();
        dx = 0;
        dy = 0;
        myFail = new Fail(SCENE_WIDTH, SCENE_HEIGHT, BACKGROUND, myScore, myTime+myTimer.getElapsedTime(), timeLeft <= 0);
        myStage.setScene(myFail.getScene());
    }

    /**
     * switches to new level if finished
     * if on last level, shows congratulatory end game screen and game ends
     */
    public void finishLevel() {
        if (bricks.size() == 0) {
            animation.stop();
            myTimer.stopTimer();
            if (currScene == 3) {
                myEnd = new Endgame(SCENE_WIDTH, SCENE_HEIGHT, BACKGROUND, myScore+1000, myTime);
                myStage.setScene(myEnd.getScene());
            } else if (currScene == 1 || currScene == 2) {
                setupGame();
            }
        }
    }

    /**
     * warps paddle from left to right and right to left sides of the screen if applicable
     * @param myPaddle
     * @param level
     */
    public void warp(Paddle myPaddle, int level) {
        if (level == 2 || level == 3) {
            if (myPaddle.getX() >= 330) {
                myPaddle.setX(20);
            } else if (myPaddle.getX() <= 15) {
                myPaddle.setX(325);
            }
        }
    }

    /**
     * starts the program
     * @param args
     */
    public static void main (String[] args) {
        launch(args);
    }
}

