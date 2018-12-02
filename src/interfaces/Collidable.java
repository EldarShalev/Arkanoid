package interfaces;

import gameobjects.Ball;
import geometry.Point;
import geometry.Rectangle;

import gameobjects.Velocity;

/**
 * Creates the interfaces.Collidable.
 */
public interface Collidable {
    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getRectangle();


    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param hitter          the ball that hit.
     * @param collisionPoint  the point of the collision.
     * @param currentVelocity - the current velocity given.
     * @return the new velocity.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}