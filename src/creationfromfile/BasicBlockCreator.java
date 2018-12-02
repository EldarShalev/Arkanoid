package creationfromfile;

import gameobjects.Block;
import interfaces.BlockCreator;

/**
 * This class is for basic block.
 */
public class BasicBlockCreator implements BlockCreator {

    /**
     * @param xpos a given x.
     * @param ypos a given y.
     * @return new basic block according to x and y only.
     */
    @Override
    public Block create(int xpos, int ypos) {
        return new Block(xpos, ypos);
    }
}
