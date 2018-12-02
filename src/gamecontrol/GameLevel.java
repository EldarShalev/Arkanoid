package gamecontrol;


import gameobjects.BlockRemover;
import gameobjects.Counter;
import gameobjects.Paddle;
import gameobjects.SpriteCollection;
import gameobjects.BallRemover;
import gameobjects.ScoreTrackingListener;
import gameobjects.ScoreIndicator;
import gameobjects.LivesIndicator;
import gameobjects.NameIndicator;
import gameobjects.Block;
import gameobjects.Ball;
import interfaces.Animation;
import interfaces.Collidable;
import interfaces.LevelInformation;
import interfaces.Sprite;
import biuoop.DrawSurface;
import biuoop.GUI;
import geometry.Point;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


import static gameobjects.Constants.BLOCK_LEFT;
import static gameobjects.Constants.BLOCK_RIGHT;
import static gameobjects.Constants.BLOCK_TOP;
import static gameobjects.Constants.DEATH_REGION;

/**
 * A class of game to control the running game.
 */
public class GameLevel implements Animation {
    // Members of game
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private String levelName;
    private Paddle paddle;
    // Counters
    private Counter remainingBlocks;
    private Counter ballsCounter;
    private Counter score;
    private Counter numOfLives;
    // Listeners
    private BlockRemover blockRemover;
    private BallRemover ballRemover;
    private ScoreTrackingListener scoreSum;
    private ScoreIndicator scoreIndicator;
    private LivesIndicator livesIndicator;
    private NameIndicator nameIndicator;
    private LevelInformation levelInformation;

    // Members of Animation
    private AnimationRunner runner;
    private boolean running;
    private GUI gui;
    private biuoop.KeyboardSensor keyboard;

    /**
     * @param levelInformation a given level to get the details from and assign to other members.
     * @param keyboardSensor   a keyboard in which the game is played on.
     * @param animationRunner  the animation runner including the gui which the game is played on.
     * @param score            the indicator of score.
     * @param numOfLives       the indicator for num of lives.
     */
    public GameLevel(LevelInformation levelInformation, KeyboardSensor keyboardSensor, AnimationRunner
            animationRunner, Counter score, Counter numOfLives) {
        // Game flow accessing
        this.levelInformation = levelInformation;
        this.keyboard = keyboardSensor;
        this.runner = animationRunner;
        this.score = score;
        this.numOfLives = numOfLives;

        // Other members to initialize for further using
        this.remainingBlocks = new Counter(levelInformation.numberOfBlocksToRemove());
        this.ballsCounter = new Counter(levelInformation.numberOfBalls());
        this.blockRemover = new BlockRemover(this, remainingBlocks);
        this.ballRemover = new BallRemover(this, ballsCounter);
        this.scoreSum = new ScoreTrackingListener(score);
        this.scoreIndicator = new ScoreIndicator(score);
        this.livesIndicator = new LivesIndicator(numOfLives);
        this.levelName = levelInformation.levelName();
        this.nameIndicator = new NameIndicator(levelName);
        this.gui = animationRunner.getGui();
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.paddle = new Paddle(keyboardSensor, levelInformation.paddleWidth(), levelInformation.paddleSpeed());


    }

    /**
     * Add a collidable.
     *
     * @param c A interfaces.Collidable.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);

    }

    /**
     * @param c a collidable to remove from the game.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * @param s a sprite to remove from the game.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Add a interfaces.Sprite.
     *
     * @param s A interfaces.Sprite.
     */
    public void addSprite(Sprite s) {
        if (s != null) {
            this.sprites.addSprite(s);
        }
    }

    /**
     * Initialize a new game.
     */
    public void initialize() {
        // Adding the background
        sprites.addSprite(levelInformation.getBackground());
        // Adding the paddle.
        paddle.addToGame(this);

        // Adding the frame
        BLOCK_TOP.addToGame(this);
        BLOCK_RIGHT.addToGame(this);
        BLOCK_LEFT.addToGame(this);
        DEATH_REGION.addToGame(this);

        // Adding the blocks
        for (Block block : levelInformation.blocks()) {
            block.addToGame(this);
            // Adding the listeners to the block
            block.addHitListener(blockRemover);
            block.addHitListener(scoreSum);

        }
        //Adding the indicators
        scoreIndicator.addToGame(this);
        livesIndicator.addToGame(this);
        nameIndicator.addToGame(this);

    }

    /**
     * Play one turn (1 lives).
     */
    public void playOneTurn() {
        // Creation of the balls
        creationOfBalls();
        // Setting the paddle in the center
        paddle.setStartingRectangle();
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        while (numOfLives.getValue() > 0 && this.blockRemover.getRemainingBlocks().getValue() > 0) {
            playOneTurn();
        }
    }

    /**
     * @return true if the game should stop.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * The logic of the game.
     *
     * @param d  a given surface.
     * @param dt - definition of time
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // Checking for pause
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY,
                    new PauseScreen()));
        }
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);

        // Checking for balls left
        if (this.ballsCounter.getValue() == 0) {
            numOfLives.decrease(1);
            ballsCounter.increase(levelInformation.numberOfBalls());
            this.running = false;
        }
        // Checking for blocks left
        if (this.remainingBlocks.getValue() == 0) {
            score.increase(100);
            this.running = false;
        }
    }

    /**
     * This class creates balls.
     */
    public void creationOfBalls() {

        List<Ball> listOfBalls = new ArrayList<Ball>();
        Point startPointOfBall;
        Ball ball;
        for (int i = 0; i < levelInformation.initialBallVelocities().size(); i++) {
            startPointOfBall = new Point(paddle.paddleLeftPointStarting().getX()
                    + (levelInformation.paddleWidth() / 2), paddle.paddleLeftPointStarting().getY());
            ball = new Ball(startPointOfBall, 4, Color.white, environment);
            listOfBalls.add(ball);
            // Assigning each velocity from the list to our new ball
            ball.setVelocity(levelInformation.initialBallVelocities().get(i));
            ball.addHitListener(ballRemover);
            ball.addToGame(this);

        }
    }
}