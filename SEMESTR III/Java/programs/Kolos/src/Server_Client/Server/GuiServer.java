package Server_Client.Server;

import Server_Client.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;


public class GuiServer extends JFrame {

    JPanel panelTolk;
    JScrollPane scrollPane;
    JTextField textField;
    JButton buttonSend;


    public Server server;
    Colors color = new Colors();
    int width = 180;
    int height = 390;

    public String fileRef = new String();
    public String fileRef1 = new String();

    public GuiServer(Server serverRef) {

        super("Chat Server by Kris");
        this.server = serverRef;


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setPreferredSize(new Dimension(width * 2, height));

        JPanel panelMain = new JPanel();
        add(panelMain);
        panelMain.setPreferredSize(new Dimension(width, height));
        panelMain.setBackground(color.bgMain);

        JPanel panelFrame = new JPanel();
        panelMain.add(panelFrame);
        panelFrame.setBackground(color.bgChat);
        panelFrame.setBorder(BorderFactory.createLineBorder(Color.gray));
        panelFrame.setPreferredSize(new Dimension((int) (width * 1.7), (int) (height * 0.7)));
        panelFrame.setLayout(new FlowLayout());

        panelTolk = new JPanel();
        //panelFrame.add(panelTolk);
        panelTolk.setLayout(new BoxLayout(panelTolk, BoxLayout.Y_AXIS));
        panelTolk.setBackground(null);
        panelTolk.setOpaque(true);

        //createCloudLeft("1alksdh d asdas sd as dasdassads ads ds dsdsdadsdas dasdasadsads ads dasdas dasdad ddsa adsadsadsdasdasda");
        //createCloudRight("1alksdh d asdas sd as dasdassads ads ds dsdsdadsdas dasdasadsads ads dasdas dasdad ddsa adsadsadsdasdasda");


        scrollPane = new JScrollPane(panelTolk);
        panelFrame.add(scrollPane);
        scrollPane.getViewport().setBackground(color.bgChat);
        scrollPane.setPreferredSize(new Dimension((int) (width * 1.67), (int) (height * 0.67)));
        scrollPane.setBorder(null);
        scrollPane.isWheelScrollingEnabled();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getAutoscrolls();
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });
        //scrollPane.setBounds(50, 30, 300, 50);

        textField = new JTextField();
        panelMain.add(textField);
        textField.setPreferredSize(new Dimension((int)(width * 1.3 ), 20));
        textField.setBackground(color.bgChat);
        textField.setForeground(color.text);

        buttonSend = new JButton("Send");
        panelMain.add(buttonSend);

        buttonSend.addActionListener(new ActionListener() {
                                         @Override
                                         public void actionPerformed(ActionEvent e) {

                                             if (textField.getText().equals("")) {
                                                 JFileChooser fileChooser = new JFileChooser();
                                                 fileChooser.setDialogTitle("Choose a file");

                                                 int ret = fileChooser.showDialog(null, "Open file");

                                                 if (ret == JFileChooser.APPROVE_OPTION) {
                                                     File file = fileChooser.getSelectedFile();
                                                     System.out.println(file);
                                                     fileRef1 = file.getPath();
                                                     fileRef = file.getName();

                                                     server.sendMessage("FILE");
                                                     server.sendMessage(fileRef);

                                                     Thread th = new Thread(new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             server.sendFile(fileRef1);
                                                         }
                                                     });
                                                     th.run();
                                                 }
                                             }else{

                                                 server.sendMessage(textField.getText() + "<br>");
                                                 textField.setText("");
                                             }
                                         }
                                     }
        );

        textField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.sendMessage(textField.getText() + "<br>");
                textField.setText("");
            }
        });


        pack();
    }

    public void createCloudLeft(String tolk) {

        String html = "<html><body style=\"width: " + width + "px\">";

        JPanel panel = new JPanel();
        panelTolk.add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        //panel.setPreferredSize(new Dimension(280, 50));
        panel.setBackground(null);

        JPanel panelLeft = new JPanel();
        panel.add(panelLeft);

        JLabel label = new JLabel(html + tolk);
        panelLeft.add(label);
        panelLeft.setBackground(color.bgMessageLeft);
        panelLeft.setBorder(BorderFactory.createLineBorder(color.frameMessageLeft, 1, false));
        label.setBackground(color.bgMessageLeft);
        label.setOpaque(true);
    }

    public void createCloudRight(String tolk) {

        String html = "<html><body style=\"width: " + width + "px\">";

        JPanel panel = new JPanel();
        panelTolk.add(panel);
        //panel.setPreferredSize(new Dimension(280, 50));
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(null);

        JPanel panelLeft = new JPanel();
        panel.add(panelLeft);

        JLabel label = new JLabel(html + tolk);
        panelLeft.add(label);
        panelLeft.setBackground(color.bgMessageRight);
        panelLeft.setBorder(BorderFactory.createLineBorder(color.frameMessageRight, 1, false));
        label.setBackground(color.bgMessageRight);
        label.setOpaque(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuiServer(new Server());
            }
        });
    }

}
