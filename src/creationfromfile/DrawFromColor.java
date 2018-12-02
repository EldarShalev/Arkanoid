package creationfromfile;

import biuoop.DrawSurface;
import geometry.Rectangle;
import interfaces.DrawingObjects;

import java.awt.Color;

/**
 * This class draw from given color.
 */
public class DrawFromColor implements DrawingObjects {
    // Members
    private Color color;

    /**
     * @param c a given color.
     */
    public DrawFromColor(Color c) {
        this.color = c;
    }

    /**
     * @param d    a given surface.
     * @param rect a given rectangle to draw the color.
     */
    public void drawOn(DrawSurface d, Rectangle rect) {
        d.setColor(this.color);
        d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(), (int) rect.getWidth(),
                (int) rect.getHeight());
    }
}
