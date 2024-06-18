import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class i2atest {
    static final int width = 500;
    static final int height = 700;
        public static void main(String[] args) throws InterruptedException, IOException {
            JFrame f = new JFrame();
            JFrame log = new JFrame();
            log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            log.add(testCaseGUI.setLogPanel());
            log.setSize(width,height);
            log.setLocation(0,0);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setResizable(false);
            f.add(testCaseGUI.setupTestGUI());
            f.setSize(width,height);//note, the sizes of the frame affect the element, such that even if you set the height of the component it will still be ignored, Order of operation matters
            f.setLocation((screenSize.width / 2) - (f.getWidth() / 2), (screenSize.height / 2) - (f.getHeight() / 2));
            f.setVisible(true);
            log.setVisible(true);
            System.out.println(testCaseGUI.readLine("What would you like to test?"));
        }
}
/*
            //Test json to sorting algorithm
            testCases.writeToJson();
            System.out.println("Which file would you like to see?");
            for (int i = 0; i < testCases.getItemList().size(); i++) {
                System.out.println(testCases.getItemList().get(i));
            }
            int input = Integer.parseInt(new Scanner(System.in).nextLine());
            Image2Array.processImage(testCases.getItem(input).toString());
            shapelogic.shapel(Image2Array.getTranscodedArray());*/