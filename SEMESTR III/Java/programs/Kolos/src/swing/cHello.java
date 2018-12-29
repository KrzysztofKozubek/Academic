package swing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

public class cHello {

    public static void createAndShowGUI(){
        JFrame frame = new JFrame("TYTUŁ");
        JDialog frame1 = new JDialog();
        JApplet frame2 = new JApplet();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label1 = new JLabel("LABEL1 ");
        JLabel label2 = new JLabel("LABEL2 ");
        JLabel label3 = new JLabel("LABEL3 ");
        JLabel label4 = new JLabel("LABEL4 ");
        JLabel label5 = new JLabel("LABEL5 ");
        JLabel label6 = new JLabel("LABEL6 ");
        JLabel label7 = new JLabel("LABEL7 ");


        frame.getContentPane().add(label1, BorderLayout.AFTER_LINE_ENDS);
        frame.getContentPane().add(label2, BorderLayout.AFTER_LAST_LINE);
        frame.getContentPane().add(label3, BorderLayout.CENTER);
        frame.getContentPane().add(label4, BorderLayout.BEFORE_FIRST_LINE);
        frame.getContentPane().add(label5, BorderLayout.BEFORE_LINE_BEGINS);
        frame.getContentPane().add(label6, BorderLayout.EAST);
        frame.getContentPane().add(label7, BorderLayout.LINE_END);
        //frame.getContentPane().add(label);
        frame.setPreferredSize(new Dimension(400, 300));
        frame.pack();

        frame1.setVisible(true);
        frame.setVisible(true);
        frame2.setVisible(true);
    }

    public static void panel(){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();

        {
            JPanel p = new JPanel(new BorderLayout());
            p.setBackground(Color.GRAY);
            p.add(new JLabel("BLA LBA"), BorderLayout.CENTER);
            mainPanel.add(p);
            //f.getContentPane().add(p);
        }
        {
            JPanel p = new JPanel(new BorderLayout());
            p.setBackground(Color.CYAN);
            p.add(new JLabel("LKZXCJIOZ"), BorderLayout.AFTER_LINE_ENDS);
            mainPanel.add(p);
            //f.getContentPane().add(p);
        }

        f.getContentPane().add(mainPanel);
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //createAndShowGUI();
                panel();
            }
        });
    }
}
