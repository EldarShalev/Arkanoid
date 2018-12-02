package gameobjects;

import gamecontrol.GameLevel;

import interfaces.Sprite;
import biuoop.DrawSurface;

import java.util.List;
import java.awt.Color;

/**
 * This class is for the background of level 3.
 */
public class Background3 implements Sprite {

    // Members
    private List<Block> listOfBlocks;
    private Color color;

    /**
     * @param color  a given color for the surface.
     * @param blocks list of blocks of level 3.
     */
    public Background3(Color color, List<Block> blocks) {
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
        // Getting the point of the top circle
        double startX = 100;
        double startY = 250;

        // Drawing the circle
        int radius = 10;
        d.setColor(Color.ORANGE.brighter());
        d.fillCircle((int) startX, (int) startY, radius);
        d.setColor(Color.red);
        d.fillCircle((int) startX, (int) startY, radius - 3);
        d.setColor(Color.white);
        d.fillCircle((int) startX, (int) startY, radius - 6);

        // Drawing the rectangle beneath the circle
        d.setColor(Color.DARK_GRAY);
        d.fillRectangle((int) startX - 5, (int) startY + 10, 10, 150);
        d.fillRectangle((int) startX - 20, (int) startY + 10 + 150, 40, 50);
        d.fillRectangle((int) startX - 60, (int) startY + 10 + 150, 120, 170);

        // Drawing the windows
        d.setColor(Color.white);
        int numOfWindows = 5;
        int windowStartingX = (int) startX - 50;
        int windowStartingY = (int) startY + 10 + 150 + 10;
        for (int i = 0; i < numOfWindows; i++) {
            for (int j = 0; j < numOfWindows; j++) {
                d.fillRectangle(windowStartingX, windowStartingY, 16, 30);
                windowStartingX += 21;
            }
            windowStartingY += 35;
            windowStartingX = (int) startX - 50;
        }


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
