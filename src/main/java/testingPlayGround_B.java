import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class testingPlayGround_B {
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
            //Test json to sorting algorithm
            testCaseHandler.writeToJson();
            System.out.println("Which file would you like to see?");
            for (int i = 0; i < testCaseHandler.getItemList().size(); i++) {
                System.out.println(testCaseHandler.getItemList().get(i));
            }
            String uI = testCaseGUI.readLine("Enter number: ");
            Image2Array.processImage(testCaseHandler.getItem(Integer.parseInt(uI)).toString());
            shapelogic.shapel(Image2Array.getTranscodedArray());
        }

}