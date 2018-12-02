package creationfromfile;

import gameobjects.Block;
import interfaces.BlockCreator;
import interfaces.DrawingObjects;

import java.awt.Color;
import java.awt.Image;

/**
 * This class drawing blocks according to it's decorated properties.
 */
public class DrawingBlockCreator extends AbstractBlockCreator {
    // Members
    private DrawingObjects blockDrawer;
    private Integer hitpoints;

    /**
     * @param decorated a given decorated block.
     * @param hp        hit point which it has.
     * @param isFill    do we need to fill the block or only edge drawing.
     * @param color     a given color to draw.
     */
    public DrawingBlockCreator(BlockCreator decorated, Integer hp, Boolean isFill, Color color) {
        super(decorated);
        this.hitpoints = hp;
        if (isFill) {
            this.blockDrawer = new FillBlockDrawer(color);
        } else {
            this.blockDrawer = new EdgeBlockDrawer(color);
        }
    }

    /**
     * @param decorated a decorated block.
     * @param hp        the given hit point.
     * @param image     the image we want to draw on the block.
     */
    public DrawingBlockCreator(BlockCreator decorated, Integer hp, Image image) {
        super(decorated);
        this.hitpoints = hp;
        this.blockDrawer = new DrawFromImage(image);
    }

    /**
     * @param x a given x pos.
     * @param y a given y pos.
     * @return a new block with x and y and the drawing feature we want.
     */
    @Override
    public Block create(int x, int y) {
        Block b = super.create(x, y);
        b.addDrawer(hitpoints, blockDrawer);
        return b;
    }
}
