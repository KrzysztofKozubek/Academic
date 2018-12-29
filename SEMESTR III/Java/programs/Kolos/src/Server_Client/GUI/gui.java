package Server_Client.GUI;


import Server_Client.Client.Client;
import Server_Client.Server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui extends JFrame {

    JTextField field;
    JLabel label = new JLabel("Port: ");
    JButton buttonServer;
    JButton buttonCLient;

    public gui() {

        super("char by Kris");

        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        container.add(panel);

        JPanel panelButton = new JPanel();
        container.add(panelButton);

        field = new JTextField();
        panel.add(label);
        panel.add(field);

        field.setPreferredSize(new Dimension(150, 20));

        buttonCLient = new JButton("Client");
        panelButton.add(buttonCLient);

        buttonServer = new JButton("Server");
        panelButton.add(buttonServer);


        buttonCLient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
                Client.start(field.getText());


            }
        });

        buttonServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);

                Server.start(field.getText());


            }
        });


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();

        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui GUI = new gui();
            }
        });
    }
}
