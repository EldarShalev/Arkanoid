package gameobjects;


import interfaces.HitListener;

/**
 * This class tracks the Score.
 */
public class ScoreTrackingListener implements HitListener {
    // Members
    private Counter currentScore;

    /**
     * Constructor.
     *
     * @param scoreCounter a score counter.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * @param beingHit the block that was hit.
     * @param hitter   the ball that hit.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // For destroying a block
        if (beingHit.getHitPoints() == 1) {
            currentScore.increase(10);
        }
        // For hitting a block
        currentScore.increase(5);

    }
}