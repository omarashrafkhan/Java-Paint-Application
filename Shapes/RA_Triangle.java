package Shapes;

import java.awt.*;

public class RA_Triangle extends Shape {

    private Point start;
    private Point end;
    private Point point2;
    private Point point3;
   // private int[] xPoints;
   // private int[] yPoints;

    public RA_Triangle(int x, int y, Color fill, Color stroke, int strokeVal) {
        start = end = point2 = point3 =new Point(x, y);
       // end = new Point(x, y);
        this.fill = fill;
        this.stroke = stroke;
        this.strokeVal = strokeVal;
        xPoints = new int[3];
        yPoints = new int[3];


    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    @Override
    public void draw(Graphics g) {
        int x = end.x - start.x;
        int y = end.y - start.y;
        point2 = new Point(end.x, start.y);
        point3 = new Point(end.x, end.y);

        xPoints[0] = start.x;
        xPoints[1] = point2.x;
        xPoints[2] = point3.x;
        yPoints[0] = start.y;
        yPoints[1] = point2.y;
        yPoints[2] = point3.y;


        if (fill != null) {

            g.setColor(fill);
            g.fillPolygon(xPoints, yPoints, 3);
        }

        if (strokeVal > 0) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(stroke);
            g2d.setStroke(new BasicStroke(strokeVal, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));


            g2d.drawPolygon(xPoints, yPoints, 3);
            g2d.dispose();
        }


    }


}
