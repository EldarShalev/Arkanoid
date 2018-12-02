package creationfromfile;

import gameobjects.Block;
import gameobjects.Velocity;
import interfaces.LevelInformation;
import interfaces.Sprite;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is for default level information which is used to clone and makeing informations for each level.
 */
public class DefaultLevelInformation implements LevelInformation, Cloneable {
    // All the features a game can have
    private String name;
    private List<Velocity> ballVelocities = new LinkedList<>();
    private List<Block> blocks = new LinkedList<>();
    private int numOfBlocks;
    private Integer paddleSpeed;
    private Integer paddleWidth;
    private Sprite background;
    private Color backgroundColor = Color.BLACK;
    private Integer blocksStartX;
    private Integer blocksStartY;
    private Integer rowHeight;
    private BlocksFromSymbolsFactory symbolsFactory;


    /**
     * Number of balls int.
     *
     * @return the int
     */
    @Override
    public int numberOfBalls() {
        return ballVelocities.size();
    }

    /**
     * The initial velocity of each ball.
     *
     * @return the list
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return ballVelocities;
    }

    /**
     * Paddle speed int.
     *
     * @return the int
     */
    @Override
    public int paddleSpeed() {
        return paddleSpeed;
    }

    /**
     * Paddle width int.
     *
     * @return the int
     */
    @Override
    public int paddleWidth() {
        return paddleWidth;
    }

    /**
     * The level name will be displayed at the top of the screen.
     *
     * @return the string
     */
    @Override
    public String levelName() {
        return name;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return the background
     */
    @Override
    public Sprite getBackground() {
        return background;
    }

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return the list
     */
    @Override
    public List<Block> blocks() {
        return blocks;
    }

    /**
     * Number of blocks to remove int.
     *
     * @return the int
     */
    public int numberOfBlocksToRemove() {
        return numOfBlocks;
    }

    /**
     * Countdown color color.
     *
     * @return the color
     */

    public Color countdownColor() {
        return Color.BLACK;
    }

    /**
     * Gets background color.
     *
     * @return the background color
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name1 a given name to set.
     */
    public void setName(String name1) {
        this.name = name1;
    }

    /**
     * @return the list of velocities.
     */
    public List<Velocity> getBallVelocities() {
        return ballVelocities;
    }

    /**
     * @param ballVelocities1 a given velocities to set.
     */
    public void setBallVelocities(List<Velocity> ballVelocities1) {
        this.ballVelocities = ballVelocities1;
    }

    /**
     * @param velocity a velocity to add.
     */
    public void addVelocity(Velocity velocity) {
        this.ballVelocities.add(velocity);
    }

    /**
     * @return the list of blocks.
     */
    public List<Block> getBlocks() {
        return blocks;
    }

    /**
     * @param blocks1 set the blocks.
     */
    public void setBlocks(List<Block> blocks1) {
        this.blocks = blocks1;
    }

    /**
     * @return num of blocks.
     */
    public int getNumOfBlocks() {
        return numOfBlocks;
    }

    /**
     * @param numOfBlocks1 set num of blocks.
     */
    public void setNumOfBlocks(int numOfBlocks1) {
        this.numOfBlocks = numOfBlocks1;
    }

    /**
     * @param paddleSpeed1 set paddle speed.
     */
    public void setPaddleSpeed(Integer paddleSpeed1) {
        this.paddleSpeed = paddleSpeed1;
    }

    /**
     * @return paddle width.
     */
    public Integer getPaddleWidth() {
        return paddleWidth;
    }

    /**
     * @param paddleWidth1 paddle width.
     */
    public void setPaddleWidth(Integer paddleWidth1) {
        this.paddleWidth = paddleWidth1;
    }

    /**
     * @param background1 given background.
     */
    public void setBackground(Sprite background1) {
        this.background = background1;
    }

    /**
     * @param backgroundColor1 given color for background.
     */
    public void setBackgroundColor(Color backgroundColor1) {
        this.backgroundColor = backgroundColor1;
    }

    /**
     * @param blocksStartX1 set block x.
     */
    public void setBlocksStartX(Integer blocksStartX1) {
        this.blocksStartX = blocksStartX1;
    }

    /**
     * @param blocksStartY1 set block y.
     */
    public void setBlocksStartY(Integer blocksStartY1) {
        this.blocksStartY = blocksStartY1;
    }

    /**
     * @param rowHeight1 the row height.
     */
    public void setRowHeight(Integer rowHeight1) {
        this.rowHeight = rowHeight1;
    }

    /**
     * @param symbolsFactory1 set the symbol factory.
     */
    public void setSymbolsFactory(BlocksFromSymbolsFactory symbolsFactory1) {
        this.symbolsFactory = symbolsFactory1;
    }

    /**
     * @param line a given line to start from.
     */
    public void addBlocksAndSpacers(String line) {
        String[] split = line.split("");
        Integer currentX = blocksStartX;
        // Creating the blocks using all our information and puting them in the list of blocks
        for (String symbol : split) {
            if (symbolsFactory.isBlockSymbol(symbol)) {
                Block block = symbolsFactory.getBlock(symbol, currentX, blocksStartY);
                // For every width of the blocks in the same line
                currentX += (int) block.getWidth();
                blocks.add(block);
            } else if (symbolsFactory.isSpaceSymbol(symbol)) {
                // For every width of the blocks in the same line
                currentX += symbolsFactory.getSpaceWidth(symbol);
            } else {
                throw new RuntimeException("Unknown symbol given: " + symbol);
            }
        }
        // for net row of blocks for other height.
        blocksStartY += rowHeight;
    }

    /**
     * @return the exact level information of the game for the next round (we must this method in order to avoid
     * overriding blocks and files we have.
     */
    public LevelInformation clone() {
        try {
            // Trying to clone
            DefaultLevelInformation cloned = (DefaultLevelInformation) super.clone();
            List<Block> newList = new LinkedList<>();
            for (Block b : cloned.blocks) {
                newList.add(b.clone());
            }
            cloned.setBlocks(newList);
            return cloned;
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}
