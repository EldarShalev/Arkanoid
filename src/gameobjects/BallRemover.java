package gameobjects;


import gamecontrol.GameLevel;
import interfaces.HitListener;

/**
 * a BallRemover is in charge of removing balls from the game, as well as keeping count of the number
 * of balls that remain.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Constructor.
     *
     * @param game       a game to init.
     * @param removeBall removed ball.
     */
    public BallRemover(GameLevel game, Counter removeBall) {
        this.game = game;
        this.remainingBalls = removeBall;
    }

    /**
     * balls that hit the death-region should be removed from the game.
     *
     * @param beingHit a the death region that was hit.
     * @param hitter   the ball the hit the death-region.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getRectangle().isPointInsideTheRectangle(hitter.getCenter())) {
            remainingBalls.decrease(1);
            hitter.removeHitListener(this);
            hitter.removeFromGame(game);
        }
    }

    /**
     * @return the remaining balls.
     */
    public Counter getRemainingBalls() {
        return remainingBalls;
    }

    /**
     * @param ballsRemain a given counter to set for remaining blocks.
     */
    public void setRemainingBalls(int ballsRemain) {
        this.remainingBalls.increase(ballsRemain);
    }

    /**
     * @return the game.
     */
    public GameLevel getGame() {
        return game;
    }
}