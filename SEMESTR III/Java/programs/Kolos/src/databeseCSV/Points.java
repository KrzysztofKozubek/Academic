package databeseCSV;

/**
 * Created by Kris on 2015-05-08.
 */
public class Points {

    private Point[][] points;
    private int precisionX;
    private int precisionY;

    Points(){
        this.precisionX = 360;
        this.precisionY = 180;
        points = new Point[precisionX + 1][];
        for(int i = 0; i <= precisionX; i++){
            points[i] = new Point[precisionY + 1];
            for(int j = 0; j < precisionY + 1; j++)
                points[i][j] = new Point();
        }
    }

    Points(int precisionX, int precisionY){
        this.precisionX = precisionX;
        this.precisionY = precisionY;
        points = new Point[precisionX + 1][];
        for(int i = 0; i <= precisionX; i++){
            points[i] = new Point[precisionY + 1];
            for(int j = 0; j < precisionY + 1; j++)
                points[i][j] = new Point();
        }
    }



    public boolean havePoint(int x, int y, Point[] points){
        for(int i=0;i<points.length;i++){
            if(x < points[i].getX() + 3 && x > points[i].getX() - 3){
                if(y < points[i].getY() + 3 && y < points[i].getY() - 3)
                    return true;
            }
        }
        return false;
    }

    public void fillUp(int widthImg, int heightImg){

        int halfPrecisionY = precisionY / 2;
        int halfPrecisionX = precisionX / 2;

        for(int i = 0; i <= precisionX; i++){
            for(int j = 0; j <= precisionY; j++) {
                //System.out.println(i + " " + j + " " + precisionX + " " + precisionY);
                points[i][precisionY - j].setPoint((i) * widthImg / precisionX, (j) * heightImg / precisionY);
            }
        }
    }

    public int getX(int x, int y){return points[(x+precisionX/2)%precisionX][(y+precisionY/2)%precisionY].getX();}
    public int getY(int x, int y){return points[(x+precisionX/2)%precisionX][(y+precisionY/2)%precisionY].getY();}
}

class Point{

    public double x;
    public double y;

    public Point(){setPoint(0, 0);}

    public Point(double x, double y){setPoint(x, y);}

    public void setPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public int getX(){return (int)x;}

    public int getY(){return (int)y;}

}