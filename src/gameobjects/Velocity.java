package gameobjects;

import geometry.Point;


/**
 * gameobjects.Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private Point p1;
    // constructors

    /**
     * Static constructor for taking a design of angle and speed, converting to change in position x and y.
     *
     * @param angle - from 0-360
     * @param speed - for the speed of the ball
     * @return the new velocity using the converted dx and dy
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // Calculating the dx and dy by converting to angles in radians and multiple the speed
        double dx = Math.round((Math.cos(Math.toRadians(angle) - Math.PI / 2) * speed)); // for change in dx
        double dy = Math.round((Math.sin(Math.toRadians(angle) - Math.PI / 2) * speed)); // for change in dy
        return new Velocity(dx, dy);
    }

    /**
     * Constructor for velocity, change in dx and dy.
     *
     * @param dx the new x.
     * @param dy the new y.
     */
    public Velocity(double dx, double dy) {
        this.p1 = new Point(dx, dy);
    }

    /**
     * @return the current speed of the velocity.
     */
    public double getSpeed() {
        return (Math.sqrt(velocityGetX() * velocityGetX() + velocityGetY() * velocityGetY()));
    }

    /**
     * // Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p  0 a point to get the x and y for the changing.
     * @param dt - the amount of dt to apply to the point.
     * @return new point after positioning the new x and y.
     */
    public Point applyToPoint(Point p, double dt) {
        return new Point(p.getX() + this.p1.getX() * dt, p.getY() + this.p1.getY() * dt);
    }

    /**
     * @return the gameobjects.Velocity of the x
     */
    public double velocityGetX() {
        return this.p1.getX();
    }

    /**
     * @return the gameobjects.Velocity of the y
     */
    public double velocityGetY() {
        return this.p1.getY();
    }


}