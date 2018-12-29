import javax.swing.*;
import java.awt.*;

class OvalIcon implements Icon {

    public int size;
    public Color color;

    OvalIcon(Color c, int s) {

        color = c;
        size = s;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {

        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    public int getIconWidth() {
        return size;
    }

    public int getIconHeight() {
        return size;
    }

}