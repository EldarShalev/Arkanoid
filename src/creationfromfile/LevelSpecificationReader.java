package creationfromfile;

import gameobjects.Velocity;
import interfaces.LevelInformation;


import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class taking an a file and making a level information with the details we want according to the file we parsed.
 */
public class LevelSpecificationReader {

    /**
     * Load levels information from file.
     *
     * @param filename file to read from
     * @return list of level information
     */
    public static List<LevelInformation> fromFile(String filename) {
        Reader fileReader = null;
        File newFile = new File(filename);
        try {
            if (newFile.exists()) {
                fileReader = new FileReader(filename);
            } else {
                /// / Checking if the file exists
                fileReader = new InputStreamReader(
                        ClassLoader.getSystemClassLoader().getResourceAsStream(filename));
                //Trying to read from the file
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


    /**
     * @param reader a given file to read from.
     * @return new list of level information with every level.
     */
    public static List<LevelInformation> fromReader(Reader reader) {
        // Levels list
        LinkedList<LevelInformation> levels = new LinkedList<>();

        // The file stream wrapper
        BufferedReader lineReader = null;

        // Current iteration values
        Row currentRow;
        DefaultLevelInformation currentLevel;
        boolean flg = false;
        try {
            lineReader = new BufferedReader(reader);
            String line = lineReader.readLine();
            while (line != null) {
                while (true) {
                    if (line != null) {
                        if (line.equals("") || line.startsWith("#")) {
                            line = lineReader.readLine();
                        } else {
                            break;
                        }
                    } else {
                        flg = true;
                        break;
                    }
                }
                if (flg) {
                    return levels;
                }
                // Creating new map with all the details.
                Map<String, Boolean> hasBeenFound = initHasBeenFoundMap();
                if (line.equals("START_LEVEL")) {
                    // Update properties if we found
                    updateProperty(hasBeenFound, new Row(line));
                    currentLevel = new DefaultLevelInformation();
                    while (line != null) {
                        line = lineReader.readLine();
                        currentRow = new Row(line);
                        if (!currentRow.isHeader()) {
                            // Update properties if we found
                            updateProperty(hasBeenFound, currentRow);
                            setRowValuesToLevel(currentLevel, currentRow);
                        } else if (line.equals("START_BLOCKS")) {
                            // Update properties if we found
                            updateProperty(hasBeenFound, new Row(line));
                            // Blocks section
                            line = lineReader.readLine();
                            while (!line.equals("END_BLOCKS")) {
                                if (!line.equals("")) {
                                    currentLevel.addBlocksAndSpacers(line);
                                }
                                line = lineReader.readLine();
                            }
                            // Update properties if we found
                            updateProperty(hasBeenFound, new Row(line));
                        } else if (line.equals("END_LEVEL")) {
                            // Update properties if we found
                            updateProperty(hasBeenFound, new Row(line));
                            for (Map.Entry<String, Boolean> entry : hasBeenFound.entrySet()) {
                                if (!entry.getValue()) {
                                    throw new Error("A property is missing while parsing file: "
                                            + entry.getKey());
                                }
                            }
                            levels.add(currentLevel);
                            break;
                        } else {
                            throw new RuntimeException("Failed to parse file on line: " + line);
                        }
                    }
                    line = lineReader.readLine();
                } else {
                    throw new RuntimeException("Failed to parse file");
                }

            }
        } catch (IOException ex) {
            System.out.println("IO Exception thrown: " + ex.toString());
            return null;
        }
        // Return the list of levels with the properties.
        return levels;
    }

    /**
     * This method checks if the given string is matched to the text we want to parse.
     *
     * @param currentLevel a given level information.
     * @param row          a given row.
     */
    private static void setRowValuesToLevel(DefaultLevelInformation currentLevel, Row row) {
        if ("level_name".equals(row.getName())) {
            currentLevel.setName(row.getValue());
        } else if ("background".equals(row.getName())) {
            setBackgroundValue(currentLevel, row);
        } else if ("ball_velocities".equals(row.getName())) {
            setBallVelocities(currentLevel, row);
        } else if ("paddle_speed".equals(row.getName())) {
            currentLevel.setPaddleSpeed(Integer.parseInt(row.getValue()));
        } else if ("paddle_width".equals(row.getName())) {
            currentLevel.setPaddleWidth(Integer.parseInt(row.getValue()));
        } else if ("num_blocks".equals(row.getName())) {
            currentLevel.setNumOfBlocks(Integer.parseInt(row.getValue()));
        } else if ("row_height".equals(row.getName())) {
            currentLevel.setRowHeight(Integer.parseInt(row.getValue()));
        } else if ("blocks_start_x".equals(row.getName())) {
            currentLevel.setBlocksStartX(Integer.parseInt(row.getValue()));
        } else if ("blocks_start_y".equals(row.getName())) {
            currentLevel.setBlocksStartY(Integer.parseInt(row.getValue()));
        } else if ("block_definitions".equals(row.getName())) {
            currentLevel.setSymbolsFactory(BlocksDefinitionReader.fromFile(row.getValue()));
        } else {
            throw new RuntimeException("Failed to parse the specification file " + row.getName());
        }
    }


    /**
     * Setting background.
     *
     * @param currentLevel a given level information.
     * @param row          a given row.
     */
    private static void setBackgroundValue(DefaultLevelInformation currentLevel, Row row) {
        // If we have color
        if (row.getValue().startsWith("color(")) {
            ColorsParser colorsParser = new ColorsParser();
            Color color = colorsParser.colorFromString(row.getValue());
            currentLevel.setBackgroundColor(color);
            currentLevel.setBackground(new creationfromfile.Background(color));
        } else if (row.getValue().startsWith("image(") && row.getValue().endsWith(")")) {
            // If we have image
            ImageCreation imageParser = new ImageCreation();
            Image image = imageParser.imageFromString(row.getValue());
            currentLevel.setBackground(new creationfromfile.Background(image));
        } else {
            throw new RuntimeException("Unknown background structure property: " + row.toString());
        }

    }

    /**
     * Setting the ball velocities.
     *
     * @param currentLevel a given level information.
     * @param row          a given row.
     */
    private static void setBallVelocities(DefaultLevelInformation currentLevel, Row row) {
        String[] velocitiesStrings = row.getValue().split(" ");
        for (String velocity : velocitiesStrings) {
            String[] angleAndSpeed = velocity.split(",");
            currentLevel.addVelocity(
                    Velocity.fromAngleAndSpeed(Double.parseDouble(angleAndSpeed[0]),
                            Double.parseDouble(angleAndSpeed[1])));
        }
    }

    /**
     * Update our properties of the game.
     *
     * @param map a given map with strings and boolean.
     * @param row a given row.
     */
    private static void updateProperty(Map<String, Boolean> map, Row row) {
        if (map.containsKey(row.getName()) && !map.get(row.getName())) {
            map.put(row.getName(), true);
        } else {
            throw new Error("Failed to parse file with property: " + row.toString());
        }
    }


    /**
     * @return new map with the key-value we added according the the initialize pattern.
     */
    private static Map<String, Boolean> initHasBeenFoundMap() {
        Map<String, Boolean> hasBeenFound = new HashMap<>();
        hasBeenFound.put("START_LEVEL", false);
        hasBeenFound.put("END_LEVEL", false);
        hasBeenFound.put("block_definitions", false);
        hasBeenFound.put("row_height", false);
        hasBeenFound.put("blocks_start_x", false);
        hasBeenFound.put("blocks_start_y", false);
        hasBeenFound.put("START_BLOCKS", false);
        hasBeenFound.put("END_BLOCKS", false);
        hasBeenFound.put("level_name", false);
        hasBeenFound.put("background", false);
        hasBeenFound.put("ball_velocities", false);
        hasBeenFound.put("paddle_speed", false);
        hasBeenFound.put("paddle_width", false);
        hasBeenFound.put("num_blocks", false);
        return hasBeenFound;
    }
}