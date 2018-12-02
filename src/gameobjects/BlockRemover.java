package gameobjects;

import gamecontrol.GameLevel;
import interfaces.HitListener;

/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count of the number
 * of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Constructor.
     *
     * @param game          a game to init.
     * @param removedBlocks removed blocks.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed from the game.
     *
     * @param beingHit a block that was hit.
     * @param hitter   the ball the hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 1) {
            remainingBlocks.decrease(1);
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(game);
        }
    }

    /**
     * @return the remaining blocks.
     */
    public Counter getRemainingBlocks() {
        return remainingBlocks;
    }

    /**
     * @param blocksRemain a given counter to set for remaining blocks.
     */
    public void setRemainingBlocks(Counter blocksRemain) {
        this.remainingBlocks = blocksRemain;
    }

    /**
     * @return the game.
     */
    public GameLevel getGame() {
        return game;
    }
}