import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.io.File;
import java.util.List;

public class fileDrop {

    JTextArea myPanel = new JTextArea();
myPanel.setDropTarget(new DropTarget() {
        public synchronized void drop(DropTargetDropEvent evt) {
            try {
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                List<File> droppedFiles = (List<File>)
                        evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                for (File file : droppedFiles) {
                    // process files
                }
                evt.dropComplete(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
}
