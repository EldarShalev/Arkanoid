package geometry;

import biuoop.DrawSurface;
import interfaces.Drawable;

import java.awt.Color;
import java.util.ArrayList;

/**
 * This class creates rectangle from lines.
 */
public class Rectangle implements Drawable {

    // Members
    private Point upperLeft;
    private double width;
    private double height;

    private Line top;
    private Line bot;
    private Line right;
    private Line left;

    /**
     * Constructor -Create a new rectangle with location and width/height.
     *
     * @param upperLeft the upper left point of the rectangle.
     * @param width     the width of the rectangle.
     * @param height    the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        init();
    }

    /**
     * This method initialize the parameters points of the rectangle.
     */
    public void init() {
        this.top = new Line(getUpperLeft(), new Point(getUpperLeft().getX() + getWidth(), getUpperLeft().getY()));
        this.bot = new Line(new Point(getUpperLeft().getX(), getUpperLeft().getY() + getHeight())
                , new Point(getUpperLeft().getX() + getWidth(), getUpperLeft().getY() + getHeight()));

        this.left = new Line(getUpperLeft(), new Point(getUpperLeft().getX(), getUpperLeft().getY() + getHeight()));

        this.right = new Line(new Point(getUpperLeft().getX() + getWidth(), getUpperLeft().getY())
                , new Point(getUpperLeft().getX() + getWidth(), getUpperLeft().getY() + getHeight()));


    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     *
     * @param line a given line.
     * @return the list of point that that intersects with the given line.
     */
    public java.util.List intersectionPoints(Line line) {
        ArrayList<Point> listOfPoints = new ArrayList<>();
        checkingAllLines(listOfPoints, line);
        return listOfPoints;
    }

    /**
     * This method checks intersection with given line to every side of the rectangle.
     *
     * @param lst  new list for insert intersection points.
     * @param line a given geometry.Line to check the intersection with every side of the rectangle.
     */
    public void checkingAllLines(ArrayList<Point> lst, Line line) {
        if (line.isIntersecting(top)) {
            lst.add(line.intersectionWith(top));
        }

        if (line.isIntersecting(bot)) {
            lst.add(line.intersectionWith(bot));
        }

        if (line.isIntersecting(right)) {
            lst.add(line.intersectionWith(right));
        }
        if (line.isIntersecting(left)) {
            lst.add(line.intersectionWith(left));
        }
    }

    /**
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @return the bot right point of the rectangle.
     */
    public Point getBotRight() {
        return (new Point(getUpperLeft().getX() + getWidth(), getUpperLeft().getY() + getHeight()));
    }

    /**
     * @return bottom line.
     */
    public Line getBot() {
        return this.bot;
    }

    /**
     * @return left line.
     */
    public Line getLeft() {
        return this.left;
    }

    /**
     * @return right line.
     */
    public Line getRight() {
        return this.right;
    }

    /**
     * @return top line.
     */
    public Line getTop() {
        return this.top;
    }

    /**
     * @param point a given point.
     * @return the name of the side of the rectangle that the point is loctaed on it.
     */
    public String getName(Point point) {

        if (this.getTop().isLocatedOnOneSegment(point)) {
            return "Top";
        }
        if (this.getLeft().isLocatedOnOneSegment(point)) {
            return "Left";
        }
        if (this.getRight().isLocatedOnOneSegment(point)) {
            return "Right";
        }
        if (this.getBot().isLocatedOnOneSegment(point)) {
            return "Bot";
        }
        return null;
    }

    /**
     * @param point a given point.
     * @return true if the point is located inside the rectangle, false otherwise.
     */
    public boolean isPointInsideTheRectangle(Point point) {
        return (upperLeft.getX() <= point.getX() && point.getX() <= upperLeft.getX() + width
                && upperLeft.getY() <= point.getY() && point.getY() <= upperLeft.getY() + height);
    }


    /**
     * Draw the shape with fill.
     *
     * @param drawSurface the draw surface to draw on
     * @param color       the drawing color
     */
    public void drawFill(DrawSurface drawSurface, Color color) {
        drawSurface.setColor(color);
        drawSurface.fillRectangle((int) getUpperLeft().getX(),
                (int) getUpperLeft().getY(),
                (int) getWidth(),
                (int) getHeight());
    }

    /**
     * Draw the shape with edge only.
     *
     * @param drawSurface the draw surface to draw on
     * @param color       the drawing color
     */
    public void drawEdge(DrawSurface drawSurface, Color color) {
        drawSurface.setColor(color);
        drawSurface.drawRectangle((int) getUpperLeft().getX(),
                (int) getUpperLeft().getY(),
                (int) getWidth(),
                (int) getHeight());
    }


    /**
     * @param w given ine for width.
     */
    public void setWidth(Integer w) {
        this.width = w.doubleValue();
        initialize();
    }

    /**
     * @param h given value for height.
     */
    public void setHeight(Integer h) {
        this.height = h.doubleValue();
        initialize();
    }

    /**
     * Initialize border lines.
     */
    public void initialize() {
        this.top = new Line(getUpperLeft(), new Point(getUpperLeft().getX() + getWidth(), getUpperLeft().getY()));
        this.left = new Line(getUpperLeft(), new Point(getUpperLeft().getX(), getUpperLeft().getY() + getHeight()));
        this.right = new Line(new Point(getUpperLeft().getX() + getWidth(), getUpperLeft().getY()),
                new Point(getUpperLeft().getX() + getWidth(), getUpperLeft().getY() + getHeight()));
        this.bot = new Line(new Point(getUpperLeft().getX(), getUpperLeft().getY() + getHeight()),
                new Point(getUpperLeft().getX() + getWidth(), getUpperLeft().getY() + getHeight()));
    }
}