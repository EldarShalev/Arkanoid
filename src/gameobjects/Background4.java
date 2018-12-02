package gameobjects;

import gamecontrol.GameLevel;

import interfaces.Sprite;
import biuoop.DrawSurface;

import java.util.List;
import java.awt.Color;

/**
 * This class is for the background of level 4.
 */
public class Background4 implements Sprite {

    // Members
    private List<Block> listOfBlocks;
    private Color color;

    /**
     * @param color  a given color for the surface.
     * @param blocks list of blocks of level 4.
     */
    public Background4(Color color, List<Block> blocks) {
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
        d.setColor(color.brighter());
        d.fillRectangle(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);


        // Drawing the left rain
        int startX = 100;
        int startY = 350;
        int stopX = 160;
        d.setColor(Color.white);
        for (int i = startX; i < stopX; i += 5) {
            d.drawLine(i, startY, i - 15, 600);
        }
        // Drawing the right rain
        startX = 600;
        startY = 400;
        stopX = 660;
        d.setColor(Color.white);
        for (int i = startX; i < stopX; i += 5) {
            d.drawLine(i, startY, i - 15, 600);
        }
        // Drawing the clouds
        startX = 100;
        startY = 350;

        int radius = 30;
        d.setColor(Color.gray);
        d.fillCircle(startX, startY, radius);
        d.setColor(Color.gray.brighter());
        d.fillCircle(startX + 20, startY - 10, radius);
        d.setColor(Color.gray);
        d.fillCircle(startX + 60, startY + 10, radius + 10);
        d.setColor(Color.gray.brighter());
        d.fillCircle(startX + 10, startY + 30, radius);
        d.setColor(Color.gray);
        d.fillCircle(startX + 40, startY + 25, radius);

        startX = 600;
        startY = 400;
        d.setColor(Color.gray);
        d.fillCircle(startX, startY, radius);
        d.setColor(Color.gray.brighter());
        d.fillCircle(startX + 20, startY - 10, radius);
        d.setColor(Color.gray);
        d.fillCircle(startX + 60, startY + 10, radius + 10);
        d.setColor(Color.gray.brighter());
        d.fillCircle(startX + 10, startY + 30, radius);
        d.setColor(Color.gray);
        d.fillCircle(startX + 40, startY + 25, radius);


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
     * add the object to game.
     *
     * @param g a gamecontrol.GameLevel.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
