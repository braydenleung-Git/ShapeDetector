import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class testingPlayGround_B {
    static final int width = 400;
    static final int height = 400;
    public static void main(String[] args) throws InterruptedException, IOException {
        JFrame f = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.add(testCaseGUI.setupTestGUI());
        f.setSize(width,height);//note, the sizes of the frame affect the element, such that even if you set the height of the component it will still be ignored, Order of operation matters
        f.setLocation((screenSize.width / 2) - (f.getWidth() / 2), (screenSize.height / 2) - (f.getHeight() / 2));
        f.setVisible(true);
        //SwingUtilities.invokeLater(() -> testCaseGUI.LogPanel.setupLogPanel(width, height));

        //Test json to sorting algorithm
        testCaseHandler.writeToJson();
        System.out.println("Which file would you like to see?");
        for (int i = 0; i < testCaseHandler.getItemList().size(); i++) {
            System.out.println(testCaseHandler.getItemList().get(i));
        }
        int uI = testCaseGUI.readInt("Enter number: ");
        testCaseGUI.flush();
        Image2Array.processImage(testCaseHandler.getItem(uI).toString());
        shapeLogic.shapeL(Image2Array.getTranscodedArray());
    }
}