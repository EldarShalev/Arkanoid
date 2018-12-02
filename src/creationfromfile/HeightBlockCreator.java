package creationfromfile;

import gameobjects.Block;
import interfaces.BlockCreator;

/**
 * This class is for the height of the block we create.
 */
public class HeightBlockCreator extends AbstractBlockCreator {
    //Member
    private Integer height;

    /**
     * @param decorated a given block decorated.
     * @param heightStr the height of the ball.
     */
    public HeightBlockCreator(BlockCreator decorated, String heightStr) {
        super(decorated);
        this.height = Integer.parseInt(heightStr);
    }

    /**
     * @param x x pos.
     * @param y y pos/
     * @return new decorated ball according to the height we added to the ball.
     */
    @Override
    public Block create(int x, int y) {
        Block b = super.create(x, y);
        b.setHeight(this.height);
        return b;
    }
}