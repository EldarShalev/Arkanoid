package gamecontrol;

import java.io.Serializable;

/**
 * This class is for the score information.
 */
public class ScoreInfo implements Serializable {
    // Members
    private String name;
    private int gameScore;

    /**
     * Constructor.
     *
     * @param name  - name of player.
     * @param score - num of point.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.gameScore = score;
    }

    /**
     * @return the name of player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the game score.
     */
    public int getScore() {
        return this.gameScore;
    }
}