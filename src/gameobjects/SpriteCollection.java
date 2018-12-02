package gameobjects;

import interfaces.Sprite;
import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * This class controls all of the sprites and can add, draw and time passed on a sprite.
 */
public class SpriteCollection {
    private List<Sprite> listOfSprite;

    /**
     * Constructor.
     */
    public SpriteCollection() {
        this.listOfSprite = new ArrayList<Sprite>();
    }

    /**
     * Adding a sprite to the list.
     *
     * @param s - List of sprites.
     */
    public void addSprite(Sprite s) {
        if (s != null) {
            this.listOfSprite.add(s);
        }
    }

    /**
     * @param s a given Collidable to remove.
     */
    public void removeSprite(Sprite s) {
        this.listOfSprite.remove(s);
    }

    /**
     * Call timePassed() on all sprites.
     *
     * @param dt - definition of time
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < listOfSprite.size(); i++) {
            listOfSprite.get(i).timePassed(dt);
        }
    }

    /**
     * Call drawOn(d) on all sprites.
     *
     * @param d - DrawSurface.
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < listOfSprite.size(); i++) {
            listOfSprite.get(i).drawOn(d);
        }
    }

}
