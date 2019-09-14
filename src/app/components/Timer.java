package app.components;

import app.app;
import javafx.animation.AnimationTimer;

/**
 * @author Samantha Whitt
 * used for creating an animation timer
 * example: stopwatch, tracks how long the animation has played
 */
public class Timer {
    private AnimationTimer timer;
    private int seconds;
    private int elapsedSeconds;

    /**
     * constructor for timer that creates a new timer/stopwatch
     * using past and current time in seconds
     * example: Timer newTimer = new Timer();
     * sources: StackOverflow
     */
    public Timer() {
        timer = new AnimationTimer() {
            private long lastTime = 0;

            /**
             * keeps track of seconds and updates time before resetting
             * @param now
             */
            @Override
            public void handle(long now) {
                if (lastTime != 0) {
                    if (now > lastTime + 1_000_000_000) {
                        seconds++;
                        elapsedSeconds = seconds;
                        lastTime = now;
                    }
                } else {
                    lastTime = now;
                }
            }

            /**
             * stops the timer and resets
             */
            @Override
            public void stop() {
                super.stop();
                lastTime = 0;
                seconds = 0;
            }
        };
    }

    /**
     * @return current time or time before timer stopped
     */
    public int getElapsedTime() {
        return elapsedSeconds;
    }

    /**
     * starts timer
     */
    public void startTimer() {
        timer.start();
    }

    /**
     * stops timer and resets
     */
    public void stopTimer() {
        timer.stop();
    }

    /**
     * takes total seconds and converts to minutes and seconds
     * @param time
     * @return time in minutes and seconds
     */
    public static int[] timeInMin(int time) {
        int sec = time % 60;
        int min = (time - sec)/60;
        return new int[] {min, sec};
    }

    /**
     * @param time
     * @return minutes portion of time
     */
    public static int getSeconds(int time) {
        return timeInMin(time)[1];
    }

    /**
     * @param time
     * @return seconds portion of time
     */
    public static int getMinutes(int time) {
        return timeInMin(time)[0];
    }
}
