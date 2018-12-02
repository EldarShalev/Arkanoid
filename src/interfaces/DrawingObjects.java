package interfaces;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * Drawing object interface.
 */
public interface DrawingObjects {

    /**
     * @param d    a given drawsurface.
     * @param rect a given rectangle to draw.
     */
    void drawOn(DrawSurface d, Rectangle rect);

}
