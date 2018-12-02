package creationfromfile;

import biuoop.DrawSurface;
import geometry.Rectangle;
import interfaces.DrawingObjects;

import java.awt.Color;

/**
 * This class for drawing the edge of the blocks line only (and not to fill it).
 */
public class EdgeBlockDrawer implements DrawingObjects {
    // Member
    private Color color;

    /**
     * @param c a given color.
     */
    public EdgeBlockDrawer(Color c) {
        this.color = c;
    }

    /**
     * @param d         the drawsurface.
     * @param rectangle the rectangle of the block we want to draw.
     */
    public void drawOn(DrawSurface d, Rectangle rectangle) {
        rectangle.drawEdge(d, color);
    }
}