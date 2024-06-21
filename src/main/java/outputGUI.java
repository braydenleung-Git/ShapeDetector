import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class outputGUI extends JPanel {
    public static int circles = 0;
    public static int rectangles = 0;
    public static int squares = 0;
    public static int triangles = 0;
    public outputGUI() {
        String filePath = startMenuGUI.getPath();
        setBackground(Color.BLACK);
        setLayout(null);

        // Display the path of the dropped file
        JLabel label = new JLabel("Dropped file: " + filePath);
        label.setBounds(20, 20, 360, 20);  // Position the label
        label.setFont(main.HelvetciaNeue_Cond_B_05.deriveFont(14f));
        label.setForeground(Color.WHITE);
        add(label);


        // Add test statements
        JLabel circle = new JLabel("The amount of circles are: " + circles);
        circle.setBounds(20, 170, 200, 40);
        circle.setFont(main.HelvetciaNeue_Cond_B_05.deriveFont(14f));
        circle.setForeground(Color.WHITE);


        JLabel rect = new JLabel("The amount of rectangles are: " + circles);
        rect.setBounds(20, 190, 200, 40);
        rect.setFont(main.HelvetciaNeue_Cond_B_05.deriveFont(14f));
        rect.setForeground(Color.WHITE);


        JLabel square = new JLabel("The amount of squares are: " + circles);
        square.setBounds(20, 210, 200, 40);
        square.setFont(main.HelvetciaNeue_Cond_B_05.deriveFont(14f));
        square.setForeground(Color.WHITE);


        JLabel tri = new JLabel("The amount of triangles are: " + circles);
        tri.setBounds(20, 230, 200, 40);
        tri.setFont(main.HelvetciaNeue_Cond_B_05.deriveFont(14f));
        tri.setForeground(Color.WHITE);


        JButton repeat = new JButton("Return to Start");
        repeat.addActionListener(e -> {
            main.changeLayout(400, 400, "Start Menu");
        });
        repeat.setBounds(20, 280, 120, 30);

        // Optionally load and display the image if it's an image file
        if (filePath != null && (filePath.endsWith(".png") )) {
            try {
                Image image = ImageIO.read(new File(filePath));
                Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);  // Scale the image
                ImageIcon icon = new ImageIcon(scaledImage);
                JLabel imageLabel = new JLabel(icon);
                imageLabel.setBounds(20, 50, 100, 100);  // Position the image label
                add(imageLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Adding labels and button
        add(circle);
        add(rect);
        add(square);
        add(tri);
        add(repeat);

    }

    public static void reset(){
        circles = 0;
        rectangles = 0;
        triangles = 0;
        squares = 0;
    }
}