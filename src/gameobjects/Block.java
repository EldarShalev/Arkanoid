package gameobjects;

import creationfromfile.DrawFromColor;
import gamecontrol.GameLevel;
import geometry.Rectangle;
import geometry.Point;


import biuoop.DrawSurface;
import interfaces.Collidable;
import interfaces.HitListener;
import interfaces.HitNotifier;
import interfaces.Sprite;
import interfaces.DrawingObjects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class creates new Blocks as a rectangles.
 */
public class Block implements Collidable, Sprite, HitNotifier, Cloneable {


    // Members
    private List<HitListener> hitListeners = new ArrayList<>();
    private int numOfHits;
    private Rectangle rect;
    private Color color;
    private DrawingObjects drawingObjects = null;
    private Map<Integer, List<DrawingObjects>> drawers = new HashMap<>();


    /**
     * Constructor One.
     *
     * @param rect a given rectangle.
     */
    public Block(Rectangle rect) {
        this.rect = new Rectangle(rect.getUpperLeft(), rect.getWidth(), rect.getHeight());
        List<DrawingObjects> newDrawersList = new ArrayList<>();
        this.drawingObjects = new DrawFromColor(Color.CYAN.darker().darker());
        newDrawersList.add(this.drawingObjects);
        drawers.put(-1, newDrawersList);
        this.hitListeners = new ArrayList<HitListener>();

    }

    /**
     * @return Drawing Objects.
     */
    public DrawingObjects getDrawingObjects() {
        return drawingObjects;
    }


    /**
     * @param hitter the ball that hit.
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }


    /**
     * @param hl adding hl to block.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * @param hl removing hl from block.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * @return the rectangle.
     */
    public Rectangle getRectangle() {
        return this.rect;
    }

    /**
     * @return the color of the block.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @return the current number of hits.
     */
    public int getHitPoints() {
        return this.numOfHits;
    }

    /**
     * @param hits number of hits to set for the block(after was hitted).
     */
    public void setNumOfHits(int hits) {
        this.numOfHits = hits;
    }


    /**
     * @param hitter          the ball the hit the current block.
     * @param collisionPoint  - The point that the ball is going to intersect with.
     * @param currentVelocity The velocity of the ball.
     * @return the new velocity according to the way the ball is going to intersect with (from top, bot,right, ot left).
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        boolean topCollision = false;
        boolean botCollision = false;
        boolean rightCollision = false;
        boolean leftCollision = false;
        int count = 0;
        // Checking if the the collision point is located on top, bot, right or left lines.
        if (this.rect.getTop().isLocatedOnOneSegment(collisionPoint)) {
            topCollision = true;
            count++;
        }
        if (this.rect.getBot().isLocatedOnOneSegment(collisionPoint)) {
            botCollision = true;
            count++;
        }
        if (this.rect.getRight().isLocatedOnOneSegment(collisionPoint)) {
            rightCollision = true;
            count++;
        }
        if (this.rect.getLeft().isLocatedOnOneSegment(collisionPoint)) {
            leftCollision = true;
            count++;
        }

        // Means that we have 2 intersection with 2 differnt lines (the corner of the block for example)
        if (count > 1) {
            // in this case, change both dx and dy directions
            this.notifyHit(hitter);
            if (getHitPoints() > 0) {
                setNumOfHits(getHitPoints() - 1);
            }
            return (new Velocity(currentVelocity.velocityGetX() * (-1), currentVelocity.velocityGetY() * (-1)));
        }
        // if top or bot lines are intersects, change the dy direction and update num of hits
        if (topCollision || botCollision) {
            this.notifyHit(hitter);
            if (getHitPoints() > 0) {
                setNumOfHits(getHitPoints() - 1);
            }
            return (new Velocity(currentVelocity.velocityGetX(), currentVelocity.velocityGetY() * (-1)));

            // if right or left lines are intersects, change the dx direction and update num of hits
        } else if (rightCollision || leftCollision) {
            this.notifyHit(hitter);
            if (getHitPoints() > 0) {
                setNumOfHits(getHitPoints() - 1);
            }
            return (new Velocity(currentVelocity.velocityGetX() * (-1), currentVelocity.velocityGetY()));

        } else {
            throw new RuntimeException("Invalid Collision point");
        }

    }


    /**
     * Draw the block on the given DrawSurface.
     *
     * @param surface a Surface given.
     */
    public void drawOn(DrawSurface surface) {

        // Draw fill rectangle.
        List<DrawingObjects> blockDrawers;
        if (drawers.containsKey(numOfHits) && numOfHits >= 0) {
            blockDrawers = drawers.get(numOfHits);
        } else {
            blockDrawers = drawers.get(-1);
        }
        if (blockDrawers != null) {
            for (DrawingObjects drawer : blockDrawers) {
                drawer.drawOn(surface, this.rect);
            }
        }

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
     * Adding the current block to the game.
     *
     * @param d a given gamecontrol.GameLevel.
     */
    public void addToGame(GameLevel d) {
        d.addSprite(this);
        d.addCollidable(this);
    }

    /**
     * Remove the current block from the game.
     *
     * @param game a given game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }


    /**
     * @return Clone of the block.
     */
    public Block clone() {
        try {
            return (Block) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    /**
     * @param hitpoints hit point of block.
     * @param drawer    a drawer the add.
     */
    public void addDrawer(Integer hitpoints, DrawingObjects drawer) {
        if (drawers.containsKey(hitpoints)) {
            drawers.get(hitpoints).add(drawer);
        } else {
            List<DrawingObjects> newDrawersList = new ArrayList<>();
            newDrawersList.add(drawer);
            drawers.put(hitpoints, newDrawersList);
        }
    }


    /**
     * @param width1 a given width.
     */
    public void setWidth(Integer width1) {
        this.rect.setWidth(width1);
    }

    /**
     * @param height1 a given height.
     */
    public void setHeight(Integer height1) {
        this.rect.setHeight(height1);
    }

    /**
     * @param xpos x pos.
     * @param ypos y pos.
     */
    public Block(int xpos, int ypos) {
        this.rect = new Rectangle(new Point(xpos, ypos), 0.0, 0.0);
        this.numOfHits = 1;
    }

    /**
     * @return the width of the block.
     */
    public double getWidth() {
        return rect.getWidth();
    }

}
