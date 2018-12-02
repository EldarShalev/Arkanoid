package creationfromfile;

import biuoop.DrawSurface;
import geometry.Rectangle;
import interfaces.DrawingObjects;

import java.awt.Color;

/**
 * This class for drawing the fill of the blocks line only.
 */
public class FillBlockDrawer implements DrawingObjects {
    private Color color;

    /**
     * @param c a given color.
     */
    public FillBlockDrawer(Color c) {
        this.color = c;
    }

    /**
     * @param d         the drawsurface.
     * @param rectangle the rectangle of the block we want to draw.
     */
    public void drawOn(DrawSurface d, Rectangle rectangle) {
        rectangle.drawFill(d, color);
    }
}