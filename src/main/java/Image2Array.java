
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.commons.imaging.Imaging;

/**
 * This class is used to interpret a specified image to an array
 */
public class Image2Array {

    private static int[][] pixelArray;
    private static int[][] transcodedArray;
    public static void processImage(String imgPath){
        try {
            BufferedImage img = Imaging.getBufferedImageFromPng(imgPath);
            int width = img.getWidth();
            int height = img.getHeight();
            pixelArray = new int[height][width];
            transcodedArray = new int[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get RGB value of the pixel
                    int rgb = img.getRGB(x, y);
                    pixelArray[y][x] = rgb;
                }
            }
            transcodeArray(height,width);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void transcodeArray(int x, int y){
        int current = 0;
        for (int i = 0; i <x; i++) {
            for (int j = 0; j < y; j++) {
                current = pixelArray[i][j];
                if(current == 0xFF000000){
                    transcodedArray[i][j] = 1;
                } else if (current == 0xFFFFFFFF) {
                    transcodedArray[i][j] = 0;
                }
                else 
            }
        }
    }
}


/*
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageToArray {
    public static void main(String[] args) {
        try {
            // Load the image
            BufferedImage image = ImageIO.read(new File("path_to_your_image.png"));

            // Get image dimensions
            int width = image.getWidth();
            int height = image.getHeight();

            // Initialize the array
            int[][] pixelArray = new int[height][width];

            // Extract pixel data
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get RGB value of the pixel
                    int rgb = image.getRGB(x, y);
                    pixelArray[y][x] = rgb;
                }
            }

            // Print pixel array as text representation
            printPixelArray(pixelArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printPixelArray(int[][] pixelArray) {
        for (int y = 0; y < pixelArray.length; y++) {
            for (int x = 0; x < pixelArray[y].length; x++) {
                // Convert the RGB value to a character representation (simplified)
                char c = (char)((pixelArray[y][x] & 0xFF) % 128);
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }
}

 */