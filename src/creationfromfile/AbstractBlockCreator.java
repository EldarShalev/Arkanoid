package creationfromfile;

import gameobjects.Block;
import interfaces.BlockCreator;

/**
 * This class for creation of block.
 */
public abstract class AbstractBlockCreator implements BlockCreator {
    // Member
    private BlockCreator decorated;

    /**
     * @param decoratedBlock a given decorated block.
     */
    public AbstractBlockCreator(BlockCreator decoratedBlock) {
        this.decorated = decoratedBlock;
    }

    /**
     * @param xpos x of new block.
     * @param ypos y of new block.
     * @return the new block created with x and y.
     */
    @Override
    public Block create(int xpos, int ypos) {
        return this.decorated.create(xpos, ypos);
    }
}