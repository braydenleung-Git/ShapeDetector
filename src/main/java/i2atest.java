import java.util.Arrays;

public class i2atest {
    public static void main(String[] args) {
        Image2Array.processImage("./Rectangle.png");
        int[][] pixels = Image2Array.getTranscodedArray();

        int[] ar = shapelogic.findpixel(pixels);
        System.out.println(Arrays.toString(ar));
        int[][] shape = shapelogic.returnShape(pixels, ar);
        System.out.println(shape.length);//y
        System.out.println(shape[0].length);//x

        System.out.println(shapelogic.rectangleorsquare(shape));
        /*
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                System.out.print(pixels[i][j] + " ");
            }
            System.out.println();
        }*/
    }
}
