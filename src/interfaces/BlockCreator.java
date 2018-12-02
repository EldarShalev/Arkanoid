package interfaces;

import gameobjects.Block;

/**
 * Interface for block creator.
 */
public interface BlockCreator {
    // Create a block at the specified location.

    /**
     * @param xpos x pos.
     * @param ypos y pos.
     * @return new block created.
     */
    Block create(int xpos, int ypos);
}

