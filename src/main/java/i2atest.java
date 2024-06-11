import java.io.IOException;
import java.util.Scanner;

public class i2atest {
    public static void main(String[] args) throws IOException {
        testCases.writeToJson();
        System.out.println("Which file would you like to see?");
        for (int i = 0; i < testCases.getItemList().size(); i++) {
            System.out.println(testCases.getItemList().get(i));
        }
        int input = Integer.parseInt(new Scanner(System.in).nextLine());
        Image2Array.processImage(testCases.getItem(input).toString());
        shapelogic.shapel(Image2Array.getTranscodedArray());
    }
}
