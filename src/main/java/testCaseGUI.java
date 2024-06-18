import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/*

 */
public class testCaseGUI {
    //This is only used by console, but this would allow the control over the start and stopping of the thread
    public static Thread consoleThread;
    static final Object lock = new Object();
    static boolean triggerPrompt = false;
    static String uInput;
    static StyledDocument doc;
    static SimpleAttributeSet normal_Text;

    public static JButton setupButton(){
        JButton button =  new JButton();
        button.setText("Test Presets");
        return button;
    }

    public static JPanel setupTestGUI(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        //Set up console output to text area
        panel.add(setupConsole(), BorderLayout.CENTER);
        panel.add(setupInputField(), BorderLayout.SOUTH);
        return panel;
    }
    static JScrollPane setupConsole(){
        JTextPane console = new JTextPane();
        console.setEditable(false);
        doc = console.getStyledDocument();
        normal_Text = new SimpleAttributeSet();
        console.setBackground(Color.BLACK);
        StyleConstants.setForeground(normal_Text, Color.white);
        //set the console output
        PrintStream output = new PrintStream(new OutputStream() {
            @Override
            //this mirrors the user input to console output
            public void write(@NotNull byte[] b, int off, int len) {
                String text = new String(b, off, len, StandardCharsets.UTF_8);
                try {
                    doc.insertString(doc.getLength(), text, normal_Text);
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }
                // Automatically scroll down console
                console.setCaretPosition(doc.getLength());
            }
            @Override
            //this mirrors the user input to console output
            public void write(int b) {
                write(new byte[]{(byte) b}, 0, 1);
            }
        });
        System.setOut(output);
        JScrollPane scroll = new JScrollPane(console);
        scroll.setBackground(Color.BLACK);
        return scroll;
    }
    static JTextField setupInputField(){
        JTextField input = new JTextField();
        consoleThread = new Thread(() -> {
            //TL:DR, it reads input, and sends it to the console
            input.addKeyListener(new KeyListener() {
                boolean listenFor = true;
                @Override
                public void keyTyped(KeyEvent e) {

                }
                @Override
                public void keyPressed(KeyEvent e) {
                    /*
                     Order of operations:
                        - Enter key
                        - Check whether an input was requested
                        - if yes, print to console, and accept input
                        - else, do nothing
                     */
                    if(e.getKeyCode()== KeyEvent.VK_ENTER && listenFor){
                        if (triggerPrompt) {
                            try {
                                //this mirrors the user input to console output
                                uInput = input.getText();
                                doc.insertString(doc.getLength(),"\n" + uInput, normal_Text);
                            } catch (BadLocationException a) {
                                throw new RuntimeException(a);
                            }
                            input.setText("");
                            uInput = "";
                            synchronized(lock) {
                                while(triggerPrompt){
                                    try {
                                        lock.wait();
                                    } catch (InterruptedException interruptedException) {
                                        throw new RuntimeException(interruptedException);
                                    }
                                    lock.notifyAll();
                                }
                            }
                        }
                        else {
                            input.setBackground(Color.RED);
                            input.setForeground(Color.WHITE);
                            input.paint(input.getGraphics());
                            listenFor = false;
                            try{
                                TimeUnit.MILLISECONDS.sleep(225);
                                listenFor = true;
                            } catch (InterruptedException interruptedException) {
                                throw new RuntimeException(interruptedException);
                            }
                            input.setBackground(Color.WHITE);
                            input.setForeground(Color.BLACK);
                            input.paint(input.getGraphics());
                        }
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {

                }
            }
            );
        });
        consoleThread.start();
        return input;
    }

    static JTextPane setLogPanel(){
        JTextPane log = new JTextPane();
        log.setEditable(false);
        StyledDocument doc = log.getStyledDocument();
        log.setBackground(Color.BLACK);
        //set the console output
        PrintStream out = new PrintStream(new OutputStream() {
            @Override
            //this mirrors the user input to console output
            public void write(@NotNull byte[] b, int off, int len) {
                String text = new String(b, off, len, StandardCharsets.UTF_8);
                try {
                    doc.insertString(doc.getLength(), text, normal_Text);
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }
                // Automatically scroll down console
                log.setCaretPosition(doc.getLength());
            }
            @Override
            //this mirrors the user input to console output
            public void write(int b) {
                write(new byte[]{(byte) b}, 0, 1);
            }
        });
        System.setErr(out);
        return log;
    }
    public static String readLine(String question) throws InterruptedException {
        //Expected to run on Main-thread, instead of the consoleThread
        CountDownLatch latch =  new CountDownLatch(1);
        System.out.println(question);
        synchronized(lock){
            triggerPrompt = true;
            while(uInput.isEmpty()){
                TimeUnit.SECONDS.sleep(1);
            }
            System.err.println("Latch moved");
            latch.countDown();
        }
        return uInput;
    }

}
