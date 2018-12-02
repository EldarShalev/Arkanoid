package gamecontrol;


import java.io.IOException;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class for high score table.
 */
public class HighScoresTable implements Serializable {

    // Members
    private ArrayList<ScoreInfo> scores;
    private int scoreSize;

    /**
     * Create an empty high-scores table with the specified scoreSize.
     *
     * @param size table holds up to scoreSize top scores.
     */
    public HighScoresTable(int size) {
        this.scoreSize = size;
        this.scores = new ArrayList<>();
    }


    /**
     * return the rank of the current score: where will it be on the list if added?
     * Rank 1 means the score will be highest on the list. Rank `scoreSize` means the score will be lowest.
     * Rank > `scoreSize` means the score is too low and will not be added to the list.
     *
     * @param score the given score to check if need to be inserted to the list.
     * @return the rank of the player.
     */
    public int getRank(int score) {
        for (int i = 0; i < this.scores.size(); i++) {
            if (this.scores.get(i).getScore() < score) {
                return i;
            }
        }
        if (this.scores.size() < this.size()) {
            return this.scores.size();
        }
        return -1;
    }

    /**
     * Add a high-score.
     *
     * @param score a given score
     */
    public void add(ScoreInfo score) {
        // We want to check the rank of the player in order to determine if we add the score or not
        int r = this.getRank(score.getScore());
        // Means we have a space for the new score in our table
        if (r != -1) {
            int index = 0;
            ScoreInfo temp = score;
            List<ScoreInfo> copy = new ArrayList<>(this.scores);
            for (ScoreInfo info : copy) {
                if (r == index) {
                    this.scores.set(index, score);
                    temp = info;
                } else if (r < index) {
                    this.scores.set(index, temp);
                    temp = info;
                }
                index++;
            }
            // to know where to add the score
            if (this.scoreSize > index) {
                this.scores.add(index, temp);
            }
        }
    }

    /**
     * @return Return table scoreSize.
     */
    public int size() {
        return this.scoreSize;
    }

    /**
     * @return the current high scores. The list is sorted such that the highest scores come first.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scores.removeAll(this.scores);
    }

    /**
     * Load table data from file, Current table data is cleared.
     *
     * @param filename a given File to load.
     * @throws IOException in case can't load.
     */
    public void load(File filename) throws IOException {
        ObjectInputStream in = null;
        // Deserialization
        try {
            // Reading the object from a file
            in = new ObjectInputStream(new FileInputStream(filename));

            // Method for deserialization of object
            HighScoresTable highScoresTable = (HighScoresTable) in.readObject();
            this.scores = highScoresTable.scores;
            this.scoreSize = highScoresTable.scoreSize;
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught on loading object from file");
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }


    /**
     * Save table data to the specified file.
     *
     * @param filename a given filename To save into.
     * @throws IOException in case we couldn't save.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream out = null;
        // Serialization
        try {
            //Saving of object in a file
            out = new ObjectOutputStream(new FileOutputStream(filename));

            // Method for serialization of object
            out.writeObject(this);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with reading it, an empty table is returned.
     *
     * @param filename a given file name to load from it.
     * @return the table or empty file if we can't read it.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable highScoresTable = new HighScoresTable(5);
        try {
            highScoresTable.load(filename);
        } catch (IOException ex) {
            System.err.println("Failed loading object");
            ex.printStackTrace(System.err);
            return null;
        }

        return highScoresTable;

    }
}
