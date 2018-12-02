package gamecontrol;

import gameobjects.SpriteCollection;
import interfaces.Animation;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The CountdownAnimation will display the given gameScreen, for numOfSeconds seconds, and on top of them it will
 * show a countdown from countFrom back to 1, where each number will appear on the screen for (numOfSeconds /
 * countFrom seconds, before it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {

    // Members
    private double numOfSeconds;
    private int countFrom;
    private int countTo;
    private SpriteCollection gameScreen;
    private Boolean stop;
    private long startingTime;
    private long timeForEveryNumber;
    private long timeToWait;


    /**
     * Constructor.
     *
     * @param numOfSec   num of second to count down for.
     * @param countFrom  from which number to start counting.
     * @param gameScreen a given game screen to show all the sprites and then counting.
     */
    public CountdownAnimation(double numOfSec, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSec;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.countTo = 0;
        // The time we enter to the function
        startingTime = System.currentTimeMillis();
        // Time for every number to be shown
        this.timeForEveryNumber = (long) numOfSeconds * 1000 / (countFrom + countTo);
        // The limit for every number to be shown
        this.timeToWait = this.timeForEveryNumber;
    }

    /**
     * Printing on the count-down to start the game.
     *
     * @param d  a given surface.
     * @param dt - definition of time
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // The current time we are in the function (will be compared with the time we first called the function)
        long currentTime = System.currentTimeMillis();
        // If the count-from went to zero- stop the loop and change stop to "true"
        if (this.countFrom == 0) {
            this.stop = true;
        }
        // Drawing the sprites so we can see them in the gui but they will not move
        this.gameScreen.drawAllOn(d);
        // Printing the current countFrom
        d.setColor(Color.red);
        d.drawText(d.getWidth() / 2, d.getHeight() / 2, String.valueOf(countFrom), 32);

        // If the current time minus the starting time is bigger then time to wait - apply to next number
        if (currentTime - this.startingTime > this.timeToWait) {
            //Changing the count from and count to in order to print the correct count from and keeping the millisecond
            this.countFrom--;
            this.countTo++;
            // The time to wait will now be plus the time for every number
            this.timeToWait += this.timeForEveryNumber;
        }
    }


    /**
     * @return true if the count down should stop.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}