package creationfromfile;

import biuoop.DrawSurface;
import gamecontrol.GameLevel;
import gameobjects.Constants;
import geometry.Point;
import geometry.Rectangle;
import interfaces.Drawable;
import interfaces.Sprite;

import java.awt.Image;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Background.
 */
public class Background implements Sprite {
    //Members
    private Map<Drawable, Color> fillDrawables = new LinkedHashMap<>();
    private Map<Drawable, Color> edgeDrawables = new LinkedHashMap<>();
    private Image image = null;


    /**
     * Constructor by color.
     *
     * @param c the given color for the background.
     */
    public Background(Color c) {
        // Adding rectangle according to the color
        addFillDrawable(new Rectangle(new Point(0, 0), Constants.GAME_WIDTH, Constants.GAME_HEIGHT), c);
    }

    /**
     * Constructor by Image.
     *
     * @param im a given image to the background.
     */
    public Background(Image im) {
        this.image = im;
    }

    /**
     * Draw the sprite to the screen.
     *
     * @param d - the draw surface
     */
    public void drawOn(DrawSurface d) {
        // Draw the fill drawables
        for (Drawable drawable : fillDrawables.keySet()) {
            drawable.drawFill(d, fillDrawables.get(drawable));
        }

        // Draw the edge drawables
        for (Drawable drawable : edgeDrawables.keySet()) {
            drawable.drawEdge(d, edgeDrawables.get(drawable));
        }

        // Checking if image!=null before drawing it
        if (image != null) {
            d.drawImage(0, 0, image);
        }

    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        // Currently nothing
    }


    /**
     * Add the sprite to game.
     *
     * @param g - the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Add fill drawable.
     *
     * @param drawable the drawable
     * @param color    the color
     */
    public void addFillDrawable(Drawable drawable, Color color) {
        fillDrawables.put(drawable, color);
    }

    /**
     * Add edge drawable.
     *
     * @param drawable the drawable
     * @param color    the color
     */
    public void addEdgeDrawable(Drawable drawable, Color color) {
        edgeDrawables.put(drawable, color);
    }
}
