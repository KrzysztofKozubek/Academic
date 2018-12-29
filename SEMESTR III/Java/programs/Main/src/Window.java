import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Window implements ActionListener {

    JFrame frame;
    JButton buttonCompresion;
    JButton buttonDecompresion;
    JButton buttonCodder;
    JButton buttonDecodder;
    JButton buttonExit;
    JTabbedPane tabbedPane1;
    JFileChooser fileChooser;

    public Window() {
        this.zbudujGUI();
    }

    public void zbudujGUI() {

        frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 200));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        height = (height / 4 - frame.getHeight()) ;
        width = (width / 4 - frame.getWidth());
        frame.setLocation(new Point(width, height));

        tabbedPane1 = new JTabbedPane();

        buttonCompresion = new JButton("Compresion!");
        buttonDecompresion = new JButton("Decompresion!");
        buttonCodder = new JButton("Codder!");
        buttonDecodder = new JButton("Decodder!");
        buttonExit = new JButton("Exit");


        buttonCompresion.addActionListener(this);
        buttonDecompresion.addActionListener(this);
        buttonCodder.addActionListener(this);
        buttonDecodder.addActionListener(this);
        buttonExit.addActionListener(this);

        frame.setLayout(new GridLayout(0, 1));
        //frame.setLayout(new GroupLayout());
        //tabbedPane.setPreferredSize(new Dimension(buttonExit.getWidth()/2, buttonExit.getHeight()));

        JPanel  panel = new JPanel();
        panel.add(buttonCompresion);
        panel.add(buttonDecompresion);
        tabbedPane1.add("Compresing", panel);

        panel  = new JPanel();
        panel.add(buttonCodder);
        panel.add(buttonDecodder);
        tabbedPane1.add("Codding", panel);

        frame.getContentPane().add(tabbedPane1);

        //frame.getContentPane().add(buttonCompresion);
        //frame.getContentPane().add(buttonDecompresion);
        //frame.getContentPane().add(buttonCodder);
        //frame.getContentPane().add(buttonDecodder);

        frame.getContentPane().add(buttonExit);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent akcja) {

        if (akcja.getSource() == buttonCompresion) {

            fileChooser = new JFileChooser();
            //FileNameExtensionFilter fileNameExtensionFileter = new FileNameExtensionFilter("Select txt files", "txt");
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.getName().startsWith("code") == true && f.getName().endsWith(".txt") || f.isDirectory())
                        return true;
                    return false;
                }

                @Override
                public String getDescription() {
                    return null;
                }
            };
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setMultiSelectionEnabled(true);

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File[] files = fileChooser.getSelectedFiles();
                try {
                    EditFile.compression(files);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (akcja.getSource() == buttonDecompresion) {

            fileChooser = new JFileChooser();
            FileNameExtensionFilter fileNameExtensionFileter = new FileNameExtensionFilter("Select zip files", "zip");

            fileChooser.setFileFilter(fileNameExtensionFileter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setMultiSelectionEnabled(false);

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    EditFile.decompression(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else if (akcja.getSource() == buttonCodder) {

            fileChooser = new JFileChooser();
            //FileNameExtensionFilter fileNameExtensionFileter = new FileNameExtensionFilter("Select text files", "txt");
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.getName().endsWith(".txt") || f.isDirectory())
                        return true;
                    return false;
                }

                @Override
                public String getDescription() {
                    return null;
                }
            };

            fileChooser.setFileFilter(fileFilter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setMultiSelectionEnabled(false);

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    EditFile.code(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        } else if (akcja.getSource() == buttonDecodder) {

            fileChooser = new JFileChooser();
            //FileNameExtensionFilter fileNameExtensionFileter = new FileNameExtensionFilter("Select txt files", "txt");

            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.getName().startsWith("code") == true && f.getName().endsWith(".txt") || f.isDirectory())
                        return true;
                    return false;
                }

                @Override
                public String getDescription() {
                    return null;
                }
            };
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setMultiSelectionEnabled(false);

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    EditFile.decode(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else if (akcja.getSource() == buttonExit) {

            System.exit(frame.EXIT_ON_CLOSE);
        }

    }
}
