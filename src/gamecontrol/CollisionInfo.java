package gamecontrol;

import geometry.Point;
import interfaces.Collidable;

/**
 * This class creates the information of the coliision point.
 */
public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collidable;

    /**
     * Constructor.
     *
     * @param p1  point of collision.
     * @param col the collidable that the ball hit.
     */
    public CollisionInfo(Point p1, Collidable col) {
        this.collisionPoint = p1;
        this.collidable = col;
    }

    /**
     * The point at which the collision occurs.
     *
     * @return the collision Point.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collidable;
    }
}