package interfaces;

import gameobjects.Ball;
import gameobjects.Block;

/**
 * This is the interface of the hit listener.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block that was hit by the ball.
     * @param hitter   the ball that hit.
     */
    void hitEvent(Block beingHit, Ball hitter);
}