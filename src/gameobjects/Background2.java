package gameobjects;

import gamecontrol.GameLevel;

import interfaces.Sprite;
import biuoop.DrawSurface;

import java.util.List;
import java.awt.Color;

/**
 * This class is for the background of level 2.
 */
public class Background2 implements Sprite {

    // Members
    private List<Block> listOfBlocks;
    private Color color;

    /**
     * @param color  a given color for the surface.
     * @param blocks list of blocks of level 2.
     */
    public Background2(Color color, List<Block> blocks) {
        this.color = color;
        this.listOfBlocks = blocks;
    }

    /**
     * Draw the sprite to the screen.
     *
     * @param d a given surface.
     */
    public void drawOn(DrawSurface d) {

        // Draw surface
        d.setColor(color);
        d.fillRectangle(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        // Getting the middle point of the sun
        double startY = listOfBlocks.get(0).getRectangle().getUpperLeft().getY();
        double middleY = startY / 2;
        double middleX = 0;
        if (listOfBlocks.get(2) != null) {
            middleX = listOfBlocks.get(2).getRectangle().getTop().middle().getX();
        }

        d.setColor(Color.ORANGE.brighter());
        // Drawing the lines
        if (listOfBlocks.get(13) != null) {
            int startingAngle = 50;
            double blockStopX = listOfBlocks.get(13).getRectangle().getUpperLeft().getX();
            double blockStartX = listOfBlocks.get(0).getRectangle().getUpperLeft().getX();
            while (blockStartX < blockStopX) {
                double sunStartX = (Math.sin(startingAngle * Math.PI / 180)) + middleY;
                double sunStartY = (Math.cos(startingAngle * Math.PI / 180)) + middleY;
                d.drawLine((int) sunStartX, (int) sunStartY, (int) blockStartX, 250);
                blockStartX += 10;
                startingAngle += 10;
            }
        }

        // Drawing the sun
        int radius = 60;
        d.setColor(Color.ORANGE.brighter());
        d.fillCircle((int) middleX, (int) middleY, radius);
        d.setColor(Color.YELLOW.darker());
        d.fillCircle((int) middleX, (int) middleY, radius - 10);
        d.setColor(Color.orange);
        d.fillCircle((int) middleX, (int) middleY, radius - 20);

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
