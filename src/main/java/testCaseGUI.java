import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
/**
 * @author braydenleung-Git
 * This class is used to set up the GUI for the test case preset panel
 */
public class testCaseGUI /*extends testCaseHandler*/{
    //This is only used by console, but this would allow the control over the start and stopping of the thread
    public static Thread consoleThread;
    static final Object lock = new Object();
    static boolean triggerPrompt = false;
    static String uInput = "";
    static StyledDocument doc;
    static SimpleAttributeSet normal_Text;

    public static JButton setupButton() {
        JButton button =  new JButton();
        button.setText("Test Presets");
        button.addActionListener(e->{
            main.changeLayout(400,700,"Test Case Console");
            new Thread(()->{
                //Test json to sorting algorithm
                testCaseHandler.writeToJson();
                System.out.println("Which file would you like to see?");
                try {
                    for (int i = 0; i < testCaseHandler.getItemList().size(); i++) {
                        System.out.println((i+1)+". "+ testCaseHandler.getItemList().get(i));
                    }
                    int uI = readInt("Enter number: ");
                    boolean doNotExit = true;
                    while(doNotExit){
                        if((uI) > testCaseHandler.getItemList().size()){
                            flush();
                            for (int i = 0; i < testCaseHandler.getItemList().size(); i++) {
                                System.out.println(testCaseHandler.getItemList().get(i));
                            }
                            uI = readInt("Invalid input, please Enter Number:");
                        }
                        else{
                            doNotExit = false;
                        }
                    }
                    startMenuGUI.path = testCaseHandler.getItem(uI).toString();
                    Image2Array.processImage(startMenuGUI.path);
                    shapeLogic.shapeL(Image2Array.getTranscodedArray());
                    flush();
                    main.changeLayout(400,400,"Output Panel");

                }catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }).start();
        });
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
        console.setFont(main.HelvetciaNeue_Cond_B_05.deriveFont(20f));
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
        input.setFont(main.HelvetciaNeue_Cond_B_05.deriveFont(20f));
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
                            synchronized(lock) {
                                triggerPrompt = false;
                                lock.notifyAll();
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

    public static class LogPanel {
        public static void setupLogPanel(int width, int height){
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
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(log);
            f.setSize(width,height);
            f.setLocation(0,0);
            f.setVisible(true);
        }
    }

    public static String readLine(String question) {
        //Expected to run on Main-thread, instead of the consoleThread
        CountDownLatch latch =  new CountDownLatch(1);
        System.out.print(question);
        synchronized(lock){
            triggerPrompt = true;
            uInput = "";
            while(uInput.isEmpty()){
                try {
                    lock.wait();
                    //System.err.println("Still sleeping");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //System.err.println("Latch moved");
            latch.countDown();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
        return uInput;
    }

    public static int readInt(String question){
        String output = readLine(question);
        try{
            return Integer.parseInt(output);
        } catch (NumberFormatException e){
            System.out.println("Unexpected input, please try again");
            return readInt(question);
        }
    }

    public static void flush(int l){
        for (int i = 0; i < l; i++) {
            System.out.println();
        }
    }

    public static void flush(){
        flush(100);
    }
}
