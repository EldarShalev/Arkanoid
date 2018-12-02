package game;


import gamecontrol.GameFlow;

import java.io.File;

/**
 * game.Ass6Game class to start the game.
 */
public class Ass6Game {
    /**
     * Start the game.
     *
     * @param args a given levels to run according to the insertion.
     */
    public static void main(String[] args) {
        // Checking if the arguments are valid
        if (args.length == 0) {
            GameFlow gameFlow = new GameFlow();
            try {
                gameFlow.startGame("level_sets.txt");
            } catch (Exception ex) {
                System.out.println(ex.toString());
                System.exit(1);
            }
        } else if (args.length == 1) {
            if (new File(args[0]).exists()) {
                GameFlow gameFlow = new GameFlow();
                try {
                    gameFlow.startGame(args[0]);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                    System.exit(1);
                }
            } else {
                throw new IllegalArgumentException("File was not found: " + args[0]);
            }
        } else {
            throw new IllegalArgumentException("Wrong number of program arguments");
        }

    }

}

