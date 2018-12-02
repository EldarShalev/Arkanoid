package gamecontrol;

import gameobjects.Counter;
import interfaces.Animation;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * This class is for the final screen.
 */
public class EndScreen implements Animation {
    // Members
    private Counter numOfLives;
    private Counter score;
    private boolean stop;

    /**
     * @param lives number of lives left.
     * @param score score gain so far.
     */
    public EndScreen(Counter lives, Counter score) {
        this.numOfLives = lives;
        this.score = score;
        this.stop = false;
    }

    /**
     * @param d  a given surface.
     * @param dt - definition of time
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // If the player lose
        if (this.numOfLives.getValue() == 0) {
            d.setColor(Color.red);
            d.drawText(0, d.getHeight() / 2, "Game Over. Your score is "
                    + this.score.getValue(), 32);
        } else {
            // If the player win
            d.setColor(Color.green);
            d.drawText(0, d.getHeight() / 2, "You Win! Your score is "
                    + this.score.getValue(), 32);
        }


    }

    /**
     * @return true if the animation should stop. fasle otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
