game
====

This project implements the game of Breakout.

Name: Samantha Whitt

### Timeline

Start Date: August 31

Finish Date: Sept 8

Hours Spent: 40-42 hours

### Resources Used
CODE: StackOverflow for AnimationTimer, StackPane, and other small JavaFX questions like setting new scene to same stage;
OracleDocs for KeyFrame and Timeline; lab_bounce for base code from Professor Duvall 
IMAGES: Pinterest for pixelated stopwatch; nicepng.com for pixelated planets

### Running the Program

Main class: app.java

Data files needed: all files located in doc and resources folder

Key/Mouse inputs: "SPACE" changes screen and starts game; "LEFT" and "RIGHT" control the paddle's motion

Cheat keys: "L" gives another life; "S" clears all of the stars; "Z" clears all of the bricks, thus finishing the game;
"T" gives 10 more seconds allowed

Known Bugs: collide() isn't precise -- sometimes when it hits a "brick" it thinks it hits it twice due to the bounds >= 
and <= some width, which to the viewer causes the brick to break on what seems like bounce 1. I had to use this method 
instead of intersect because intersect was less precise with round-shaped ImageViews. Warping also doesn't let you go 
to the edge of the screen. Also occasionally, the falling star will pause with a power up but eventually continue 
falling. Sometimes on the the third level if the player pressed the spacebar beforehand, the ball appears to move side 
to side on the paddle. If the player moves the paddle left or right, the game will resume correctly. 

Extra credit: Notion of time and the falling stars. Players only have 3 minutes and 08 seconds to complete all 3 levels 
or the "sun" burns up. To do this, I used an AnimationTimer that would start and stop whenever the player stopped/
started playing and had a myTime variable to keep track of previous times with elapsed time. The falling stars offset 
all of the beneficial power ups because they were large in size, but if a player collected one, it would result in losing 
a life. Stars fall at a random spot about every 10 seconds, which also used the timer.

### Notes
The middle half of the paddle bounces 90 degrees, but the left and right quarters bounce the ball in the direction it 
came from. The blue "L" power up gives the player an extra life. The red "S" power up enlarges the ball. The stopwatch 
power up gives 10 more seconds for the game to be completed. The green "B" power up slows down the speed of the ball.
The wormhole/warping ability only shows up in the second and third levels, and the falling/shooting stars only shows up
in the third level. There are different fail screens should the player fail due to time or lives, and there is a success
screen at the end. If the player completes the game before time is up, the player is rewards 1000 points. Breaking a
star is 50 points, a planet is 100 points, and an asteroid is 150 points.

### Impressions
I found that writing the code from almost scratch to be rewarding but extremely challenging in never coding with JavaFX
beforehand. Although it was disappointing that the collisions are still a tad glitch-y, I learned so much about cleaning 
code for readability and scalability, public versus private functions, and passing by value rather than reference. I 
deviated from my initial plan slightly by doing different cheat codes and not having negative power ups because of the 
timer that offsets it. Initially due to easily accessible variables and components, I thought that the hierarchy of 
classes and methods made sense. However looking at my code now, I see that there can be improvements done to better 
organize the main app class, which would also improve clean code writing. This game took me a lot of time, but I'm 
happy to physically see my hard work from the past week.
