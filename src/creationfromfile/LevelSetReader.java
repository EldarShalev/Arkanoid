package creationfromfile;


import biuoop.KeyboardSensor;
import gamecontrol.AnimationRunner;
import gamecontrol.GameFlow;
import gamecontrol.MenuAnimation;
import interfaces.LevelInformation;
import interfaces.Menu;
import interfaces.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.LineNumberReader;
import java.util.List;

/**
 * This is for setting the levels before we start.
 */
public class LevelSetReader {

    /**
     * @param filename       a given file name.
     * @param title          the given title.
     * @param keyboardSensor the keyboard sensor.
     * @param background     the background.
     * @param runner         the animation runner.
     * @param gameFlow       the game flow.
     * @return new Menu From file.
     */
    public static Menu<Task> fromFile(String filename, String title, KeyboardSensor keyboardSensor,
                                      Background background, AnimationRunner runner, GameFlow gameFlow) {
        Reader fileReader = null;
        File newFile = new File(filename);
        try {
            if (newFile.exists()) {
                fileReader = new FileReader(filename);
            } else {
                fileReader = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(filename));
                //Trying to read from the file
            }
            return fromReader(fileReader, title, keyboardSensor, background, runner, gameFlow);
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
     * @param reader         a given reader to read from.
     * @param title          the title we want to display.
     * @param keyboardSensor the keyboard sensor.
     * @param background     the background of the menu.
     * @param runner         the animation runner which will run the menu.
     * @param gameFlow       the game flow with all the information we need.
     * @return new menu to display with keys waiting for order.
     */
    public static Menu<Task> fromReader(java.io.Reader reader,
                                        String title,
                                        KeyboardSensor keyboardSensor,
                                        Background background,
                                        AnimationRunner runner,
                                        GameFlow gameFlow) {
        Menu<Task> subMenu = new MenuAnimation<>(title, keyboardSensor, background, runner);

        // The file stream wrapper
        LineNumberReader lineReader;

        // Current iteration values
        Row currentLine;
        String line;
        String currentKey = null;
        String currentName = null;

        // Trying to read from file.
        try {
            lineReader = new LineNumberReader(reader);
            line = lineReader.readLine();
            // As long as not null
            while (line != null) {
                // Skipping the empty lines.
                while (line.equals("")) {
                    line = lineReader.readLine();
                }

                currentLine = new Row(line);
                // Trying to get the name of the level set file
                if (!currentLine.isHeader() && lineReader.getLineNumber() % 2 != 0) {
                    currentKey = currentLine.getName();
                    currentName = currentLine.getValue();
                    line = lineReader.readLine();
                } else if (currentLine.isHeader() && lineReader.getLineNumber() % 2 == 0) {
                    final String filename = currentLine.getName();
                    final List<LevelInformation> levels = LevelSpecificationReader.fromFile(filename);
                    // Adding to the sub menu the levels we found
                    subMenu.addSelection(currentKey, currentName, new Task() {
                        @Override
                        public Void run() {
                            gameFlow.runLevels(levels);
                            return null;
                        }
                    });
                    line = lineReader.readLine();
                } else {
                    throw new Exception("Wrong level set file format");
                }
            }
        } catch (IOException ex) {
            System.out.println("IO Exception thrown: " + ex.toString());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subMenu;
    }
}
