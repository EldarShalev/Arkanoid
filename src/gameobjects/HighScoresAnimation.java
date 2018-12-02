package gameobjects;

import biuoop.DrawSurface;
import gamecontrol.ScoreInfo;
import interfaces.Animation;
import gamecontrol.HighScoresTable;

import java.awt.Color;

/**
 * This is animation for showing the high score table.
 */
public class HighScoresAnimation implements Animation {

    // Members
    private HighScoresTable myScores;

    /**
     * Constructor.
     *
     * @param scores a given score.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.myScores = scores;
    }

    /**
     * @param d  a given surface.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLUE);
        int spaces = 1;
        d.drawText(d.getWidth() / 2 - 101, 71, "High Scores:", 33);
        d.drawText(d.getWidth() / 2 - 101, 121, "Name", 23);
        d.drawText(d.getWidth() / 2, 121, "Score", 23);


        for (ScoreInfo score : this.myScores.getHighScores()) {
            d.drawText(d.getWidth() / 2 - 101, 121 + spaces * 20, String.valueOf(score.getScore()), 20);
            d.drawText(d.getWidth() / 2, 121 + spaces * 20, score.getName(), 20);
            spaces++;
        }
    }


    /**
     * @return true if the animation should stop. fasle otherwise.
     */
    public boolean shouldStop() {
        return false;
    }
}