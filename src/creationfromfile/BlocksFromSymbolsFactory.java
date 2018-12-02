package creationfromfile;

import gameobjects.Block;

import interfaces.BlockCreator;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a factory for all the blocks, the method which we use for our blocks definition.
 */
public class BlocksFromSymbolsFactory {
    // Members
    private Map<String, Integer> spacerWidths = new HashMap<>();
    private Map<String, BlockCreator> blockCreators = new HashMap<>();

    /**
     * @param s a given string
     * @return true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * @param s a given string
     * @return true if 's' is a valid space symbol.
     */
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    /**
     * @param s a given string
     * @return the width in pixels associated with the given symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * @param s a given string.
     * @param x a given x position.
     * @param y a given y position.
     * @return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }

    /**
     * Adding to the block creators map the symbol and what it describes.
     *
     * @param symbol       a given symbol.
     * @param blockCreator a given block creator.
     */
    public void addBlockCreator(String symbol, BlockCreator blockCreator) {
        blockCreators.put(symbol, blockCreator);
    }

    /**
     * Adding to the block creators map the symbol and what it describes.
     *
     * @param symbol a given symbol.
     * @param width  a given block creator.
     */
    public void addSpacer(String symbol, Integer width) {
        spacerWidths.put(symbol, width);
    }
}