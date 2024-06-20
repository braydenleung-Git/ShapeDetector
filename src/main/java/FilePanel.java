import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FilePanel extends JFrame {

    public static String path;
    InputStream imageStream = null;

    public FilePanel() {
        // Basic settings
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color of content pane
        getContentPane().setBackground(Color.BLACK);

        // Setting layout to null for absolute positioning
        setLayout(null);

        // Making buttons


        JLabel circle = new JLabel("The amount of circles are: ");
        circle.setBounds(20, 170, 200, 40);
        circle.setFont(new Font("Calibri", Font.BOLD, 14));
        circle.setForeground(Color.WHITE);

        JLabel rect = new JLabel("The amount of rectangles are: ");
        rect.setBounds(20, 190, 200, 40);
        rect.setFont(new Font("Calibri", Font.BOLD, 14));
        rect.setForeground(Color.WHITE);

        JLabel square = new JLabel("The amount of squares are: ");
        square.setBounds(20, 210, 200, 40);
        square.setFont(new Font("Calibri", Font.BOLD, 14));
        square.setForeground(Color.WHITE);

        JLabel tri = new JLabel("The amount of triangles are: ");
        tri.setBounds(20, 230, 200, 40);
        tri.setFont(new Font("Calibri", Font.BOLD, 14));
        tri.setForeground(Color.WHITE);

        JButton repeat = new JButton("Return to Start");
        repeat.setBounds(20, 280, 120, 30);



        String path = StartMenu.getPath();


        //adding images
        if(!(path == null)){
            imageStream = getClass().getResourceAsStream(StartMenu.getPath());
        }
        else{
            imageStream = getClass().getResourceAsStream("/Shape.png");
        }





        if (imageStream != null) {
            try {
                Image image = ImageIO.read(imageStream);
                ImageIcon icon = new ImageIcon(image.getScaledInstance(400, 100, Image.SCALE_SMOOTH));
                JLabel gameIconTitleScreen = new JLabel(icon);
                gameIconTitleScreen.setBounds(20, -80, 300, 300);
                add(gameIconTitleScreen); // Add image to frame
            } catch (IOException e) {
                e.printStackTrace();
                }
        }
        else {
            System.err.println("Image not found.");
        }


        // Placing components
        //add(dnT);

        add(circle);
        add(rect);
        add(square);
        add(tri);
        add(repeat);

        // Ensure all components are visible
        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FilePanel());
    }


}


