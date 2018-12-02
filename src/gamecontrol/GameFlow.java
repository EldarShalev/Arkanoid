package gamecontrol;

import biuoop.DialogManager;
import biuoop.GUI;
import creationfromfile.Background;
import creationfromfile.DefaultLevelInformation;
import creationfromfile.LevelSetReader;

import gameobjects.Constants;
import gameobjects.Counter;
import gameobjects.HighScoresAnimation;
import interfaces.LevelInformation;
import biuoop.KeyboardSensor;
import interfaces.Menu;
import interfaces.Task;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;


import static gameobjects.Constants.HIGH_SCORE_TABLE_FILE_SIZE;


/**
 * This class is for running all the levels.
 */
public class GameFlow {

    // Members
    private biuoop.KeyboardSensor keyboard;
    private AnimationRunner animationRunner;
    private Counter numOfLives;
    private Counter score;
    private HighScoresTable scoresTable;

    /**
     * Consturctor.
     *
     * @param ar a given animation to run on it.
     * @param ks a given keyboard to use.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks) {
        this.keyboard = ks;
        this.animationRunner = ar;
        this.numOfLives = new Counter(Constants.NUM_OF_LIVES);
        this.score = new Counter(0);
        this.scoresTable = loadHighScoreTable();
    }

    /**
     * Instantiates a new Game flow.
     */
    public GameFlow() {
        GUI gui = new GUI("Arkanoid", Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        this.keyboard = gui.getKeyboardSensor();
        this.scoresTable = loadHighScoreTable();
        this.animationRunner = new AnimationRunner(gui);
        this.numOfLives = new Counter(Constants.NUM_OF_LIVES);
        this.score = new Counter(0);

    }

    /**
     * @param levelSetFileName the level set file name.
     */
    public void startGame(String levelSetFileName) {
        while (true) {
            // Create new menu with other function, according to the logic wa want (s to start, q to quit..)
            MenuAnimation<Task> menu = getMenu(levelSetFileName);
            // Run the menu
            this.animationRunner.run(menu);
            // get the status from the menu
            Task taskStatus = menu.getStatus();
            // When the key is pressed and we got the task, run it if it's not null
            if (taskStatus != null) {
                taskStatus.run();
            }

        }
    }

    /**
     * @param levels list of level information.
     */
    public void runLevels(List<LevelInformation> levels) {
        // Init the indicators to their init values
        this.numOfLives.setNumber(Constants.NUM_OF_LIVES);
        this.score.setNumber(0);
        DefaultLevelInformation currentLevel;
        // Starting the levels one by one
        for (LevelInformation levelInfo : levels) {
            currentLevel = (DefaultLevelInformation) levelInfo.clone();
            GameLevel level = new GameLevel(currentLevel, this.keyboard, this.animationRunner, score, numOfLives);
            level.initialize();
            // run the level (an inside while is in every level itself)
            if (currentLevel.numberOfBlocksToRemove() != 0 && numOfLives.getValue() > 0) {
                level.run();
            }
            // If the player out of lives
            if (numOfLives.getValue() == 0) {
                break;
            }
        }
        // When finish to play
        if (scoresTable.getRank(score.getValue()) >= 0) {
            // Getting the name
            DialogManager dialogManager = this.animationRunner.getGui().getDialogManager();
            String name = dialogManager.showQuestionDialog("Name", "What is your name?", "");
            // Add to the table (if it should be added)
            scoresTable.add(new ScoreInfo(name, score.getValue()));
            // Saving the new high score table according to the new score we got
            try {
                scoresTable.save(new File(Constants.HIGH_SCORE_TABLE_FILE_NAME));
            } catch (IOException ex) {
                System.out.println("Failed to save the table");
                ex.printStackTrace(System.err);
            }
        }
        // Showing end screen
        this.animationRunner.run(new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY,
                new EndScreen(numOfLives, score)));

        // Showing high score table
        this.animationRunner.run(new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(scoresTable)));
    }

    /**
     * @return the high score table object if exists, else create new one and return it.
     */
    private HighScoresTable loadHighScoreTable() {
        File file = new File(Constants.HIGH_SCORE_TABLE_FILE_NAME);
        if (file.exists()) {
            return HighScoresTable.loadFromFile(file);
        } else {
            HighScoresTable highScoresTable = new HighScoresTable(HIGH_SCORE_TABLE_FILE_SIZE);
            try {
                highScoresTable.save(file);
            } catch (IOException e) {
                System.out.println("Failed to save the table");
            }
            return highScoresTable;
        }
    }

    /**
     * @param levelSetFileName the level set name.
     * @return new menu animation.
     */
    private MenuAnimation<Task> getMenu(String levelSetFileName) {
        // Start the menu
        MenuAnimation<Task> menu = new MenuAnimation<>("Arkanoid",
                this.keyboard,
                new Background(Color.orange),
                animationRunner);

        Menu<Task> subMenu = LevelSetReader.fromFile(levelSetFileName, "Start the game", keyboard,
                new Background(Color.ORANGE), animationRunner, this);

        menu.addSubMenu("s", "Start the game", subMenu);

        Task<Void> highScoreTask = new Task<Void>() {
            public Void run() {
                animationRunner.run(new KeyPressStoppableAnimation(keyboard,
                        KeyboardSensor.SPACE_KEY,
                        new HighScoresAnimation(scoresTable)));
                return null;
            }
        };

        Task<Void> quit = new Task<Void>() {
            public Void run() {
                System.exit(0);
                return null;
            }
        };

        menu.addSelection("h", "High Scores", highScoreTask);
        menu.addSelection("q", "Quit", quit);
        return menu;
    }


}