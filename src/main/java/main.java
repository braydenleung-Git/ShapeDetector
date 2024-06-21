import javax.swing.*;
import java.awt.*;

/*
TODO:
    - multi-shape recognition
    - handle invalid file
    - handle browsed/dropped files
    - UI customization/theme
    - setting focus
 */
public class main {
    static JFrame f =  new JFrame();
    public static JPanel mainPanel = new JPanel();
    public static CardLayout cl = new CardLayout();
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static JPanel outputPanel = new outputGUI();
    public static void main(String[] args) {
        setupFrame();
        f.setVisible(true);
    }

    static void setupFrame(){
        mainPanel.setLayout(cl);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel startMenu = new startMenuGUI();
        mainPanel.add(startMenu,"Start Menu");

        mainPanel.add(outputPanel,"Output Panel");
        JPanel testCaseConsole = testCaseGUI.setupTestGUI();
        mainPanel.add(testCaseConsole, "Test Case Console");
        f.add(mainPanel);
        cl.show(mainPanel,"Start Menu");
        f.setSize(400,400);
        f.setLocation((screenSize.width / 2) - (f.getWidth() / 2), (screenSize.height / 2) - (f.getHeight() / 2));
        f.setResizable(false);
    }

    public static void changeLayout(int width, int height, String layout) {
        cl.show(mainPanel, layout);
        f.setSize(width, height);
        f.setLocation((screenSize.width / 2) - (f.getWidth() / 2), (screenSize.height / 2) - (f.getHeight() / 2));
        if(layout.equals("Output Panel")){
            outputPanel.repaint();
        }
    }
}
