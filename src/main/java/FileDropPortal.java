import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class FileDropPortal extends JFrame {

    private JTextField filePathField;

    public FileDropPortal() {
        setTitle("File Drop Portal");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel dropLabel = new JLabel("Drop File Here:");
        filePathField = new JTextField(20);
        JButton browseButton = new JButton("Browse");

        // Set layout
        setLayout(new FlowLayout());

        // Add components to the frame
        add(dropLabel);
        add(filePathField);
        add(browseButton);

        // Add action listeners
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        // Enable drag and drop
        new FileDrop(filePathField, new FileDrop.Listener() {
            @Override
            public void filesDropped(File[] files) {
                for (File file : files) {
                    filePathField.setText(file.getAbsolutePath());
                    System.out.println("File path: " + file.getAbsolutePath());
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileDropPortal();
            }
        });
    }
}

class FileDrop {

    public interface Listener {
        void filesDropped(File[] files);
    }

    public FileDrop(Component c, Listener listener) {
        new DropTarget(c, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    Transferable transferable = event.getTransferable();
                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                        listener.filesDropped(files.toArray(new File[0]));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
