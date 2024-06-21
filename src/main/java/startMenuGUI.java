
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class startMenuGUI extends JPanel {

    static String path;
    ImageIcon background;

    public startMenuGUI() {
        // Basic settings
        //setBackground(Color.WHITE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(0, 80, 5, 80); // Set margin
        backG("/J4o.gif");





        // Making buttons
        JButton dnD = new JButton(/*);*/"<html><center>Drag and Drop Your Files Here <br> Or Click to Browse Your File</center></html>");
        dnD.setPreferredSize(new Dimension(40, 60));

        JButton testCase = testCaseGUI.setupButton();
        testCase.setPreferredSize(new Dimension(40, 60));

        JButton colorPicker = new JButton("Pick Color");
        colorPicker.setPreferredSize(new Dimension( 40, 60));

        dnD.setFont(new Font("Serif", Font.BOLD, 14));
        testCase.setFont(new Font("Serif", Font.BOLD, 30));
        colorPicker.setFont(new Font("Serif", Font.BOLD, 30));

        // Add drop target to button
        new DropTarget(dnD, new FileDropTargetListener());

        //adding images
        InputStream imageStream = getClass().getResourceAsStream("/Shape.png"); // Load image from resources

        if (imageStream != null) {
            try {
                Image image = ImageIO.read(imageStream);
                ImageIcon icon = new ImageIcon(image.getScaledInstance(450, 125, Image.SCALE_SMOOTH));
                JLabel gameIconTitleScreen = new JLabel(icon);
                gameIconTitleScreen.setPreferredSize(new Dimension(400, 100));
                add(gameIconTitleScreen);// Add image to frame
                constraints.gridy = 1;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Image not found.");
        }

        InputStream imageButton = getClass().getResourceAsStream("/image.png");
        if (imageButton != null) {
            try {
                Image image = ImageIO.read(imageButton);
                ImageIcon icon = new ImageIcon(image.getScaledInstance(400, 100, Image.SCALE_SMOOTH));
                JLabel gameIconTitleScreen = new JLabel(icon);
                //dnD.setIcon(icon);
                //gameIconTitleScreen.setBounds(50, -20, 300, 300);
                //add(gameIconTitleScreen); // Add image to frame
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Image not found.");
        }

        add(dnD, constraints);
        constraints.gridy = 2;
        add(testCase, constraints);
        constraints.gridy = 3;
        add(colorPicker, constraints);
        constraints.gridy = 4;


        // Add action listener to color picker button
        colorPicker.addActionListener(e -> pickColor());
        dnD.addActionListener(e->browse());
    }

    public void backG(String gifPath) {
        // Load the GIF file
        background = new ImageIcon(getClass().getResource(gifPath));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the GIF as the background
        if (background != null) {
            g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static String getPath(){
        return path;
    }

    // Method to handle color picking
    private void pickColor() {
        Color selectedColor = JColorChooser.showDialog(this, "Choose Background Color", Color.WHITE);
        if (selectedColor != null) {
            setBackground(selectedColor);
            // Repaint the frame to reflect the new background color
            repaint();
        }
    }

    // Method to handle file browser
    private void browse(){
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            boolean png = true;
            File selectedFile = null;
                if (!selectedFile.getName().toLowerCase().endsWith(".png")) {
                    selectedFile = fileChooser.getSelectedFile();
                    path = selectedFile.getAbsolutePath();
                }


        }
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

                    boolean png = true;
                    for (File file : droppedFiles) {
                        // Check if the file extension is .png
                        if (!file.getName().toLowerCase().endsWith(".png")) {
                            png = false;
                            break;
                        }
                    }

                    if(png) {
                        for (File file : droppedFiles) {
                            System.err.println("Dropped file: " + file.getAbsolutePath());
                            path = file.getAbsolutePath();
                        }
                        dtde.dropComplete(true);
                        main.changeLayout(400, 400, "Output Panel");
                    }
                } else {
                    dtde.rejectDrop();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                dtde.dropComplete(false);
            }
        }
    }
}
