package interfaces;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The interface Drawable.
 */
public interface Drawable {
    /**
     * Draw the shape with fill.
     *
     * @param drawSurface the draw surface to draw on
     * @param color       the drawing color
     */
    void drawFill(DrawSurface drawSurface, Color color);

    /**
     * Draw the shape with edge only.
     *
     * @param drawSurface the draw surface to draw on
     * @param color       the drawing color
     */
    void drawEdge(DrawSurface drawSurface, Color color);
}