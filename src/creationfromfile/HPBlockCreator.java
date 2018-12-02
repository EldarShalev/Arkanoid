package creationfromfile;

import gameobjects.Block;
import interfaces.BlockCreator;

/**
 * Block for hit point.
 */
public class HPBlockCreator extends AbstractBlockCreator {
    private int hitPoints;

    /**
     * @param decorated a decorated block we got.
     * @param hp        the hit point we add to the point.
     */
    public HPBlockCreator(BlockCreator decorated, String hp) {
        super(decorated);
        this.hitPoints = Integer.parseInt(hp);
    }

    /**
     * @param x x pos.
     * @param y y pos.
     * @return new block according to the num of hits we added.
     */
    @Override
    public Block create(int x, int y) {
        Block b = super.create(x, y);
        b.setNumOfHits(this.hitPoints);
        return b;
    }
}