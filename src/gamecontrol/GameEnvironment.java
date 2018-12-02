package gamecontrol;

import gameobjects.Paddle;
import geometry.Line;
import geometry.Point;
import interfaces.Collidable;

import java.util.ArrayList;
import java.util.List;

/**
 * this Class creates a game environment using list of collidables.
 */
public class GameEnvironment {

    private List<Collidable> listOfCollidable;

    /**
     * Constructor.
     */
    public GameEnvironment() {
        this.listOfCollidable = new ArrayList<Collidable>();
    }

    /**
     * @return the list of collidables.
     */
    public List<Collidable> getListOfCollidable() {
        return listOfCollidable;
    }

    /**
     * @return the paddle.
     */
    public Paddle getPaddle() {
        if (this.getListOfCollidable().size() > 0) {
            // Checking for each collidable if it's instance of gameobjects.Paddle and return if found
            for (int i = 0; i < this.getListOfCollidable().size(); i++) {
                if (getListOfCollidable().get(i) instanceof Paddle) {
                    return (Paddle) getListOfCollidable().get(i);
                }
            }
        }
        return null;
    }


    /**
     * Add the given collidable to the environment.
     *
     * @param c - a given collidable.
     */
    public void addCollidable(Collidable c) {
        this.listOfCollidable.add(c);
    }

    /**
     * @param c a given Collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.listOfCollidable.remove(c);
    }


    /**
     * Assume an object moving from line.start() to line.end(). If this object will not collide with any of the
     * collidables in this collection, return null. Else, return the information about the closest collision that
     * is going to occur.
     *
     * @param trajectory - a line to check if there is an intersection.
     * @return the collision info for the closet collision point.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // To check the first intersection
        boolean firstIntersection = true;
        Double minDistance = null;
        Point comparePoint = null;
        Point clostPoint = null;
        Collidable collidable = null;
        // Run on all collidables
        for (int i = 0; i < listOfCollidable.size(); i++) {
            // Assigning the compare point to be the first intersection of the collidable
            comparePoint = trajectory.closestIntersectionToStartOfLine(listOfCollidable.get(i).getRectangle());
            // if the first collidable has an intersection with the trajectory, assign the point without checking min
            // to other points(because he is the first one)
            if (comparePoint != null && (firstIntersection)) {
                // Assigning the first point and the collidable to be the first collidable
                minDistance = trajectory.start().distance(comparePoint);
                collidable = listOfCollidable.get(i);
                clostPoint = new Point(comparePoint.getX(), comparePoint.getY());
                // if we already has a point we compare our new point with the last minimum point we found to check if
                // he is the minimum point
            } else if (comparePoint != null) {
                if (trajectory.start().distance(comparePoint) < minDistance) {
                    minDistance = trajectory.start().distance(comparePoint);
                    collidable = listOfCollidable.get(i);
                    clostPoint = new Point(comparePoint.getX(), comparePoint.getY());
                }
            }
        }
        // if no point was found
        if (clostPoint == null) {
            return null;
            // if we found a point-
            // return the collision info of the closet point and the collidable he is intersect with.

        } else {
            return new CollisionInfo(clostPoint, collidable);
        }
    }


    /**
     * @param point a given point.
     * @return true if the point is inside any collidable, false otherwise.
     */
    public boolean isPointContainedInCollidable(Point point) {
        // Run on all collidables
        for (Collidable collidable : listOfCollidable) {
            // Getting for each collidable the rectangle and check in every rectangle if the point is inside
            if (collidable.getRectangle().isPointInsideTheRectangle(point)) {
                return true;
            }
        }
        return false;

    }
}