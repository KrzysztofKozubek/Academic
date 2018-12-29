package swing;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Created by kris_ on 2015-01-20.
 */
public class cFileChooser {


    public static void createMessage(){
        JFrame frame = new JFrame("InputDialog Example #1");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        String name = null;



        Object[] possible = {"asd", "xzc", "wrwwe"};
        name = (String) JOptionPane.showInputDialog(frame,
                "What is your favorite pizza?",
                "Favorite Pizza",
                JOptionPane.ERROR_MESSAGE,
                null,
                possible,
                possible[0]);



        name = JOptionPane.showInputDialog(frame, "What's your name?");

        JOptionPane.showMessageDialog(frame,
                "Problem writing to backup directory: '" + name + "'.",
                "Backup problem",
                JOptionPane.INFORMATION_MESSAGE);



    }

    public static void createFileChooser(){
        JFrame frame= new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("OBRAZKI", "jpg", "png");

//int ret = fileChooser.showOpenDialog(parent);

        frame.add(fileChooser);
        frame.pack();
        frame.setVisible(true);
    }

    public static void createGUI(){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JFileChooser fch = new JFileChooser();
        FileNameExtensionFilter filtr = new FileNameExtensionFilter("OBRAZY JPG I PNG", "jpg", "png");
        fch.setFileFilter(filtr);


        f.add(fch);
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                createMessage();
               createGUI();
            }
        });
    }
}
