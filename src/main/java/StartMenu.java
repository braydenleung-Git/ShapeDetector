import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.util.List;

public class StartMenu extends JFrame {

    private JPanel imagePanel;

    public StartMenu() {
        // Basic settings
        setTitle("Shape Identifier");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color of content pane
        getContentPane().setBackground(Color.WHITE); // Default background color

        JLabel title = new JLabel("Shape Identifier");
        title.setFont(new Font("Serif", Font.BOLD, 30));

        // Setting layout to null for absolute positioning
        setLayout(null);

        // Making buttons
        JButton dnD = new JButton("Drag and Drop Your Files Here");
        dnD.setBounds(100, 200, 200, 40);
        JButton testCase = new JButton("Test Case");
        testCase.setBounds(100, 255, 200, 40);
        JButton colorPicker = new JButton("Pick Color");
        colorPicker.setBounds(100, 310, 200, 40);
        dnD.setFont(new Font("Serif", Font.BOLD, 12));
        testCase.setFont(new Font("Serif", Font.BOLD, 12));
        colorPicker.setFont(new Font("Serif", Font.BOLD, 12));
        title.setBounds(100, 80, 500, 100);

        Color color = new Color(0, 0, 0);
        title.setForeground(color);

        // Add drop target to button
        new DropTarget(dnD, new FileDropTargetListener());

        // Image panel initialization
        imagePanel = new ImagePanel("./image.png");
        imagePanel.setBounds(100, 150, 500, 300);

        // Placing components
        add(dnD);
        add(testCase);
        add(colorPicker);
        add(title);
        add(imagePanel);

        // Add action listener to color picker button
        colorPicker.addActionListener(e -> pickColor());

        // Ensure all components are visible
        setVisible(true);
    }

    // Method to handle color picking
    private void pickColor() {
        Color selectedColor = JColorChooser.showDialog(this, "Choose Background Color", Color.WHITE);
        if (selectedColor != null) {
            getContentPane().setBackground(selectedColor);
            // Repaint the frame to reflect the new background color
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartMenu());
    }

    // Inner class to handle file drop
    private static class FileDropTargetListener implements DropTargetListener {
        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
            // Provide visual feedback when dragging over the target
            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dtde.acceptDrag(DnDConstants.ACTION_COPY);
            } else {
                dtde.rejectDrag();
            }
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
            // You can provide additional feedback here if needed
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {
            // Handle change in drop action if needed
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
            // Handle the event when the drag exits the target
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            try {
                if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable transferable = dtde.getTransferable();
                    List<File> droppedFiles = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                    for (File file : droppedFiles) {
                        System.out.println("Dropped file: " + file.getAbsolutePath());
                        // You can add code here to handle the dropped files as needed
                    }
                    dtde.dropComplete(true);
                } else {
                    dtde.rejectDrop();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                dtde.dropComplete(false);
            }
        }
    }

    // Inner class for image panel
    private class ImagePanel extends JPanel {
        private Image image;

        public ImagePanel(String imagePath) {
            this.image = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                // Calculate the new size while maintaining the aspect ratio
                int originalWidth = image.getWidth(this);
                int originalHeight = image.getHeight(this);
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                double aspectRatio = (double) originalWidth / originalHeight;
                int newWidth, newHeight;

                if (panelWidth / aspectRatio <= panelHeight) {
                    newWidth = panelWidth;
                    newHeight = (int) (panelWidth / aspectRatio);
                } else {
                    newWidth = (int) (panelHeight * aspectRatio);
                    newHeight = panelHeight;
                }

                // Center the image
                int x = (panelWidth - newWidth) / 2;
                int y = (panelHeight - newHeight) / 2;

                // Draw the image with the new size
                g.drawImage(image, x, y, newWidth, newHeight, this);
            } else {
                System.out.println("Image is null and cannot be drawn.");
            }
        }
    }
}