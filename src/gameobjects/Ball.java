package gameobjects;

import gamecontrol.CollisionInfo;
import gamecontrol.GameLevel;
import gamecontrol.GameEnvironment;
import geometry.Line;
import geometry.Point;

import interfaces.HitListener;
import interfaces.HitNotifier;
import interfaces.Sprite;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static gameobjects.Constants.DEATH_REGION;


/**
 * This class creates ball using point and radius. Also creating random balls.
 */
public class Ball implements Sprite, HitNotifier {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity v1;
    private GameEnvironment gameEnvironment;
    private List<HitListener> hitListeners;


    // constructors

    /**
     * Construct a ball using Point center and a radius.
     *
     * @param center          - Center of the ball.
     * @param r               Radius of the ball.
     * @param color           Color of the ball.
     * @param gameEnvironment the game Environment.
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.radius = r;
        this.center = center;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
        this.hitListeners = new ArrayList<HitListener>();

    }

    /**
     * Construct a ball using x and y for center and a radius.
     *
     * @param x               for x of the point.
     * @param y               for y of the point
     * @param r               Radius of the ball.
     * @param color           Color of the ball.
     * @param gameEnvironment the game environment of the ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;

    }


    /**
     * @return the x of the center of the ball
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return the y of the center of the ball
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return the radius of the ball
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * @return the center of the ball.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface a Surface given.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) center.getX(), (int) center.getY(), radius);
        surface.setColor(Color.black);
        surface.drawCircle((int) center.getX(), (int) center.getY(), radius);
    }

    /**
     * Set velocity with given new velocity object.
     *
     * @param v Given velocity
     */
    public void setVelocity(Velocity v) {
        this.v1 = v;
    }


    /**
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.v1;
    }

    /**
     * @return the game environment.
     */
    public GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }

    /**
     * This method checks according to the velocity, where the ball should be in it's next step.
     * If it doesn't exceed the frame limit, and doesn't get inside any collidable, the ball is re-center to new
     * position according to velocity.
     * If it does exceeds, it gets a negative velocity (dx or dy) to keep staying on it's limited frames.
     *
     * @param dt - definition of time.
     */
    public void moveOneStep(double dt) {

        // Creating new point for the next center that the ball is going to be
        Point nextCenter = this.getVelocity().applyToPoint(this.center, dt);
        // Computing the ball trajectory, we have two to avoid edge cases in which we need to round the x or the y
        Line trajectory1 = new Line(this.center, nextCenter);
        Line trajectory2 = new Line(new Point(Math.round(this.center.getX()), Math.round(this.center.getY()))
                , new Point(Math.round(nextCenter.getX()), Math.round(nextCenter.getY())));
        // getting the collision info if the trajectory intersect with any collidable
        CollisionInfo collisionInfo1 = getGameEnvironment().getClosestCollision(trajectory1);
        CollisionInfo collisionInfo2 = getGameEnvironment().getClosestCollision(trajectory2);

        // as long as we have an intersection
        if (collisionInfo1 != null || collisionInfo2 != null) {
            // Edge case for Math.round cases
            if (collisionInfo2 != null) {
                collisionInfo1 = collisionInfo2;
            }
            if (collisionInfo1.collisionObject().equals(DEATH_REGION)) {
                this.center = this.getVelocity().applyToPoint(this.center, dt);
                this.notifyHit(this);
                return;
            }
            // assigning the new velocity to be after he hit the collidable- means, change his dx ot dy direction
            this.setVelocity(collisionInfo1.collisionObject().hit(this, collisionInfo1.collisionPoint(),
                    this.getVelocity()));
            // assigning the next center to be "almost" on the collision point with the collidable
            nextCenter = centerAfterCollision(collisionInfo1, trajectory1);

            // Paddle "eating ball" checking
            // the next 2 conditions are to assure that the next center is not going to be inside the moving paddle, if
            // the paddle is moving (if the key is pressed) to avoid the ball get inside the paddle.
            if (gameEnvironment.getPaddle().getKeyboard().isPressed(KeyboardSensor.RIGHT_KEY)
                    && gameEnvironment.getPaddle().getNextRectangleRight(dt).isPointInsideTheRectangle(nextCenter)) {
                // if we found that the ball is going to be inside the moving paddle- assign the ball to be infront
                // of the moving paddle so the paddle will not contain the ball
                this.center = new Point(gameEnvironment.getPaddle().getNextRectangleRight(dt).getUpperLeft().getX()
                        + gameEnvironment.getPaddle().getNextRectangleRight(dt).getWidth() + radius, nextCenter.getY());
                this.setVelocity(Velocity.fromAngleAndSpeed(60, this.getVelocity().getSpeed()));
                // same check for left key presses
            } else if (gameEnvironment.getPaddle().getKeyboard().isPressed(KeyboardSensor.LEFT_KEY)
                    && gameEnvironment.getPaddle().getNextRectangleLeft(dt).isPointInsideTheRectangle(nextCenter)) {
                this.center = new Point(gameEnvironment.getPaddle().getNextRectangleLeft(dt).getUpperLeft().getX()
                        - radius, nextCenter.getY());
                // same assigning but for left side
                this.setVelocity(Velocity.fromAngleAndSpeed(300, this.getVelocity().getSpeed()));


                // last check - to avoid that the point will be inside any collidable , also the statics ones
            } else if (!gameEnvironment.isPointContainedInCollidable(nextCenter)) {
                this.center = nextCenter;
            }

        } else {  //if collision info is null
            this.center = nextCenter;
        }


    }


    /**
     * @param collisionInfo a given collision info.
     * @param trajectory    a given line to that intersect with the collidable.
     * @return the new point according to the side of the rectangle that the trajectory intersected with.
     */
    public Point centerAfterCollision(CollisionInfo collisionInfo, Line trajectory) {


        Point p1 = null;
        // Checking if the collision point is on the top line of the rectangle
        if (collisionInfo.collisionObject().getRectangle().getName(collisionInfo.collisionPoint())
                .equals("Top")) {
            p1 = new Point(collisionInfo.collisionPoint().getX(), collisionInfo.collisionPoint().getY() - 1);
        }
        // Checking if the collision point is on the left line of the rectangle
        if (collisionInfo.collisionObject().getRectangle().getName(collisionInfo.collisionPoint())
                .equals("Left")) {
            p1 = new Point(collisionInfo.collisionPoint().getX() - 1, collisionInfo.collisionPoint().getY());

        }
        // Checking if the collision point is on the right line of the rectangle
        if (collisionInfo.collisionObject().getRectangle().getName(collisionInfo.collisionPoint())
                .equals("Right")) {
            p1 = new Point(collisionInfo.collisionPoint().getX() + 1, collisionInfo.collisionPoint().getY());
        }
        // Checking if the collision point is on the bot line of the rectangle
        if (collisionInfo.collisionObject().getRectangle().getName(collisionInfo.collisionPoint())
                .equals("Bot")) {
            p1 = new Point(collisionInfo.collisionPoint().getX(), collisionInfo.collisionPoint().getY() + 1);

        }
        return p1;
    }


    /**
     * Control the ball changing during time.
     *
     * @param dt - definition of time.
     */
    public void timePassed(double dt) {
        moveOneStep(dt);
    }

    /**
     * @param g a gamecontrol.GameLevel.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Remove the ball from the game.
     *
     * @param game a game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }


    /**
     * @param hl adding hl to ball.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * @param hl removing hl from ball.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * @param hitter the ball that hit.
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(DEATH_REGION, hitter);
        }
    }
}