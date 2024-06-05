import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class is used to interpret a specified image to an array
 */
public class Image2Array {

    private static int[][] pixelArray;
    private static int[][] transcodedArray;

    public static void processImage(String imgPath) {
        try {
            //BufferedImage img = Imaging.getBufferedImage(Image2Array.class.getResourceAsStream(imgPath)); // not working
            BufferedImage img = ImageIO.read(Image2Array.class.getResourceAsStream(imgPath));
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
            transcodeArray(height, width);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void transcodeArray(int y, int x) {
        int current;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                current = pixelArray[i][j];
                if (current == 0xFF000000) {
                    transcodedArray[i][j] = 1;
                } else if (current == 0xFFFFFFFF) {
                    transcodedArray[i][j] = 0;
                } else {
                    System.out.println("Wrong pixel value: " + current);
                    break;
                }
            }
        }
    }

    public static int[][] getTranscodedArray() {
        return transcodedArray;
    }

}