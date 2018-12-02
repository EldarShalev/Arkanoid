package creationfromfile;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This calss controls the color parsing.
 */
public class ColorsParser {
    /**
     * @param s a given string.
     * @return a color according to the string.
     */
    public Color colorFromString(String s) {
        // Checking if we got the rgb creation color
        if ((s.contains("rgb")) || (s.contains("RGB"))) {
            Pattern pattern = Pattern.compile("\\d{1,3}");
            Matcher matcher = pattern.matcher(s);
            if (!matcher.find()) {
                return null;
            }
            // Parsing the r-g-b colors if we got a number for each letter
            int r = Integer.parseInt(s.substring(matcher.start(), matcher.end()));

            if (!matcher.find()) {
                return null;
            }
            int g = Integer.parseInt(s.substring(matcher.start(), matcher.end()));

            if (!matcher.find()) {
                return null;
            }
            int b = Integer.parseInt(s.substring(matcher.start(), matcher.end()));

            return new Color(r, g, b);
            // Checking if we got a color name
        } else if (s.contains("cyan")) {
            return Color.CYAN;
        } else if (s.contains("pink")) {
            return Color.PINK;
        } else if (s.contains("red")) {
            return Color.RED;
        } else if (s.contains("white")) {
            return Color.WHITE;
        } else if (s.contains("black")) {
            return Color.BLACK;
        } else if (s.contains("gray")) {
            return Color.GRAY;
        } else if (s.contains("lightGray")) {
            return Color.LIGHT_GRAY;
        } else if (s.contains("green")) {
            return Color.GREEN;
        } else if (s.contains("orange")) {
            return Color.ORANGE;
        } else if (s.contains("blue")) {
            return Color.BLUE;
        } else if (s.contains("yellow")) {
            return Color.YELLOW;
        }
        return null;
    }
}
