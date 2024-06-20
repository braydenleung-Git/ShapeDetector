
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StartMenu extends JFrame {

    public static String path;

    public StartMenu() {
        // Basic settings
        setTitle("Shape Identifier");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color of content pane
        getContentPane().setBackground(Color.WHITE);

        //JLabel title = new JLabel("Shape Identifier");
        //title.setFont(new Font("Serif", Font.BOLD, 30));

        // Setting layout to null for absolute positioning
        setLayout(null);

        // Making buttons
        JButton dnD = new JButton();//"Drag and Drop Your Files Here");
        dnD.setBounds(100, 200, 200, 40);

        JButton testCase = new JButton("Test Case");
        testCase.setBounds(100, 255, 200, 40);

        JButton colorPicker = new JButton("Pick Color");
        colorPicker.setBounds(100, 310, 200, 40);

        //JLabel dnT = new JLabel("Drag and drop your files here");
        //dnT.setBounds(100, 200, 200, 40);

        dnD.setFont(new Font("Serif", Font.BOLD, 12));
        testCase.setFont(new Font("Serif", Font.BOLD, 12));
        colorPicker.setFont(new Font("Serif", Font.BOLD, 12));
        //title.setBounds(100, 80, 500, 100);

        Color color = new Color(0, 0, 0);
        //title.setForeground(color);

        // Add drop target to button
        new DropTarget(dnD, new FileDropTargetListener());


        //adding images
        InputStream imageStream = getClass().getResourceAsStream("/Shape.png"); // Load image from resources



        if (imageStream != null) {
            try {
                Image image = ImageIO.read(imageStream);
                ImageIcon icon = new ImageIcon(image.getScaledInstance(400, 100, Image.SCALE_SMOOTH));
                JLabel gameIconTitleScreen = new JLabel(icon);
                gameIconTitleScreen.setBounds(50, -20, 300, 300);
                add(gameIconTitleScreen); // Add image to frame
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
                dnD.setIcon(icon);
                //gameIconTitleScreen.setBounds(50, -20, 300, 300);
                add(gameIconTitleScreen); // Add image to frame
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Image not found.");
        }



        add(dnD);
        add(testCase);
        add(colorPicker);

        // Add action listener to color picker button
        colorPicker.addActionListener(e -> pickColor());

        // Ensure all components are visible
        setVisible(true);
    }

    public static String getPath(){
        return path;
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
                        //System.out.println("Dropped file: " + file.getAbsolutePath());
                        path = file.getAbsolutePath();

                    }
                    dtde.dropComplete(true);
                    FilePan filePanel = new FilePan(path);
                    filePanel.setVisible(true);
                    //Dispose();

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
