package interfaces;

import gamecontrol.GameLevel;
import biuoop.DrawSurface;

/**
 * Interface of interfaces.Sprite.
 */
public interface Sprite {
    /**
     * Draw the sprite to the screen.
     *
     * @param d a given surface.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    void timePassed(double dt);

    /**
     * add the object to game.
     *
     * @param g a gamecontrol.GameLevel.
     */
    void addToGame(GameLevel g);
}
