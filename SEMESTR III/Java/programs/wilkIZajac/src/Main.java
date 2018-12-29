import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.event.*;

public class Main extends JFrame {

    private final int dim = 8;
    private final int fldSize = 50;
    private final int iconSize = 32;
    private Vector<Integer> pleaceIcon = new Vector<Integer>(64);

    private final Icon icon1 = new OvalIcon(Color.magenta, iconSize);
    private final Icon icon2 = new OvalIcon(Color.BLUE, iconSize);

    private final Color black = Color.black;
    private final Color white = Color.white;

    private Container cPane = getContentPane();
    private JLayeredPane layers = getLayeredPane();
    private JPanel chessPane = new JPanel();
    private MouseWatcher mWatcher = new MouseWatcher();

    public Main() {

        super("ŁĄCZYMY SIĘ PRZEZ INTERNET");
        setLocation(200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        cPane.setLayout(new BorderLayout());

        fillBoard();

        pack();
        show();
    }

    private void fillBoard() {

        short fldPos = 0;
        cPane.add(chessPane, BorderLayout.CENTER);
        chessPane.setLayout(new GridLayout(dim, dim));

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                Color bgColor = (fldPos % 2 == 0 ? white : black);
                JLabel field = new JLabel();


                if (i == 0 && j % 2 == 0) {
                    Icon icon = (j % 2 == 0 ? icon2 : icon1);
                    field.setIcon(icon);
                    pleaceIcon.add(2);
                }else{
                if (i == 7 && j == 1) {
                    Icon icon = (i % 2 == 0 ? icon2 : icon1);
                    field.setIcon(icon);
                    pleaceIcon.add(1);
                }else {pleaceIcon.add(0);}}

                field.setHorizontalAlignment(JLabel.CENTER);
                field.setPreferredSize(new Dimension(fldSize, fldSize));
                field.setBackground(bgColor);
                field.setOpaque(true);

                field.addMouseListener(mWatcher);
                field.addMouseMotionListener(mWatcher);

                chessPane.add(field);
                fldPos++;
            }
            fldPos++;
        }
    }


    class MouseWatcher implements MouseInputListener {

        private boolean dragging = false;
        private JLabel dragged;
        private int lx, ly;
        private int ex, ey;

        MouseWatcher() {
            dragged = new JLabel();
            dragged.setHorizontalAlignment(JLabel.CENTER);
            dragged.setPreferredSize(new Dimension(fldSize, fldSize));
            dragged.setVisible(false);
            layers.add(dragged, JLayeredPane.DRAG_LAYER);
        }

        public void mousePressed(MouseEvent e) {

            if (e.getPoint().distance(new Point(fldSize / 2, fldSize / 2)) > iconSize / 2) {
                return;
            }

            JLabel label = (JLabel) e.getSource();
            Icon icon = label.getIcon();

            label.setIcon(null);

            ex = e.getX();
            ey = e.getY();

            lx = label.getX();
            ly = label.getY();

            dragged.setIcon(icon);

            dragged.setBounds(lx, ly, fldSize, fldSize);

            dragged.setVisible(true);
            dragging = true;
        }

        public void mouseReleased(MouseEvent e) {

            if (!dragging) {
                return;
            }
            dragging = false;

            JLabel src = (JLabel) e.getSource();

            Point srcPos = src.getLocation();

            Point dstPos = e.getPoint();

            srcPos.translate(dstPos.x, dstPos.y);
            JLabel dst;

            Component c = chessPane.getComponentAt(srcPos);


            if (c instanceof JLabel) {

                dst = (JLabel) c;
            } else {

                dst = src;
            }


            if (dst.getBackground() == white && dst.getIcon() == null) {
                dst.setIcon(dragged.getIcon());
            } else {
                src.setIcon(dragged.getIcon());
            }

            dragged.setVisible(false);
        }

        public void mouseDragged(MouseEvent e) {

            dragged.setLocation(lx + e.getX() - ex, ly + e.getY() - ey);
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void showVector() {

            for (Integer var : pleaceIcon) {
                System.out.println(  var);

            }
        }
    }

    public static void main(String[] args) throws Exception {

        Main my = new Main();
        //Server.main(args);
        //Client.main(args);

    }


}

