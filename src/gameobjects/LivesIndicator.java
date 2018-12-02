package gameobjects;

import gamecontrol.GameLevel;
import geometry.Line;
import geometry.Point;
import interfaces.Sprite;
import biuoop.DrawSurface;

import static gameobjects.Constants.INDICATORS_RECT;

/**
 * This class for indicator the lives.
 */
public class LivesIndicator implements Sprite {

    // Members
    private Counter scoreCounter;

    /**
     * Constructor.
     *
     * @param score the score counter.
     */
    public LivesIndicator(Counter score) {
        this.scoreCounter = score;
    }

    /**
     * @param g a gamecontrol.GameLevel. to add this sprite.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt - definition of time.
     */
    public void timePassed(double dt) {
        // Currently- nothing
    }

    /**
     * @param d a given surface.
     */
    public void drawOn(DrawSurface d) {

        // Getting the middle of each line
        geometry.Point middleWidthTop = INDICATORS_RECT.getTop().middle();
        geometry.Point middleHeightLeft = INDICATORS_RECT.getLeft().middle();
        geometry.Point middleWidthBot = INDICATORS_RECT.getBot().middle();
        geometry.Point middleHeightRight = INDICATORS_RECT.getRight().middle();
        // Getting the middle of the rectangle
        Point middleBlock = (new Line(middleWidthBot, middleWidthTop).intersectionWith(new Line(middleHeightLeft,
                middleHeightRight)));
        // Setting the lives to be on the left middle of the rectangle.
        Point middleLeft = new Point(middleBlock.getX() / 2, middleBlock.getY());
        // Setting the string to draw to be value of number of hits
        String stringToDraw = String.valueOf(this.scoreCounter.getValue());
        // Draw on the middle of the rectangle the number score
        d.drawText((int) middleLeft.getX(), (int) middleLeft.getY(), "Lives: " + stringToDraw, 12);

    }

}
