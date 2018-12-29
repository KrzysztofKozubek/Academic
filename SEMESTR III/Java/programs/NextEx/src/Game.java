
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame implements ActionListener {

    public JFrame frame;
    public JButton buttonServer;
    public JButton buttonClient;

    public static int numberPlayer = 0;

    public String address = "localhost";
    public int port = 5005;

    public Game() {

        frame = new JFrame();
        buttonServer = new JButton("Server");
        buttonClient = new JButton("Client");

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());

        contentPane.add(buttonServer);
        contentPane.add(buttonClient);

        buttonClient.addActionListener(this);
        buttonServer.addActionListener(this);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        numberPlayer++;

        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {

        new Game();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonServer) {

            Server.main(numberPlayer, address, port);

        } else {
            if (e.getSource() == buttonClient) {

                Client.main(numberPlayer, address, port);
            }
        }
    }
}
