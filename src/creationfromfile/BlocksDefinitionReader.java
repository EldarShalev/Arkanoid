package creationfromfile;

import interfaces.BlockCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines all the blocks from a given file.
 */
public class BlocksDefinitionReader {

    /**
     * Gets the blocksfromsymbolsfactory map from the file.
     *
     * @param reader - our file reader
     * @return a map with the details of the file
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        BlocksFromSymbolsFactory blocksFromSymbolsFactory = new BlocksFromSymbolsFactory();
        CustomBlocksFactory customBlocksFactory = new CustomBlocksFactory();

        // The file stream buffer
        BufferedReader lineReader;

        // Current lines we will check
        Row currentProperty;
        Row currentSymbol;
        BlockCreator currentBlockCreator;
        String[] currentLine;

        // Defaults list of rows
        List<Row> defaults = new ArrayList<>();

        try {
            //Trying to read from file
            lineReader = new BufferedReader(reader);
            String line = lineReader.readLine();
            while (line != null) {
                currentLine = line.split(" ");
                // Skip empty lines
                while (currentLine[0].equals("") || currentLine[0].startsWith("#")) {
                    line = lineReader.readLine();
                    if (line != null) {
                        currentLine = line.split(" ");
                    }
                }

                // Checking for default line
                if (currentLine[0].equals("default")) {
                    for (int i = 1; i < currentLine.length; i++) {
                        defaults.add(new Row(currentLine[i]));
                    }
                    // Checking for 'bdef' lines
                } else if (currentLine[0].equals("bdef") && currentLine[1].startsWith("symbol:")) {
                    currentBlockCreator = new BasicBlockCreator();
                    for (int i = 2; i < currentLine.length; i++) {
                        currentProperty = new Row(currentLine[i]);
                        // Adding the property of the block
                        currentBlockCreator = customBlocksFactory.addProperty(currentBlockCreator, currentProperty);
                    }
                    for (Row row : defaults) {
                        // Adding the property of the block
                        currentBlockCreator = customBlocksFactory.addProperty(currentBlockCreator, row);
                    }
                    // For new symbol definition
                    currentSymbol = new Row(currentLine[1]);
                    blocksFromSymbolsFactory.addBlockCreator(currentSymbol.getValue(), currentBlockCreator);
                } else if (currentLine[0].equals("sdef") && currentLine[1].startsWith("symbol:")) {
                    // If we have sdef definition in the text
                    currentSymbol = new Row(currentLine[1]);
                    currentProperty = new Row(currentLine[2]);
                    // We add the symbol
                    blocksFromSymbolsFactory.addSpacer(currentSymbol.getValue(),
                            Integer.parseInt(currentProperty.getValue()));
                } else {
                    System.out.println("Failed to parse blocks definitions");
                }
                line = lineReader.readLine();
            }
        } catch (IOException ex) {
            System.out.println("IO Exception thrown: " + ex.toString());
            return null;
        }

        return blocksFromSymbolsFactory;
    }

    /**
     * @param filename a given file name to try to read.
     * @return a new Block from symbol factory which has all the details about how to make blocks.
     */
    public static BlocksFromSymbolsFactory fromFile(String filename) {
        Reader fileReader = null;
        File newFile = new File(filename);
        try {
            if (newFile.exists()) {
                fileReader = new FileReader(filename);
            } else {
                // Try new file and read from it
                fileReader = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(filename));
            }
            return fromReader(fileReader);
        } catch (FileNotFoundException | NullPointerException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ex) {
                    System.out.println("IO Exception thrown while closing stream: " + ex.toString());
                }
            }
        }
    }
}