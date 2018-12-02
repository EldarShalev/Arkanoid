package gameobjects;

import gamecontrol.GameLevel;
import geometry.Line;
import geometry.Point;
import interfaces.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

import static gameobjects.Constants.INDICATORS_RECT;

/**
 * This class indicates how much score the player got.
 */
public class ScoreIndicator implements Sprite {

    // Members
    private Counter scoreCounter;

    /**
     * Constructor.
     *
     * @param score the score counter.
     */
    public ScoreIndicator(Counter score) {
        this.scoreCounter = score;
    }

    /**
     * @param g a gamecontrol.GameLevel. to add this sprite.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }


    /**
     * Drawing the rectangle with the current score.
     *
     * @param d a given surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.CYAN.darker());
        d.fillRectangle(((int) INDICATORS_RECT.getUpperLeft().getX()), (int) INDICATORS_RECT.getUpperLeft().getY(),
                (int) (INDICATORS_RECT.getRight().start().getX() - INDICATORS_RECT.getUpperLeft().getX()),
                (int) (INDICATORS_RECT.getRight().end().getY() - INDICATORS_RECT.getUpperLeft().getY()));
        d.setColor(Color.black);
        d.drawRectangle(((int) INDICATORS_RECT.getUpperLeft().getX()), (int) INDICATORS_RECT.getUpperLeft().getY(),
                (int) (INDICATORS_RECT.getRight().start().getX() - INDICATORS_RECT.getUpperLeft().getX()),
                (int) (INDICATORS_RECT.getRight().end().getY() - INDICATORS_RECT.getUpperLeft().getY()));

        // Getting the middle of each line
        Point middleWidthTop = INDICATORS_RECT.getTop().middle();
        Point middleHeightLeft = INDICATORS_RECT.getLeft().middle();
        Point middleWidthBot = INDICATORS_RECT.getBot().middle();
        Point middleHeightRight = INDICATORS_RECT.getRight().middle();
        // Getting the middle of the rectangle
        Point middleBlock = (new Line(middleWidthBot, middleWidthTop).intersectionWith(new Line(middleHeightLeft,
                middleHeightRight)));
        // Setting the string to draw to be value of number of hits
        String stringToDraw = String.valueOf(this.scoreCounter.getValue());
        // Draw on the middle of the rectangle the number score
        d.drawText((int) middleBlock.getX(), (int) middleBlock.getY(), "Score: " + stringToDraw, 12);

    }


    /**
     * notify the sprite that time has passed.
     *
     * @param dt - definition of time.
     */
    public void timePassed(double dt) {
        // Currently- nothing
    }

}
