package creationfromfile;

import interfaces.BlockCreator;

import java.awt.Color;
import java.awt.Image;

/**
 * This class is for custom block factory which we can later on add any custom properties for our block.
 */
public class CustomBlocksFactory {
    // Members
    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";
    private static final String HIT_POINTS = "hit_points";
    private static final String FILL = "fill";
    private static final String STROKE = "stroke";

    /**
     * @param currentCreator a current block creator we have.
     * @param property       a given property to add.
     * @return a new block created decorated with the property we want.
     */
    public BlockCreator addProperty(BlockCreator currentCreator, Row property) {
        // Return new height block
        if (HEIGHT.equals(property.getName())) {
            return new HeightBlockCreator(currentCreator, property.getValue());
        } else if (WIDTH.equals(property.getName())) {
            // Return new width block
            return new WidthBlockCreator(currentCreator, property.getValue());
        } else if (property.getName().startsWith(FILL) || property.getName().startsWith(STROKE)) {
            // Return new block with stroke or fill
            Integer hitPointsValueToAdd = -1;
            Boolean isFill = property.getName().startsWith("fill");
            int dividerIndex = property.getName().indexOf("-");
            // Get - separator index
            if (dividerIndex != -1) {
                hitPointsValueToAdd = Integer.parseInt(property.getName().substring(dividerIndex + 1));
            }
            // Handle fill or stroke properties
            if (property.getValue().startsWith("image(")) {
                // Return new image block
                // Handle images parsing
                ImageCreation parsedImage = new ImageCreation();
                Image image = parsedImage.imageFromString(property.getValue());
                return new DrawingBlockCreator(currentCreator, hitPointsValueToAdd, image);
            } else if (property.getValue().startsWith("color(")) {
                // Return new Color block
                ColorsParser colorsParser = new ColorsParser();
                Color color = colorsParser.colorFromString(property.getValue());
                return new DrawingBlockCreator(currentCreator, hitPointsValueToAdd, isFill, color);
            }
        } else if (HIT_POINTS.equals(property.getName())) {
            // Return new hitPoint block
            return new HPBlockCreator(currentCreator, property.getValue());
        }

        // Unable to parse value
        throw new RuntimeException(String.format("Unable to parse file at property: %s", property.toString()));
    }
}