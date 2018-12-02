package gameobjects;

import gamecontrol.GameLevel;
import geometry.Line;
import geometry.Point;
import interfaces.Sprite;
import biuoop.DrawSurface;

import java.util.List;

import java.awt.Color;

/**
 * This background is for level 1.
 */
public class Background1 implements Sprite {

    // Members
    private List<Block> listOfBlocks;
    private Color color;

    /**
     * Constructor for background 1.
     *
     * @param color  a given color to the background.
     * @param blocks the list of blocks in the level.
     */
    public Background1(Color color, List<Block> blocks) {
        this.color = color;
        this.listOfBlocks = blocks;
    }

    /**
     * Draw the sprite to the screen.
     *
     * @param d a given surface.
     */
    public void drawOn(DrawSurface d) {

        d.setColor(color);
        d.fillRectangle(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        geometry.Point middleWidthTop = this.listOfBlocks.get(0).getRectangle().getTop().middle();
        geometry.Point middleHeightLeft = this.listOfBlocks.get(0).getRectangle().getLeft().middle();
        geometry.Point middleWidthBot = this.listOfBlocks.get(0).getRectangle().getBot().middle();
        geometry.Point middleHeightRight = this.listOfBlocks.get(0).getRectangle().getRight().middle();
        // Getting the middle of the rectangle
        Point middleBlock = (new Line(middleWidthBot, middleWidthTop).intersectionWith(new Line(middleHeightLeft,
                middleHeightRight)));
        d.setColor(Color.blue);
        // Drawing the blue circles
        d.drawCircle((int) middleBlock.getX(), (int) middleBlock.getY(), 50);
        d.drawCircle((int) middleBlock.getX(), (int) middleBlock.getY(), 80);
        d.drawCircle((int) middleBlock.getX(), (int) middleBlock.getY(), 100);
        d.drawLine((int) middleBlock.getX() + 30, (int) middleBlock.getY(), (int) middleBlock.getX() + 120,
                (int) middleBlock.getY());
        d.drawLine((int) middleBlock.getX() - 30, (int) middleBlock.getY(), (int) middleBlock.getX() - 120,
                (int) middleBlock.getY());
        d.drawLine((int) middleBlock.getX(), (int) middleBlock.getY() + 30, (int) middleBlock.getX(),
                (int) middleBlock.getY() + 120);
        d.drawLine((int) middleBlock.getX(), (int) middleBlock.getY() - 30, (int) middleBlock.getX(),
                (int) middleBlock.getY() - 120);


    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt - definition of time
     */
    public void timePassed(double dt) {
        // Currently- nothing
    }

    /**
     * add the object to game.
     *
     * @param g a gamecontrol.GameLevel.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
