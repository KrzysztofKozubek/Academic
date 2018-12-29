import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class Board extends JFrame implements ActionListener {

    private final int dim = 8;
    private final int fldSize = 50;
    private final int iconSize = 30;
    private static Vector<Integer> pleaceIcon = new Vector<Integer>();

    private final Icon icon1 = new OvalIcon(Color.magenta, iconSize);
    private final Icon icon2 = new OvalIcon(Color.BLUE, iconSize);

    private final Color black = Color.black;
    private final Color white = Color.white;

    private Container cPane = getContentPane();
    private JLayeredPane layers = getLayeredPane();
    private JPanel chessPane = new JPanel();
    private MouseWatcher mWatcher = new MouseWatcher();

    public Board() {

        super("WILK I OWCE");
        this.fillVector();
        setLocation(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        cPane.setLayout(new BorderLayout());

        fillBoard();

        pack();
        show();
    }

    public Board(Vector<Integer> pleaceIcon) {

        this.fillVector(pleaceIcon);
        this.pleaceIcon = pleaceIcon;
        setLocation(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        cPane.setLayout(new BorderLayout());

        fillBoard();

        pack();
        show();
    }


    private void fillVector() {

        int fldpo = 0;
        int number = 0;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {

                number = 0;
                if (i % 2 == 0) {
                    if (fldpo % 2 == 1) {

                        number = 1;
                        if (i == 0) number = 2;
                        else if ((i == dim - 1 && j == 0) || (i == dim - 1 && j == 1)) number = 3;
                    }
                } else {
                    if (fldpo % 2 == 0) {

                        number = 1;
                        if (i == 0) number = 2;
                        else if ((i == dim - 1 && j == 0) || (i == dim - 1 && j == 1)) number = 3;
                    }
                }


                pleaceIcon.add(number);
                fldpo++;
            }
        }
    }

    private void fillVector(Vector<Integer> vector) {

        int fldpo = 0;
        int number = 0;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {

                pleaceIcon.set(fldpo, vector.get(fldpo));
            }
        }
    }

    private void fillBoard() {

        short fldPos = 0;
        cPane.add(chessPane, BorderLayout.CENTER);
        chessPane.setLayout(new GridLayout(dim, dim));

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {

                JLabel field = null;
                Icon icon = null;
                Color bgColor = null;

                if (this.pleaceIcon.get(fldPos) == 0) {

                    bgColor = black;
                    field = new JLabel();
                } else {

                    bgColor = white;
                    field = new JLabel();
                    if (this.pleaceIcon.get(fldPos) == 2) {

                        icon = icon1;
                        field.setIcon(icon);
                    } else if (this.pleaceIcon.get(fldPos) == 3) {

                        icon = icon2;
                        field.setIcon(icon);
                    }
                }

                field.setHorizontalAlignment(JLabel.CENTER);
                field.setPreferredSize(new Dimension(fldSize, fldSize));
                field.setBackground(bgColor);
                field.setOpaque(true);

                field.addMouseListener(mWatcher);
                field.addMouseMotionListener(mWatcher);

                chessPane.add(field);
                fldPos++;
            }
        }
    }

    private void fillBoard(Vector<Integer> vector) {

        short fldPos = 0;
        cPane.add(chessPane, BorderLayout.CENTER);
        chessPane.setLayout(new GridLayout(dim, dim));

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {

                JLabel field = null;
                Icon icon = null;
                Color bgColor = null;

                if (vector.get(fldPos) == 0) {

                    bgColor = black;
                    field = new JLabel();
                } else {

                    bgColor = white;
                    field = new JLabel();
                    if (vector.get(fldPos) == 1) {


                        icon = icon1;
                        field.setIcon(icon);
                        pleaceIcon.add(2);
                    } else {

                        icon = icon2;
                        field.setIcon(icon);
                        pleaceIcon.add(2);
                    }

                }

                field.setHorizontalAlignment(JLabel.CENTER);
                field.setPreferredSize(new Dimension(fldSize, fldSize));
                field.setBackground(bgColor);
                field.setOpaque(true);

                field.addMouseListener(mWatcher);
                field.addMouseMotionListener(mWatcher);

                chessPane.add(field);
                fldPos++;
            }
        }
    }

    public static void main(String[] args) {

        new Board();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }




    class MouseWatcher implements MouseInputListener {

        private boolean dragging = false;
        private JLabel dragged;
        private int lx, ly;
        private int ex, ey;

        public int[] tab = new int[5];

        public MouseWatcher() {

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

            JLabel labell = (JLabel) e.getSource();
            tab[0] = labell.getX()/dim;
            tab[1] = labell.getY()/dim;

            JLabel label = (JLabel) e.getSource();
            Icon icon = label.getIcon();
            label.setIcon(null);
            dragged.setIcon(icon);
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

                System.out.print(dst.getX() / dim + " ");
                tab[2] = dst.getX()/dim;
                tab[3] = dst.getY()/dim;

            } else {
                dst = src;
            } if (dst.getBackground() == white && dst.getIcon() == null) {
                dst.setIcon(dragged.getIcon());
            } else {
                src.setIcon(dragged.getIcon());
            }




            dragged.setVisible(false);
        }

        public void mouseDragged(MouseEvent e) {

            for(int i=0;i<5;i++)
                tab[i]=0;
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
                System.out.println(var);

            }
        }
    }
}
