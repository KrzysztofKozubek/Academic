import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Vector;

public class Board extends JFrame {

    public int size = 8;
    public int pxSize = 50;

    public Color colorField1 = Color.BLACK;
    public Color colorField2 = Color.WHITE;

    public Color colorPawn1 = Color.BLUE;
    public Color colorPawn2 = Color.CYAN;

    public int[][] border = new int[8][8];

    public Board() {
        fillBoard();
        createGUI();
    }

    public Board(int size, int pxSize, Color colorField1, Color colorField2, int[][] border) {

        this.border = border;
        this.size = size;
        this.pxSize = pxSize;
        this.colorField1 = colorField1;
        this.colorField2 = colorField2;

        createGUI();
    }

    public void createGUI() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(pxSize * size, pxSize * size);
        setVisible(true);
    }

    public void fillBoard() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (i == 0 && j % 2 == 0)
                    border[i][j] = 1;
                else if (i == 7 && j == 1)
                    border[i][j] = 2;

                //System.out.print(border[i][j] + " ");
            }
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        g.setColor(colorField1);
//g.fillOval(j, j, i, i);
                    } else {
                        g.setColor(colorField2);
                    }
                } else {
                    if (j % 2 == 0) {
                        g.setColor(colorField2);
                    } else {
                        g.setColor(colorField1);
                    }
                }
                g.fillRect(i * pxSize, j * pxSize, i * pxSize + pxSize, j * pxSize + pxSize);
            }
        }


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (border[i][j] != 0) {

                    if (border[i][j] == 1) {
                        g.setColor(colorPawn1);
                    } else g.setColor(colorPawn2);

                    g.fillOval(j * pxSize, i * pxSize, pxSize, pxSize);
                }
            }
        }

    }

    public void upDateBoard(String in) {
        //setVisible(false);
        char[] tab = in.toCharArray();

        int x1 = (int) tab[0] - 48;
        int x2 = (int) tab[1] - 48;
        int x3 = (int) tab[2] - 48;
        int x4 = (int) tab[3] - 48;

        int tmp = border[x1][x2];
        border[x1][x2] = 0;
        border[x3][x4] = tmp;
        this.repaint();
        new Board(size, pxSize, colorField1, colorField2, border);

    }


}

