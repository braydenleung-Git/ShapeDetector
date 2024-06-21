import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/*
TODO:
    - multi-shape recognition
    - UI customization/theme
 */
public class main {
    static JFrame f =  new JFrame();
    public static JPanel mainPanel = new JPanel();
    public static CardLayout cl = new CardLayout();
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static Font HelvetciaNeue_Cond_B_05 = null;
    private static Font PTMono_Regular_02 = null;
    public static void main(String[] args) {
        setupFonts();
        setupFrame();
        f.setVisible(true);
    }

    static void setupFrame(){
        mainPanel.setLayout(cl);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel startMenu = new startMenuGUI();
        mainPanel.add(startMenu,"Start Menu");
        JPanel testCaseConsole = testCaseGUI.setupTestGUI();
        mainPanel.add(testCaseConsole, "Test Case Console");
        f.add(mainPanel);
        cl.show(mainPanel,"Start Menu");
        f.setSize(400,400);
        f.setLocation((screenSize.width / 2) - (f.getWidth() / 2), (screenSize.height / 2) - (f.getHeight() / 2));
        f.setResizable(false);
    }

    public static void changeLayout(int width, int height, String layout) {
        if(layout.equals("Output Panel")){
            mainPanel.add(new outputGUI(),"Output Panel");
            mainPanel.repaint();
        }
        else if(layout.equals("Start Menu")){
            mainPanel.remove(2);
            outputGUI.reset();
        }
        cl.show(mainPanel, layout);
        f.setSize(width, height);
        f.setLocation((screenSize.width / 2) - (f.getWidth() / 2), (screenSize.height / 2) - (f.getHeight() / 2));

    }

    private static void setupFonts(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            //Load the font file
            //HelveticaNeue-CondensedBold-05
            InputStream HelvetciaNeue_Cond_B_05_File = main.class.getResourceAsStream("/Fonts/HelveticaNeue/HelveticaNeue-CondensedBold-05.ttf");
            HelvetciaNeue_Cond_B_05 = Font.createFont(Font.TRUETYPE_FONT, HelvetciaNeue_Cond_B_05_File);
            ge.registerFont(HelvetciaNeue_Cond_B_05);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        try {
            //PTMono-Regular-02
            InputStream PTMono_Regular_02_File = main.class.getResourceAsStream("/Fonts/PTMono/PTMono-Regular-02.ttf");
            PTMono_Regular_02 = Font.createFont(Font.TRUETYPE_FONT, PTMono_Regular_02_File);
            ge.registerFont(PTMono_Regular_02);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
