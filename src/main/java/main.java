import javax.swing.*;
/*
TODO:
    - CardLayout
    - Third Panel
    - multi-shape recognition
    - handle invalid file
    - handle browsed/dropped files
    - UI customization/theme

 */
public class main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        JPanel startMenu = new startMenuGUI();
        JPanel testCaseConsole = testCaseGUI.setupTestGUI();

        // Ensure all components are visible
        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width / 2) - (this.getWidth() / 2), (screenSize.height / 2) - (this.getHeight() / 2));
        setVisible(true);*/

    }
    void setupFrame(){

    }
}
