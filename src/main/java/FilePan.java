import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FilePan extends JFrame {
    public FilePan(String filePath) {
        setTitle("File Panel");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);  // Use absolute layout for positioning components

        // Display the path of the dropped file
        JLabel label = new JLabel("Dropped file: " + filePath);
        label.setBounds(20, 20, 360, 20);  // Position the label
        label.setForeground(Color.WHITE);
        add(label);

        // Add test statements
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

        // Adding labels and button
        add(circle);
        add(rect);
        add(square);
        add(tri);
        add(repeat);

        // Optionally load and display the image if it's an image file
        if (filePath != null && (filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath.endsWith(".jpeg"))) {
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
    }

    public static void main(String[] args) {
        // Example usage
        SwingUtilities.invokeLater(() -> new FilePan("path/to/your/image.png").setVisible(true));
    }
}