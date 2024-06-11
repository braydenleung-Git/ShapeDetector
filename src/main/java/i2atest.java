import java.io.IOException;
import java.util.Scanner;

public class i2atest {
    public static void main(String[] args) throws IOException {
        /*.writeToJson();
        System.out.println("Which file would you like to see?");
        for (int i = 0; i < testCases.getItemList().size(); i++) {
            System.out.println(testCases.getItemList().get(i));
        }
        int input = Integer.parseInt(new Scanner(System.in).nextLine());
        System.out.println(testCases.getItem(input));*/
        Image2Array.processImage("./TestCases/Square.png");

        shapelogic.shapel(Image2Array.getTranscodedArray());
    }
}
/*
Image2Array.processImage("./Rectangle.png");
transcoded array
find pixel
return shape
test case
 */