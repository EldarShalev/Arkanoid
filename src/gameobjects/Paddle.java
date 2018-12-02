package gameobjects;

import gamecontrol.GameLevel;
import geometry.Rectangle;
import geometry.Point;

import interfaces.Collidable;
import interfaces.Sprite;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;


import java.awt.Color;

/**
 * This class creates the paddle which can move inside the game.
 */
public class Paddle implements Sprite, Collidable {
    //members and constants.
    private int width;
    private int movementSpeed;
    private Rectangle rect;
    private biuoop.KeyboardSensor keyboard;
    private Point paddleStartingPosition;


    /**
     * The constructor of paddle.
     *
     * @param keyboardSensor a given keyboard.
     * @param width          a given width.
     * @param movementSpeed  a given movement speed.
     */
    public Paddle(KeyboardSensor keyboardSensor, int width, int movementSpeed) {
        this.width = width;
        this.movementSpeed = movementSpeed;
        this.paddleStartingPosition = new Point((Constants.GAME_WIDTH - width) / 2,
                Constants.GAME_HEIGHT - Constants.PADDLE_HEIGHT - 20);
        this.rect = new Rectangle(paddleStartingPosition, width, Constants.PADDLE_HEIGHT);
        this.keyboard = keyboardSensor;

    }

    /**
     * @return the keyboard.
     */
    public KeyboardSensor getKeyboard() {
        return this.keyboard;
    }

    /**
     * Moving left.
     *
     * @param dt - definition of time.
     */
    public void moveLeft(double dt) {
        if (rect.getUpperLeft().getX() > 20) {
            this.rect = getNextRectangleLeft(dt);
        } else {
            this.rect = rect;
        }
    }

    /**
     * Moving Right.
     *
     * @param dt - definition of time.
     */
    public void moveRight(double dt) {
        if (rect.getUpperLeft().getX() + this.width + 20 < Constants.GAME_WIDTH) {
            this.rect = getNextRectangleRight(dt);
        } else {
            this.rect = rect;
        }
    }

    /**
     * @param dt - definition of time.
     * @return the next rectangle to the right.
     */
    public Rectangle getNextRectangleRight(double dt) {
        return (new Rectangle(new Point(rect.getUpperLeft().getX() + this.movementSpeed * dt,
                rect.getUpperLeft().getY()), this.width, Constants.PADDLE_HEIGHT));
    }

    /**
     * @param dt - definition of time.
     * @return the next rectangle to the left.
     */
    public Rectangle getNextRectangleLeft(double dt) {
        return new Rectangle(new Point(rect.getUpperLeft().getX() - this.movementSpeed * dt,
                rect.getUpperLeft().getY()), this.width, Constants.PADDLE_HEIGHT);
    }


    // interfaces.Sprite

    /**
     * checking what is the next step - if need to move the paddle left,right or do nothing.
     *
     * @param dt - definition of time.
     */
    public void timePassed(double dt) {
        if (keyboard.isPressed(keyboard.LEFT_KEY)) {
            moveLeft(dt);
        } else if (keyboard.isPressed(keyboard.RIGHT_KEY)) {
            moveRight(dt);
        }

    }

    /**
     * Drawing the paddle on the game.
     *
     * @param d a given surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.yellow);
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                this.width, Constants.PADDLE_HEIGHT);
    }

    // interfaces.Collidable

    /**
     * @return the rectangle of the paddle.
     */
    public Rectangle getRectangle() {
        return this.rect;
    }

    /**
     * @return the start position of the paddle.
     */
    public Point paddleLeftPointStarting() {
        return this.paddleStartingPosition;
    }

    /**
     * Setting the paddle in the starting rectangle.
     */
    public void setStartingRectangle() {
        this.rect = new Rectangle(paddleStartingPosition, width, Constants.PADDLE_HEIGHT);
    }

    /**
     * @param ball            a given ball.
     * @param collisionPoint  - The point that the ball is going to intersect with the paddle.
     * @param currentVelocity The velocity of the ball.
     * @return the new velocity according to the way the ball is going to intersect with (from top, bot,right, ot left).
     */
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity) {
        Point fullPoint = new Point(Math.round(collisionPoint.getX()), Math.round(collisionPoint.getY()));
        // If the hit is going to be on the top line of the gameobjects.Paddle
        if (this.rect.getTop().isLocatedOnOneSegment(collisionPoint)) {
            // Getting the region of where the ball hit.
            int regionResult = angleAccordingToPaddleHit(collisionPoint);
            //Edge case for staying with the same velocity
            if (regionResult == Constants.REGION3) {
                // Returning the same velocity x, changing only the vertical position.
                return (new Velocity(currentVelocity.velocityGetX(), currentVelocity.velocityGetY() * (-1)));
            }
            // Return the new velocity according to the desired angle we calculated.
            return (Velocity.fromAngleAndSpeed(regionResult, currentVelocity.getSpeed()));

        } else {         // If the hit is going to be on the right or left line of the gameobjects.Paddle
            if (this.rect.getLeft().isLocatedOnOneSegment(collisionPoint)
                    || this.rect.getRight().isLocatedOnOneSegment(collisionPoint)) {
                // Changing only the horizon position.
                return (new Velocity(currentVelocity.velocityGetX() * (-1), currentVelocity.velocityGetY()));

            } else {
                System.out.println(collisionPoint.getX() + " , " + collisionPoint.getY());
                throw new RuntimeException("Invalid Collision point");
            }
        }
    }

    /**
     * This method checks in which region the ball hit the paddle.
     *
     * @param collisionPoint the Point of collision between the ball to the paddle.
     * @return the new Region for new gameobjects.Velocity.
     */
    public int angleAccordingToPaddleHit(Point collisionPoint) {
        if (collisionPoint.getX() <= this.rect.getLeft().start().getX()
                + Math.round(this.width / Constants.NUMBER_OF_REGIONS)) {
            return Constants.REGION1;
        } else if (collisionPoint.getX() <= this.rect.getLeft().start().getX()
                + 2 * Math.round(this.width / Constants.NUMBER_OF_REGIONS)) {
            return Constants.REGION2;
        } else if (collisionPoint.getX() <= this.rect.getLeft().start().getX()
                + 3 * Math.round(this.width / Constants.NUMBER_OF_REGIONS)) {
            return Constants.REGION3;
        } else if (collisionPoint.getX() <= this.rect.getLeft().start().getX()
                + 4 * Math.round(this.width / Constants.NUMBER_OF_REGIONS)) {
            return Constants.REGION4;
        } else {
            return Constants.REGION5;
        }

    }

    /**
     * Adding the paddle to the game.
     *
     * @param g a given game.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}
