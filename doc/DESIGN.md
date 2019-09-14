* Special Note: I accidentally wrote this before I refactored my code at the end, so this is relating to the current 
code.

The overall project's design was focused on splitting the code into components and from there, into more specific 
components. By splitting the code into sections based on components, it made it relatively easy to add whatever existing
components someone wanted to the game. For instance, adding another ball simply meant creating a new ball object and 
adding it to the level in the constructor, or adding another level required someone to add it to the if tree in 
app.java, and the game would automatically switch to another level once the level before it is finished. Thus there are
two packages splitting from app: components and scenes.

###

The high-level design acts like a class hierarchy going from app.java to scenes to all other component classes by 
way of composition. app.java is at the top, as it handles everything that allows the game to run including but not 
limited to: timeline, game loop, animation starter/stopper, components that move, switching scenes, and creating each 
level. Because Level.java is the class that sets up the scene with its contents, app.java is constantly in 
communication with Level.java, getting its contents like ball and paddle and seeing what to do with them every step. 
From there the other classes like Ball.java, Paddle.Java, Brick.java, PowerUp.java, etc. in the components package 
initiate their elements, but their main purpose is to stand as objects that are added to scenes/levels and are then 
called on by the main game class. Last is the scenes package, which hold all of the special setups of the scenes like 
the tutorial, end -- both in failure and success, and levels. Thus, the design centers on composition since at the 
lowest level, there are the component classes which make up scenes and levels where they are initiated and placed, then
there are the scene classes which make up app.java where app.java initializes them, switches between them, and takes 
their components to constantly update/run the game. 

###

To reduce the amount of complexity, I assumed that the ball collides then reflects against objects at a 90 degree angle 
regardless of where it hits the object, as shown whenever starting the level ,hitting bricks, walls, and the paddle in 
the middle half of it. If the ball hit the left or right quarters of the paddle, it would simply bounce back in the 
direction it came, which I also decided though not physically accurate. The star proved difficult because of its 
multiple edges as well as the circle for the exact opposite reason. Since I wanted to reduce redundant code in the 
collide method, I treated each brick the same in assuming that they were all like rectangular bricks where if the ball 
hit the left or right side it would bounce in the opposite x direction, and if it hit the top or bottom it would bounce
in the opposite y direction. With warping I found that there was going to be some space on the left and right sides of 
the scene where the player couldn't reach with the paddle, so I decided to leave it due to the fact that by having an x 
limit for where the paddle can go before warping to the other side was much simpler than breaking up the paddle 
according to how much the player moved off scene. 

###
The features I had planned to implement but didn't get to are both helpful and harmful power ups, a sun timeline bar 
that shows the player how much time they have left (in addition the clock), and a power up that adds an additional 
paddle that the player can control using different keys. Other changes in the plan resulted from better strategy 
regarding how the player interacts with the game. For instance, if the paddle collects a shooting star, the player 
loses a life rather than time since the time count is already short. However if I wanted to implement the other 
features I would do so as following:

1. Harmful Power Ups
    - Create two private instance variables instead of POWERUP_SPEED: GOOD_POWERUP_SPEED and BAD_POWERUP_SPEED 
      (GOOD_POWERUP_SPEED is faster/larger number than BAD_POWERUP_SPEED)
    - In PowerUp.java, add a private int instance variable called mySpeed
    - Have a random number generate 0 or 1 for each if/else if statement where 0 will be a "good" power up and mySpeed 
      is set to GOOD_POWERUP_SPEED, whereas 1 will be a "bad" power up and mySpeed is set to BAD_POWERUP_SPEED
    - Create a getPowerUpSpeed() method in PowerUp.java
    - In fallDown() of app.java, call getPowerUpSpeed() on the powerUp when iterating through the powerUps array list 
      to set the speed when it falls down (powerUp.setY(powerUp.getY() + powerUp.getPowerUpSpeed() * elapsedTime);)
    - In activatePowerUp() of PowerUp.java, set up a boolean that is true if the powerUp.getPowerUpSpeed() is good and 
      false if the powerUp.getPowerUpSpeed() is bad
    - Depending on that boolean value, for each else if statement, it will act accordingly. For example, if the 
      powerUpType is 0, if good, lives += 1, but if bad, lives -= 1. The bad power ups are the opposite of what's 
      currently there
2. Sun Timeline
    - In Sun.java, add a rectangle that has a fixed length for the sun to move "across"
    - In Level.java, add a new sun (Sun mySun = new Sun();) to setUpScene() and set its x and y placement in the top 
      corner to have enough space to also show the countdown
    - Figure out a mathematical way to proportion time with the length of the bar
    - In app.java, add a method called moveSun() that gets called every step() where its x position is set per the 
      proportion given the elapsedTime+myTime
3. Multiple Paddles
    - In PowerUp.java, add another if/else if statement with another powerUpType with a new image
    - In activatePowerUp() add an option depending on that powerUpType that calls a function in Level.java
    - In Level.java, create that method where it adds a new Paddle (Paddle secondPaddle = new Paddle();); to the root 
      and sets the x location 
    - In Level.java, create an arrayList of paddles that is also accessed in app.java and add preexisting paddle 
      (always) as well as this new paddle
    - In app.java in handleKeyInput() create another if/else where pressing "A" moves the second paddle left and "D" 
      moves the second paddle right

### REFACTOR CODE
use polymorphism to split powerups 
