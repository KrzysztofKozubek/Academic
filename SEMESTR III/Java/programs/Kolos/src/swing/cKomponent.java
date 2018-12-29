package swing;

import javax.swing.*;
import javax.swing.text.StringContent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class cKomponent extends JFrame implements ActionListener {

    private static final String COMMAND_GO = "go";
    private JEditorPane webpage;
    private JTextField url;
    private JTextArea htmlPage;

    public cKomponent() {
        super();
        this.getContentPane().add(this.createMainPanel());
    }

    private JPanel createMainPanel() {
        JPanel mp = new JPanel();
        mp.setLayout(new BoxLayout(mp, BoxLayout.Y_AXIS));

        JPanel p = new JPanel();
        this.url = new JTextField();

        this.url.setPreferredSize(new Dimension(500, 20));
        this.url.setText("http://google.com");
        JLabel l = new JLabel("address");
        l.setLabelFor(this.url);

        p.add(l);
        p.add(this.url);

//button
        JButton b = new JButton("GO");
        b.setActionCommand(COMMAND_GO);

        b.addActionListener(this);
        b.setPreferredSize(new Dimension(100, 40));

        p.add(b);
        mp.add(p);
//end button


        this.webpage = new JEditorPane();
        this.htmlPage = new JTextArea();
        try {
            this.setPage(new URL("http://zamulacztv.cba.pl"));
        } catch (IOException e) {
        }

        JTabbedPane tp = new JTabbedPane();
        tp.setPreferredSize(new Dimension(600, 400));
        JScrollPane sp = new JScrollPane(this.webpage);
        tp.add("page", sp);
        sp = new JScrollPane(this.htmlPage);
        tp.add("html", sp);
        mp.add(tp);

        return mp;


    }

    public static void createAndShow() {
        cKomponent k = new cKomponent();
        k.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        k.pack();
        k.setLocationRelativeTo(null);
        k.setVisible(true);
    }

    private void setPage(URL page) throws IOException {
        String s;
        this.webpage.setPage(page);
        BufferedReader br = new BufferedReader(new InputStreamReader(page.openStream()));
        while ((s = br.readLine()) != null) this.htmlPage.append(s + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (COMMAND_GO.equals(e.getActionCommand())) {
            try {
                this.setPage(new URL(this.url.getText()));
            } catch (IOException e2) {
                this.webpage.setText("Problem z adresem " + this.url.getText());
                this.htmlPage.setText("Problem z adresem " + this.url.getText());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShow();
            }
        });
    }
}
