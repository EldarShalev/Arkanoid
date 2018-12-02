package geometry;

import biuoop.DrawSurface;
import interfaces.Drawable;

import java.awt.Color;
import java.util.List;

/**
 * This class is used to make a geometry.Line using 2 points - start point and end point.
 */
public class Line implements Drawable {

    private Point startPoint;
    private Point endPoint;
    // constructors

    /**
     * @param start start point.
     * @param end   end point.
     */
    public Line(final Point start, final Point end) {
        this.startPoint = start;
        this.endPoint = end;
    }

    /**
     * Creating a line using x and y (and not points).
     *
     * @param x1 start x.
     * @param y1 start y.
     * @param x2 end x.
     * @param y2 end y.
     */
    public Line(double x1, double y1, double x2, double y2) {
        startPoint = new Point(x1, y1);
        endPoint = new Point(x2, y2);
    }

    /**
     * @return the length of the line.
     */
    public double length() {
        return (startPoint.distance(endPoint));
    }

    /**
     * @return the middle point of the line.
     */
    public Point middle() {
        Point middle = new Point((endPoint.getX() + startPoint.getX()) / 2,
                (endPoint.getY() + startPoint.getY()) / 2);
        return middle;
    }

    /**
     * @return the start point of the line
     */
    public Point start() {
        return this.startPoint;
    }

    /**
     * @return the end point of the line
     */
    public Point end() {
        return this.endPoint;
    }

    /**
     * @param other - another line to compare with.
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return (intersectionWith(other) != null);
    }


    /**
     * @param other -another line to compare with.
     * @return the intersection point if the lines intersect, otherwise returns null.
     */
    public Point intersectionWith(Line other) {
        // geometry.Line AB (current line) represented as a1x + b1y = c1
        double a1 = this.endPoint.getY() - this.startPoint.getY();
        double b1 = this.startPoint.getX() - this.endPoint.getX();
        double c1 = a1 * (this.startPoint.getX()) + b1 * (this.startPoint.getY());
        // geometry.Line CD represented as a2x + b2y = c2
        double a2 = other.endPoint.getY() - other.startPoint.getY();
        double b2 = other.startPoint.getX() - other.endPoint.getX();
        double c2 = a2 * (other.startPoint.getX()) + b2 * (other.startPoint.getY());
        // checking the determination of the 2 lines
        double determinant = a1 * b2 - a2 * b1;
        // Edge cases if the lines have same y and 1 common point
        if (this.startPoint.getY() == other.startPoint.getY() && this.endPoint.getX() == other.startPoint.getX()) {
            return (other.startPoint);
        }
        if (this.startPoint.getY() == other.startPoint.getY() && this.startPoint.getX() == other.endPoint.getX()) {
            return (this.startPoint);
        }
        // Edge cases if the lines have same x and 1 common point
        if (this.startPoint.getX() == other.startPoint.getX() && this.endPoint.getY() == other.startPoint.getY()) {
            return (other.startPoint);
        }
        if (this.startPoint.getX() == other.startPoint.getX() && this.startPoint.getY() == other.endPoint.getY()) {
            return (this.startPoint);
        }
        // checking to avoid dividing by zero
        if (determinant == 0) {
            return null;
        }

        double newX = (b2 * c1 - b1 * c2) / determinant;
        double newY = (a1 * c2 - a2 * c1) / determinant;
        Point p1 = new Point(Math.round(newX), Math.round(newY));
        Point p2 = new Point(newX, newY);

        if (isLocatedOnTwoSegment(p1, other) || isLocatedOnTwoSegment(p2, other)) {
            return new Point(newX, newY);

        } else {
            return null;
        }
    }

    /**
     * @param other - another line to compare with.
     * @return return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return ((this.startPoint.equals(other.startPoint)) && (this.endPoint.equals(other.endPoint)));
    }

    /**
     * This method returns true if the point is located in 2 segments, else false.
     *
     * @param l2 geometry.Line.
     * @param p1 Point.
     * @return true if the point is located on the two segments, false otherwise.
     */
    public boolean isLocatedOnTwoSegment(Point p1, Line l2) {
        return ((p1.getX() <= Math.max(this.startPoint.getX(), this.endPoint.getX()))
                && (p1.getX() >= Math.min(this.startPoint.getX(), this.endPoint.getX()))
                && (p1.getY() <= Math.max(this.startPoint.getY(), this.endPoint.getY()))
                && (p1.getY() >= Math.min(this.startPoint.getY(), this.endPoint.getY()))
                // Also checking for other geometry.Line
                && (p1.getX() <= Math.max(l2.startPoint.getX(), l2.endPoint.getX()))
                && (p1.getX() >= Math.min(l2.startPoint.getX(), l2.endPoint.getX()))
                && (p1.getY() <= Math.max(l2.startPoint.getY(), l2.endPoint.getY()))
                && (p1.getY() >= Math.min(l2.startPoint.getY(), l2.endPoint.getY())));

    }

    /**
     * This method returns true if the point is located on the segment, else return false.
     *
     * @param p1 a given point to check if it's located on the line.
     * @return true if the point is located on the line, otherwise-false.
     */
    public boolean isLocatedOnOneSegment(Point p1) {
        Point roundPoint = new Point(Math.round(p1.getX()), Math.round(p1.getY()));
        // Checking if we get a point that is the start point or the end point of the line
        if (p1.equals(this.startPoint) || p1.equals(this.endPoint)
                || roundPoint.equals(this.startPoint) || roundPoint.equals(this.endPoint)) {
            return true;
        }

        // Checking that the distance between the start to to the point, plus the distance from the point to
        // the end equals to the line length
        return ((p1.distance(startPoint) + p1.distance(endPoint) == startPoint.distance(endPoint))
                || (roundPoint.distance(startPoint) + roundPoint.distance(endPoint) == startPoint.distance(endPoint))
                || (roundPoint.distance(startPoint) + roundPoint.distance(endPoint)
                == Math.round(startPoint.distance(endPoint))));
    }

    /**
     * If this line does not intersect with the rectangle, return null. Otherwise, return the closest intersection
     * point to the start of the line.
     *
     * @param rect a given rectangle.
     * @return the closet point intersection to start of line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List listOfPoints = rect.intersectionPoints(this);
        // Checking if the list is not empty
        if (listOfPoints.isEmpty()) {
            return null;
        } else {
            // Assigning the minimum distance to be the distance of the first point in the List.
            double min = this.startPoint.distance((Point) listOfPoints.get(0));
            Point minPoint = (Point) listOfPoints.get(0);
            // Running on all list of points we found
            for (int i = 1; i < listOfPoints.size(); i++) {
                // Checking the minimum points by calculating distance
                if (this.startPoint.distance((Point) listOfPoints.get(i)) < min) {
                    min = this.startPoint.distance((Point) listOfPoints.get(i));
                    minPoint = ((Point) listOfPoints.get(i));
                }
            }
            return minPoint;
        }
    }

    /**
     * Draw the shape with fill.
     *
     * @param drawSurface the draw surface to draw on
     * @param color       the drawing color
     */
    public void drawFill(DrawSurface drawSurface, Color color) {
        drawSurface.setColor(color);
        drawSurface.drawLine((int) startPoint.getX(),
                (int) startPoint.getY(),
                (int) endPoint.getX(),
                (int) endPoint.getY());
    }


    /**
     * Draw the shape with edge only.
     *
     * @param drawSurface the draw surface to draw on
     * @param color       the drawing color
     */
    public void drawEdge(DrawSurface drawSurface, Color color) {
        drawFill(drawSurface, color);
    }
}
