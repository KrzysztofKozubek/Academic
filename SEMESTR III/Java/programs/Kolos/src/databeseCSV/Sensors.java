package databeseCSV;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import databeseCSV.model.Sensor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by Kris on 2015-05-04.
 */
public class Sensors extends JFrame {

    private Datebese datebese = new Datebese();
    private String csvFile;

    /*       main frame         */
    private JTabbedPane tabbedPane = new JTabbedPane();
    private Colors color = new Colors();
    private JPanel Cmain = new JPanel();
    private int height = 395;
    private int width = 700;

    /*       first row         */
    private JPanel panelBreak;
    private JPanel panelLoadCSV;
    private JButton loadFile;
    private JTextField textField;
    /*       second row         */
    private JPanel secondPanel;
    private JPanel sPanelImage;
    private ImageIcon image;
    private JLabel label;
    private int heightImg = 424;
    private int widthImg = 800;
    private int sizeCircle = 5;
    private Point[] pointOnMap;

    class IconPanel extends JPanel {

        private Image img;
        private double skale = 0.9;
        private Points points = new Points();
        private boolean dontUse = true;

        public IconPanel(Image img) { this.img = img; }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            heightImg = (int)(height * skale);
            widthImg = (int)(width * skale);
            points.fillUp(widthImg, heightImg);

            setBackground(color.bgMain);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
            g2.drawImage(img, 10, 10, widthImg, heightImg, this);
            g2.setColor(color.circle);

            sPanelImage.addMouseListener(new MouseAdapter() {
                @Override public void mousePressed(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();

                    if (points.havePoint(x, y, pointOnMap) && dontUse){
                        dontUse = false;
                        System.out.println(x + " " + y);
                    }else dontUse = true;
                }
                @Override public void mouseReleased(MouseEvent e) {System.out.print("");} });

            Ellipse2D.Double circle1 = new Ellipse2D.Double(points.getX(19, 120), points.getY(50, 50), sizeCircle, sizeCircle);
            g2.fill(circle1);
            Ellipse2D.Double circle2 = new Ellipse2D.Double(points.getX(0, -90), points.getY(-50, 0), sizeCircle, sizeCircle);
            g2.fill(circle2);
            Ellipse2D.Double circle3 = new Ellipse2D.Double(points.getX(0, 90), points.getY(-50, 50), sizeCircle, sizeCircle);
            g2.fill(circle3);

            //g2.drawRect();
        }
    }

    /*       third row       */
    private JPanel thirdPanel;
    private JPanel thirdBrakeTopPanel;
    private JPanel thirdInformaitonPanel;


    private JLabel thirdLabelAverage = new JLabel("<html> <font color=\""+color.textBasicInformationTextLabel+"\">Average</font><font color=\""+color.textBasicInformationValue+"\">");
    private JLabel thirdLabelVariance = new JLabel("<html> <font color=\""+color.textBasicInformationTextLabel+"\">Variance</font><font color=\""+color.textBasicInformationValue+"\">");
    private JLabel thirdLabelFrequency = new JLabel("<html> <font color=\""+color.textBasicInformationTextLabel+"\">Frequency</font><font color=\""+color.textBasicInformationValue+"\">");
    private JLabel thirdLabelDominant = new JLabel("<html> <font color=\""+color.textBasicInformationTextLabel+"\">Dominant</font><font color=\""+color.textBasicInformationValue+"\">");
    private JLabel thirdLabelMedian = new JLabel("<html> <font color=\""+color.textBasicInformationTextLabel+"\">Median</font><font color=\""+color.textBasicInformationValue+"\">");
    private JLabel thirdLabelQuantile = new JLabel("<html> <font color=\""+color.textBasicInformationTextLabel+"\">Quantile</font><font color=\""+color.textBasicInformationValue+"\">");
    private JLabel thirdLabelHeave = new JLabel("<html> <font color=\""+color.textBasicInformationTextLabel+"\">Heave</font><font color=\""+color.textBasicInformationValue+"\">");
    private JLabel thirdLabelStandardDevation = new JLabel("<html> <font color=\""+color.textBasicInformationTextLabel+"\">Standard Devation</font><font color=\""+color.textBasicInformationValue+"\">");
    private JLabel thirdLabelKurtosis = new JLabel("<html> <font color=\""+color.textBasicInformationTextLabel+"\">Kurtosis</font><font color=\""+color.textBasicInformationValue+"\">");

    Sensors(){
        super("title");
        setLocation(400, 75);
        Cmain.setBackground(color.bgMain);
        Cmain.setPreferredSize(new Dimension(width, height));

        /*       first row         */
        setFirstPanel();
        /*       second row         */
        setSecondPanel();
        /*       third row         */
        setThirdPanel();


        addToTabblePanel(panelLoadCSV, "B22222", "Load CSV");
        addToTabblePanel(secondPanel, "B22222", "Map");
        addToTabblePanel(thirdPanel, "B22222", "Basic Statistic");

        Cmain.add(tabbedPane);
        add(Cmain);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        this.addComponentListener(new ComponentListener() {
            @Override public void componentResized(ComponentEvent e) { responspible(); }
            @Override public void componentMoved(ComponentEvent e) {}
            @Override public void componentShown(ComponentEvent e) {}
            @Override public void componentHidden(ComponentEvent e) {}
        });
        pack();
    }

    private void addToTabblePanel(JPanel panel, String color, String title){
        tabbedPane.addTab("<html><font color=\""+color+"\">"+title+"</font>", panel);
    }

    private void responspible(){
        height = getHeight();
        width = getWidth();

        int heightBreakPanel = height / 3;
        Cmain.setPreferredSize(new Dimension(width, height));
        panelBreak.setPreferredSize(new Dimension(width, heightBreakPanel));
        panelLoadCSV.setPreferredSize(new Dimension(width - 50, height - 50));
        textField.setPreferredSize(new Dimension(width - 80, 30));
        tabbedPane.setPreferredSize(new Dimension(width - 50, height -50));

        secondPanel.setPreferredSize(new Dimension(width-50, height-50));
        sPanelImage.setPreferredSize(new Dimension(width-50, height -50));
        heightImg = height -60;
        widthImg = width -60;


        int widthLabel = width - 100;
        int heightLabel = 25;
        thirdPanel.setPreferredSize(new Dimension(width-50, height-50));
        thirdBrakeTopPanel.setPreferredSize(new Dimension(width-50, 50));
        thirdInformaitonPanel.setPreferredSize(new Dimension(width-50, height-50));

        thirdLabelAverage.setPreferredSize(new Dimension(widthLabel, heightLabel));
        thirdLabelVariance.setPreferredSize(new Dimension(widthLabel, heightLabel));
        thirdLabelFrequency.setPreferredSize(new Dimension(widthLabel, heightLabel));
        thirdLabelDominant.setPreferredSize(new Dimension(widthLabel, heightLabel));
        thirdLabelMedian.setPreferredSize(new Dimension(widthLabel, heightLabel));
        thirdLabelQuantile.setPreferredSize(new Dimension(widthLabel, heightLabel));
        thirdLabelHeave.setPreferredSize(new Dimension(widthLabel, heightLabel));
        thirdLabelStandardDevation.setPreferredSize(new Dimension(widthLabel, heightLabel));
        thirdLabelKurtosis.setPreferredSize(new Dimension(widthLabel, heightLabel));
    }

    private void setFirstPanel(){
        panelBreak = new JPanel();
        panelBreak.setBackground(color.bgMain);
        panelBreak.setPreferredSize(new Dimension(width / 3, height / 3));

        panelLoadCSV = new JPanel();
        panelLoadCSV.setBackground(color.bgMain);
        panelLoadCSV.setPreferredSize(new Dimension(width - 50, height - 50));
        panelLoadCSV.setLayout(new FlowLayout());
        loadFile = new JButton("Add file CSV to database");
        loadFile.setBackground(color.bgButton);

        loadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                csvFile = textField.getText();
                List<String[]> listSensorValue = ReadCSV.readCSV(csvFile);
                datebese.insertSensors(listSensorValue);
            }
        });

        textField = new JTextField("Path to file CSV C:\\a.csv");
        textField.setPreferredSize(new Dimension(width - 80, 30));
        panelLoadCSV.add(panelBreak);
        panelLoadCSV.add(textField);
        panelLoadCSV.add(loadFile);
    }

    private void setSecondPanel(){
        secondPanel = new JPanel();
        secondPanel.setBackground(color.bgMain);
        secondPanel.setPreferredSize(new Dimension(width-50, height-50));
        pointOnMap = datebese.getPointFromMap(5);

        image = new ImageIcon("map.jpg");
        label = new JLabel("", image, JLabel.CENTER);

        sPanelImage = new IconPanel(image.getImage());
        secondPanel.add(sPanelImage);
    }

    private void setThirdPanel(){
        thirdPanel = new JPanel();
        thirdPanel.setBackground(color.bgMain);
        thirdPanel.setPreferredSize(new Dimension(width-50, height-50));

        thirdBrakeTopPanel = new JPanel();
        thirdBrakeTopPanel.setBackground(color.bgMain);
        thirdBrakeTopPanel.setPreferredSize(new Dimension(width-50, 50));
        thirdPanel.add(thirdBrakeTopPanel);

        thirdInformaitonPanel = new JPanel();
        thirdInformaitonPanel.setBackground(color.bgMain);
        thirdInformaitonPanel.setPreferredSize(new Dimension(width - 50, height - 50));
        thirdPanel.add(thirdInformaitonPanel);


        thirdInformaitonPanel.add(thirdLabelAverage);
        thirdInformaitonPanel.add(thirdLabelVariance);
        thirdInformaitonPanel.add(thirdLabelFrequency);
        thirdInformaitonPanel.add(thirdLabelDominant);
        thirdInformaitonPanel.add(thirdLabelMedian);
        thirdInformaitonPanel.add(thirdLabelQuantile);
        thirdInformaitonPanel.add(thirdLabelHeave);
        thirdInformaitonPanel.add(thirdLabelStandardDevation);
        thirdInformaitonPanel.add(thirdLabelKurtosis);


        thirdLabelAverage.setPreferredSize(new Dimension(width - 5, 22));
        thirdLabelVariance.setPreferredSize(new Dimension(width - 5, 22));
        thirdLabelFrequency.setPreferredSize(new Dimension(width - 5, 22));
        thirdLabelDominant.setPreferredSize(new Dimension(width - 5, 22));
        thirdLabelMedian.setPreferredSize(new Dimension(width - 5, 22));
        thirdLabelQuantile.setPreferredSize(new Dimension(width - 5, 22));
        thirdLabelHeave.setPreferredSize(new Dimension(width - 5, 22));
        thirdLabelStandardDevation.setPreferredSize(new Dimension(width - 5, 22));
        thirdLabelKurtosis.setPreferredSize(new Dimension(width - 5, 22));
    }







    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Sensors csv = new Sensors();
            }
        });
    }
}
