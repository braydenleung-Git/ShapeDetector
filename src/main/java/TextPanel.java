import javax.swing.*;
import java.awt.*;

public class TextPanel extends JFrame {

    private JTextArea textArea;

    public TextPanel() {
        setTitle("Text Panel Example");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JPanel to hold components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Set panel background color

        // Create a JTextArea for displaying text
        textArea = new JTextArea();
        textArea.setLineWrap(true); // Enable line wrapping
        textArea.setWrapStyleWord(true); // Wrap at word boundaries
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBackground(Color.WHITE); // Set scroll pane background color
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add some initial text to the text area
        textArea.setText("Welcome to Text Panel!\nYou can type your text here.");
        textArea.setBackground(Color.BLACK); // Set text area background color
        textArea.setForeground(Color.GREEN); // Set text color

        // Add the main panel to the frame
        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextPanel::new);
    }
}