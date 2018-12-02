package creationfromfile;


import gameobjects.Block;
import interfaces.BlockCreator;

/**
 * Creating the width block decorated.
 */
public class WidthBlockCreator extends AbstractBlockCreator {
    private Integer width;

    /**
     * @param decorated decorated block given.
     * @param widthStr  the width given.
     */
    public WidthBlockCreator(BlockCreator decorated, String widthStr) {
        super(decorated);
        this.width = Integer.parseInt(widthStr);
    }

    /**
     * @param x x pos.
     * @param y y pos.
     * @return new Width-block decorated.
     */
    @Override
    public Block create(int x, int y) {
        Block b = super.create(x, y);
        b.setWidth(this.width);
        return b;
    }
}
