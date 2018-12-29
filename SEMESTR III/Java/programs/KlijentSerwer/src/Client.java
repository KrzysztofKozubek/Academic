import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client {

    private static final int PORT = 50007;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) throws IOException {

        OknoKlient okno = new OknoKlient();
        okno.setSize(300, 200);
        okno.setTitle("Okno klienta.");
        okno.setVisible(true);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class OknoKlient extends JFrame implements ActionListener {
    JTextField pole1 = null, pole2 = null;
    Socket polaczenie = null;
    InputStream we = null;
    OutputStream wy = null;
    Watek1 w1 = null;
    Watek2 w2 = null;

    public OknoKlient() {
        setLayout(new FlowLayout());
        JLabel lab1 = new JLabel("Wpisz polskie słowo do przetłumaczenia:");
        add(lab1);
        pole1 = new JTextField(20);
        add(pole1);

        JButton przycisk1 = new JButton("wyczyść");
        przycisk1.addActionListener(this);
        add(przycisk1);

        JButton przycisk2 = new JButton("przetłumacz");
        przycisk2.addActionListener(this);
        add(przycisk2);

        JButton przycisk3 = new JButton("koniec");
        przycisk3.addActionListener(this);
        add(przycisk3);

        JLabel lab2 = new JLabel("Słowo w języku angielskim:");
        add(lab2);
        pole2 = new JTextField(20);
        add(pole2);

        try {
            polaczenie = new Socket("localhost", 8866);
        } catch (Exception e) {
        }
/* koniec konstruktora: */
    }

    public void actionPerformed(ActionEvent ev) {

        if (ev.getActionCommand() == "wyczyść") {
            pole1.setText("");
            pole2.setText("");
        }

        if (ev.getActionCommand() == "przetłumacz") {
            w2 = new Watek2(polaczenie, wy);
            w2.start();
        }
        if (ev.getActionCommand() == "koniec") {
            w1 = new Watek1(polaczenie, wy);
            w1.start();
        }
    }

    class Watek1 extends Thread {
        Socket polaczenie;
        OutputStream wy;
        DataOutputStream zapis = null;
        InputStream we;
        BufferedReader odczyt = null;

        public Watek1(Socket polaczenie, OutputStream wy) {
            this.polaczenie = polaczenie;
            this.wy = wy;
        }

        public void run() {
            try {
                wy = polaczenie.getOutputStream();
                zapis = new DataOutputStream(wy);
                zapis.writeBytes("koniec");
                polaczenie.close();
                System.exit(0);
            } catch (Exception e) {
            }


/* koniec metody run():   */
        }

/* koniec klasy wew: */
    }

    class Watek2 extends Thread {
        Socket polaczenie;
        OutputStream wy;
        DataOutputStream zapis = null;
        InputStream we;
        BufferedReader odczyt = null;

        public Watek2(Socket polaczenie, OutputStream wy) {
            this.polaczenie = polaczenie;
            this.wy = wy;
        }

        public void run() {
            try {
                wy = polaczenie.getOutputStream();
                String slowoPL = pole1.getText();

                if (slowoPL != null && !(slowoPL.equals(""))) {
                    zapis = new DataOutputStream(wy);
                    zapis.writeBytes(slowoPL + " ");

                    we = polaczenie.getInputStream();
                    odczyt = new BufferedReader(new InputStreamReader(we));
                    String slowoEN = odczyt.readLine();

                    if (slowoEN != null && !(slowoEN.equals(""))) {
                        pole2.setText(slowoEN);
                    } else if (slowoEN == null && !(slowoEN.equals(""))) {
                        pole2.setText("");
                    }
                }

            } catch (Exception e) {
            }

/* koniec metody run():   */
        }
/* koniec klasy wew: */

    }
}