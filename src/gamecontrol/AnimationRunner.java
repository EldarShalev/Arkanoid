package gamecontrol;

import gameobjects.Constants;
import interfaces.Animation;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;


/**
 * This class creates the Animation runner.
 */
public class AnimationRunner {
    // Members
    private Sleeper sleeper;
    private GUI gui;
    private double framesPerSecond;

    /**
     * Constructor.
     *
     * @param gui a given gui.
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.framesPerSecond = Constants.FPS;
        this.sleeper = new Sleeper();
    }

    /**
     * @return the gui of the game.
     */
    public GUI getGui() {
        return gui;
    }

    /**
     * @param animation a given Animation to run.
     */
    public void run(Animation animation) {
        double millisecondsPerFrame = 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            // Doing one frame
            animation.doOneFrame(d, 1 / this.framesPerSecond);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = ((long) millisecondsPerFrame) - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}