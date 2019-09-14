package app;

import app.components.Brick;
import app.components.Wormhole;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Setup {
    private ArrayList<Brick> myBricks = new ArrayList<>();
    private ArrayList<Brick> myStars = new ArrayList<>();
    private ArrayList<Wormhole> myWormholes = new ArrayList<>();

    /**
     * reads text file and places bricks in their locations like a map on the scene
     * increases every 15 x-wise starting at 15 and every 31 y-wise starting at 50
     * adds bricks to arrayList of bricks and stars to arrayList of stars
     * @param level
     */
    public void setUpBricks(String level) {
        int row_count = 1;
        double x_dist = 15;
        double y_dist = 50;
        try (Scanner scanner = new Scanner(new File(level))) {
            while (scanner.hasNext()) {
                if (row_count % 10 == 0) {
                    x_dist = 15;
                    y_dist += 31;
                }
                int brickType = Integer.parseInt(scanner.next());
                if (brickType > 0) {
                    Brick currBrick = new Brick(brickType);
                    currBrick.setX(x_dist);
                    currBrick.setY(y_dist);
                    myBricks.add(currBrick);
                    if (brickType == 1) myStars.add(currBrick);
                }
                x_dist += 37;
                row_count ++;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * places wormholes on the bottom left and right corners of the screen
     * @param level
     */
    public void setUpWormholes(String level) {
        if (level.equals(app.LEVEL2) || level.equals(app.LEVEL3)) {
            Wormhole myWormholeL = new Wormhole("left");
            myWormholes.add(myWormholeL);
            Wormhole myWormholeR = new Wormhole("right");
            myWormholes.add(myWormholeR);
        }
    }

    /**
     * @return list of all of the bricks
     */
    public ArrayList<Brick> getBricks() {
        return myBricks;
    }

    /**
     * used for cheat code to delete all stars
     * @return list of all of the star bricks
     */
    public ArrayList<Brick> getStars() {
        return myStars;
    }

    /**
     * @return list of all of the star bricks
     */
    public ArrayList<Wormhole> getWormholes() {
        return myWormholes;
    }
}
