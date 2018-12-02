package geometry;
/**
 * this class for creating a Point with x any y.
 */
public class Point {

    // Members
    private double x;
    private double y;


    /**
     * New Point Constructor.
     *
     * @param x for x
     * @param y for y
     */
    // constructor
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param other other point given.
     * @return the distance between 2 points.
     */
    // distance -- return the distance of this point to the other point
    public double distance(Point other) {
        return (Math.sqrt((other.getX() - this.x) * (other.getX() - this.x)
                + (other.getY() - this.y) * (other.getY() - this.y)));
    }

    /**
     * @param other other point given.
     * @return true if the points are equals, otherwise false.
     */
    // equals -- return true is the points are equal, false otherwise
    public boolean equals(Point other) {
        if (other != null) {
            return (this.x == other.getX() && this.y == other.getY());
        }
        return false;
    }

    /**
     * @return the x of a point
     */
    // Return the x and y values of this point
    public double getX() {
        return this.x;
    }

    /**
     * @return the y of the point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * @param x1 set x for a point.
     */
    public void setX(double x1) {
        this.x = x1;
    }

    /**
     * @param y1 set a y for a point.
     */
    public void setY(double y1) {
        this.y = y1;
    }

}

