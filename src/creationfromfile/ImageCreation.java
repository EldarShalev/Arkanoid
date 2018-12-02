package creationfromfile;


import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is for creation of images.
 */
public class ImageCreation {

    /**
     * @param imageString a string which contaions the image.
     * @return new image.
     */
    public Image imageFromString(String imageString) {
        File newFile = new File(imageString);
        InputStream is = null;
        // Checking if the string contains the word image
        if (imageString.contains("image")) {
            // Checking for start of the path and slpit until the end of the path
            if (imageString.contains("(")) {
                imageString = imageString.substring(imageString.indexOf("(") + 1,
                        imageString.lastIndexOf(')'));
            }
            try {
                if (newFile.exists()) {
                    is = new FileInputStream(imageString);

                } else {
                    // trying to get the image
                    is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageString);
                }
                Image image = ImageIO.read(is);
                return image;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;


    }
}