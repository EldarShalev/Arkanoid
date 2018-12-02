package gamecontrol;

import interfaces.Animation;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * This class is for pausing the game.
 */
public class PauseScreen implements Animation {
    // Members
    private boolean stop;

    /**
     * Constructor.
     */
    public PauseScreen() {
        this.stop = false;
    }

    /**
     * @param d  a given surface.
     * @param dt - definition of time
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.red);
        d.drawText(100, d.getHeight() / 2, "paused -- press space to continue", 32);

    }

    /**
     * @return true if the pausing should stop.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}