package gameobjects;

import gamecontrol.GameLevel;
import geometry.Line;
import geometry.Point;
import interfaces.Sprite;
import biuoop.DrawSurface;

import static gameobjects.Constants.INDICATORS_RECT;

/**
 * This class indicates how much score the player got.
 */
public class NameIndicator implements Sprite {

    // Members
    private String levelName;

    /**
     * Constructor.
     *
     * @param levelName the score counter.
     */
    public NameIndicator(String levelName) {
        this.levelName = levelName;
    }

    /**
     * @param g a gamecontrol.GameLevel. to add this sprite.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }


    /**
     * Drawing the rectangle with the current level Name.
     *
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
        // Setting the lives to be on the right middle of the rectangle.
        Point middleRight = new Point((middleBlock.getX() + INDICATORS_RECT.getRight().start().getX()) / 2
                , middleBlock.getY());

        // Draw on the middle of the rectangle the number score
        d.drawText((int) middleRight.getX(), (int) middleRight.getY(), "Level Name: " + levelName, 12);

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
