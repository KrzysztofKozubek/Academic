package swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by kris_ on 2015-01-20.
 */
public class cLisner implements ActionListener {

    JButton buttonMessage;

    public void createFrame(){
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();

        JPanel topPanel = new JPanel();
        buttonMessage = new JButton("Message");
        buttonMessage.addActionListener(this);

        topPanel.add(buttonMessage);

        mainPanel.add(topPanel);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == buttonMessage){
            JOptionPane.showMessageDialog(new JFrame(), "Został naciśnięty przycisk niedziałający");
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "Został naciśnięty przycisk niedziałający2");
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            new cLisner().createFrame();
            }
        });
    }


}
