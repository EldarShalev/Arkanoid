package creationfromfile;

import biuoop.DrawSurface;
import geometry.Rectangle;
import interfaces.DrawingObjects;

import java.awt.Image;

/**
 * This class to draw the image of given one.
 */
public class DrawFromImage implements DrawingObjects {
    // Members
    private Image image;

    /**
     * Constructor.
     *
     * @param img a given image to draw.
     */
    public DrawFromImage(Image img) {
        this.image = img;
    }

    /**
     * @param d    a given surface.
     * @param rect a given rectangle to draw the image.
     */
    public void drawOn(DrawSurface d, Rectangle rect) {
        d.drawImage((int) rect.getUpperLeft().getX(),
                (int) rect.getUpperLeft().getY(), this.image);
    }
}
