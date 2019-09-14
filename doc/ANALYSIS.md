### Status
The code is readable in that function names are intuitive and all the classes are divided into their own 
components. Additionally, there are helpful comments for each method that outline the purpose and examples of how to 
use it. On the other hand though, it's not completely readable if a coder is trying to see how things relate to each 
other because there are many dependencies that aren't outlined and certain classes handle more functions than the rest. 
For example, addShootingStar() is in Level.java to add a shooting star to the screen, but some may think that the 
function should be in the ShootingStar.java class. 

Because of this design organization, there is a mix of clear dependencies and "back channels". All of the images are 
instance variables and components like myScene, myBouncer, myPaddle, etc. are passed as parameters to other methods 
with return values and are initialized in constructors (particularly in the Level.java class). However component 
attributes are called by getter methods, and sometimes if the private instance variable is already in the class, the 
methods treat them like global variables by simply calling it instead of getting it from a parameter, which is possible 
because the constructor initializes them. There are many getters for each component due to many things needing the 
imageView of the object, the type, etc. 

``` 
public int activatePowerUp(PowerUp powerUp, Paddle myPaddle, Ball myBouncer, int lives, int timeAllowed, int velocity) {
        int res = 0;
        int powerUpType = powerUp.getPowerUpType();
        if (powerUpType == 0) {
            lives += 1;
            res = lives;
        } else if (powerUpType == 1) {
            myPaddle.getImage().setFitWidth(myPaddle.getWidth() + 15);
        } else if (powerUpType == 2) {
            myBouncer.getImage().setFitWidth(myBouncer.getWidth() + 5);
            myBouncer.getImage().setFitHeight(myBouncer.getHeight() + 5);
        } else if (powerUpType == 3) {
            timeAllowed += 10;
            res = timeAllowed;
        } else if (powerUpType == 4) {
            velocity -= 25;
            res = velocity;
        }
        return res;
    }
```
The above code corresponds to activating power ups in the game if the paddle collects the falling power up after 
completely breaking a brick. Based on the powerUp that is associated with the brick, it changes different features of 
the game like the paddle length, ball speed, time allowed, lives count, and ball size. This code is very readable 
because it's mainly if trees that act depending on the powerUpType given the powerUp passed in. Power ups had many 
commits because there were various parts to making it happen, but activating power ups were included in the commits 
starting with "added power ups and deleted properly" to "finished power ups" since making them activate was the last 
step. I used SourceTree to help me with my commits and seeing what code was changed, which is why the commits weren't 
as descriptive as they should be, but the purpose of my commits is normally adding new code and if I refactored 
anything, the message would include "fix" or "refactor". Because I broke up integrating the power ups in steps with 
creating the class as the first, adding them to scene as second, making them fall down and get collected as the third, 
and finally making them change features upon being collected, I chose to package together this successful function, 
marking the end of implementing power ups.

```
    private void setUpBricks(String level) {
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

        for (Brick brick: myBricks) {
            myRoot.getChildren().add(brick.getImage());
        }
    }
```
The above code method sets up all the bricks on the scene given the text file that corresponds to what the brick layout 
should be. This makes the code relatively easy to read and understand because it's short and simply loops through the 
text file, sets the locations of the brick, and adds it to brick ArrayLists. Commit messages relating to this code are 
"reading from txt file and setting scene" to "refactoring into new classes". This code was initially in app.java, so 
the first commit relays what the new code is doing. Then when I realized what classes I further needed, I 
created more classes shown in "adding new classes", and moved successfully and cleaned up in "refactoring into new 
classes". I committed these bits of code in that order because that's how I thought about my code and didn't want to 
lose any changes. Thus I created new code, committed, and switched it into new classes after making sure it still 
worked, and committed. 

### Design

The high-level design acts like a class hierarchy by way of composition. Starting at the lowest level, there are the 
component classes which make up scenes and levels where they are initiated and placed, then
there are the scene classes which make up the main class where the main class initializes them, switches between them, 
and takes their components to constantly update/run the game. Thus, it's based on composition since the top main class 
runs the game and contains levels/scenes, and those levels/scenes create new components.

To add a new level, create a new txt file in the resources folder that has numbers from 0-3 in a grid that's 9 by 10 
numbers. In app.java class in setupGame(), add an else if statement following the same pattern as the rest.

Because I liked the idea of having classes for components so that I could easily create new ones, I had the overall 
design be composition based. Furthermore, having a main game class and putting a lot of the weight on that class as 
well as initializing many of the objects in the level class made it simpler for dependencies. If a method required 
something to move, it was in the main app.java class and if something was dealing with the UI, then it was in the 
Level.java class where all of the private instance variables were. However, this design lacks a lot of clear 
organization because in that case, many methods were pertaining to specific components were not in their classes but 
instead in app.java or Level.java. I couldn't figure out a smooth way to make methods that related to specific 
components since I didn't want a method that handled all of the shooting stars to be located in a class object that 
signifies only one. Therefore, I wrestled with creating a new class to take some of the methods from the main class so 
that the main class would only have to take care of player changes and overall game functions, and another game class 
would take care of moving objects. Or, in general I wrestled with trying to get everything to be updated properly 
without calling many getters/setters. Since the main app.java and Level.java classes handle so much of the game, they 
are very sensitive in terms of dependencies. Like mentioned earlier, instead of passing them in as parameters if the 
private instance variable was already at the top, the methods would simply call them. This made things much easier 
because many of the multiple components were put in ArrayLists that needed to be constantly updated.

The power up feature requires image resources as well as classes PowerUp.java, Brick.java, Level.java, and app.java. 
PowerUp.java contains everything needed to make the power up including the appropriate ImageView, Brick.java for 
associating a random power up to each brick, Level.java for initializing the power up on screen, and app.java for 
making the power ups fall, checking whether or not the paddle collected the power up, and calling activate from the 
PowerUp.java class if applicable. I wanted the power ups to be an attribute of the brick so that it was easier to place 
power ups if the bricks were broken so that they "dropped" them. Because the functionality was spread over many classes, 
it limits the flexibility of adding a new power up. For instance if adding a new power up, a coder would have to change 
the limit of random integer generator in the Brick.java class, add another else if condition to both the PowerUp.java 
class constructor and the activatePowerUp() method. The instance variables detailing its speed, which thus deals with 
its movement is in the app.java class, but the rest of the information like powerUpType is in PowerUp.java.

Another feature are the bricks which all use Brick.java, Level.java, and app.java. The images relating to each type of 
brick are found in the resources folder, and specific attributes to the brick are in the Brick.java class. Level.java 
initializes the bricks based on the current level and creates an ArrayList of all the bricks. app.java calls on those 
attributes to figure out if the ball collided with the list of bricks, so it follows a similar structure as the power 
ups in that anything relating to the position and initializing bricks is in Level.java, attributes are in Brick.java, 
and moving components are in app.java. Consequently, this means that like power ups, it has limitations when trying to 
follow how the bricks work or adding a new type of brick because its functionality is split into different classes. It 
also assumes that the text document relating to how the bricks should be set follows specific parameters (9 by 10 grid), 
which makes it even more inflexible when trying to add new brick layouts, but because the structure for how each of 
these components is consistent, it makes it easier for a coder to change the code upon realization of the pattern.

### Alternate Designs

As mentioned before, I was considering adding a Game.java class that took care of moving things from app.java. This was 
hard to implement because of the dependencies, and I felt as though it would essentially become a "helper" class rather 
than something separable. On the contrary, if added it would've had more established roles regarding what app.java and 
Game.java are supposed to do in retrospect to the entire simulation. It would also add a more organized class hierarchy 
that's still based on composition. Alternatively, I thought of making layout classes for each component so that I could 
differentiate methods that relate to multiple of the components with methods that refer to one specific object like 
shootStars() versus getImage(). This design solved the issue of readability previously talked about since if someone 
was trying to figure out how everything related or where the powerUps were actually added to the scene, there would be 
one class labeled appropriately that made it clear. The negative side of this design strategy was that it would only be 
possible through many parameters, getters/setters, etc., which leads to confusion with dependencies since they're 
getting passed around all over. Once this were coded though, it would make things easier to add because the methods 
would automatically take care of it. For this reason if I could redo the project, I would implement the second 
alternative design choice. I could use abstraction for the layout classes of each component where the more specific 
example would be under it like AllBricks.java and Brick.java. This way the code has a clear class hierarchy, is 
readable, facilitates an easy way to add new features, and eliminates redundancy. 

The three most obvious bugs result from assumptions I made including: collision accuracy, warping, and starting off 
each level. Collision accuracy deals with the fact that sometimes the ball thinks it hits the oddly-shaped brick 
multiple times though it only looks like one hit. Sometimes if the player goes to the right and warps to the left, 
going to the right again won't let it warp. On the last level, the ball is occasionally glitching/moving on the paddle 
already rather than waiting for the player to press the space-bar to release. To fix this, all the player has to do is 
move the paddle.