import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.Vector;


public class kolokwium extends JFrame implements ActionListener {

    String[] valueMenuItem = {"Save", "Find", "Release"};

    JMenuBar menuBar;
    JTextArea textArea;
    JMenu menu;
    Vector<JMenuItem> menuItem;
    JScrollPane scrollPane;
    JFileChooser fileChooser;
    int startIndex;
    JMenuItem textF;

    public kolokwium() {
        setTitle("NOTATNIK");
        this.setPreferredSize(new Dimension(800, 600));

        Container mainPanel = this.getContentPane();
        mainPanel.setSize(800, 600);

        this.createMenuBar();

        textArea = new JTextArea();
        scrollPane = new JScrollPane();
        mainPanel.add(scrollPane);
        scrollPane.setViewportView(textArea);

        textArea.setSize(800, 600);

        this.pack();
    }

    public void createMenuBar() {

        menuBar = new JMenuBar();
        this.add(menuBar);
        setJMenuBar(menuBar);

        menu = new JMenu("Menu");
        menuBar.add(menu);

        menuItem = new Vector<JMenuItem>();
        for (String var : valueMenuItem) {
            menuItem.add(new JMenuItem(var));
        }
        for (JMenuItem var : menuItem) {
            var.addActionListener(this);
            menu.add(var);
        }
    }

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {


        kolokwium me = new kolokwium();
        me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        me.setVisible(true);

    }

    public void saveFile(String fileName, String value) {
        PrintWriter writer = null;
        ByteBuffer buf = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            buf = ByteBuffer.wrap(value.getBytes());

            FileChannel fc = FileChannel.open(Paths.get(fileName), StandardOpenOption.WRITE);
            fc.write(buf);
        } catch (IOException e) {
        }
        if (writer != null)
            writer.close();
        if (buf != null)
            buf.clear();
    }

    public void openFile(String to) {
        Path file = (new File(to)).toPath();
        try {
            SeekableByteChannel sbc = Files.newByteChannel(file, StandardOpenOption.READ);
            ByteBuffer buf = ByteBuffer.allocate(10);

            String encoding = System.getProperty("file.encoding");

            while (sbc.read(buf) > 0) {
                buf.rewind();
                System.out.println(Charset.forName(encoding).decode(buf));
                buf.flip();
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
    }

    public void find() {
        int select_start = textArea.getText().indexOf(textF.getText());
        if (select_start == -1) {
            startIndex = 0;
            JOptionPane.showMessageDialog(null, "Could not find \"" + textF.getText() + "\"!");
            return;
        }
        if (select_start == textArea.getText().lastIndexOf(textF.getText())) {
            startIndex = 0;
        }
        int select_end = select_start + textF.getText().length();
        textArea.select(select_start, select_end);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        JButton buttonSearch;
        JButton buttonNext;
        JButton buttonRelese;
        JButton buttonReleseAll;


        if (e.getSource() == menuItem.get(0)) {
            fileChooser = new JFileChooser();
            int returnVal = fileChooser.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                this.saveFile(fileToSave.getAbsolutePath(), textArea.getText());
                //this.openFile(fileToSave.getAbsolutePath());
                //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                //System.out.print(textArea.getText());
                //File file = new File();
            }
        } else {
            if (e.getSource() == menuItem.get(1) || e.getSource() == menuItem.get(2)) {

                JFrame frame = new JFrame("Znajdz");

                frame.setDefaultCloseOperation(HIDE_ON_CLOSE);

                Container container = frame.getContentPane();
                container.setLayout(new FlowLayout());
                JPanel panelLeft = new JPanel();
                container.add(panelLeft);
                JPanel panelRight = new JPanel();
                container.add(panelRight);

                JTextField field = new JTextField();
                panelLeft.add(field);
                field.setPreferredSize(new Dimension(200, 50));
                JTextField fieldRelese = new JTextField();
                panelLeft.add(fieldRelese);
                fieldRelese.setPreferredSize(new Dimension(200, 50));

                buttonSearch = new JButton("Szukaj");
                panelRight.add(buttonSearch);
                buttonNext = new JButton("Znajdz nastepny");
                panelRight.add(buttonNext);
                buttonRelese = new JButton("Zastap wartosc");
                panelRight.add(buttonRelese);
                buttonReleseAll = new JButton("Zastap wszystkie");
                panelRight.add(buttonReleseAll);

                buttonSearch.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                buttonNext.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                buttonRelese.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                buttonReleseAll.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });


                panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.PAGE_AXIS));
                panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.PAGE_AXIS));
                frame.pack();
                frame.setVisible(true);
            }
        }
    }
}
