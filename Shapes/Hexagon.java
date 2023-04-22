package Shapes;

import java.awt.*;

public class Hexagon extends Shape{

    //private int[] xPoints;
   // private int[] yPoints;
    //private int radius;

    //private double angle;

//    public void setAngle(double angle) {
//          this.angle = angle;
//    }

//    public void setRadius(int radius) {
//        this.radius = radius;
//    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    private boolean dragging;

    public Hexagon(int x, int y, Color fill, Color stroke, int strokeVal){
        this.x=x;
        this.y = y;
        this.fill= fill;
        this.stroke = stroke;
        this.strokeVal = strokeVal;
        xPoints = new int[6];
        yPoints = new int[6];
        angle = 0 ;
    }



    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < 6; i++) {
            xPoints[i] = (int) (x + radius * Math.cos(i * Math.PI / 3));
            yPoints[i] = (int) (y + radius * Math.sin(i * Math.PI / 3));
        }

        rotateHexagon(angle);



        if(fill != null){
            g.setColor(fill);
            g.fillPolygon(xPoints,yPoints,6);
        }

        if(strokeVal >0)
        {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(stroke);
            g2d.setStroke(new BasicStroke(strokeVal));


            g2d.drawPolygon(xPoints, yPoints, 6);
            g2d.dispose();
        }





    }


    public void rotateHexagon(double angle) {



        // calculate the rotation matrix
        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);
        double[][] rotationMatrix = {
                {cosTheta, -sinTheta, 0},
                {sinTheta, cosTheta, 0},
                {0, 0, 1}
        };

        // apply the rotation matrix to each point
        for (int i = 0; i < xPoints.length; i++) {
            // translate the point to the origin
            double[][] point = {
                    {xPoints[i] - x},
                    {yPoints[i] - y},
                    {1}
            };

            // apply the rotation matrix
            double[][] rotatedPoint = multiplyMatrices(rotationMatrix, point);

            // translate the point back to its original position
            xPoints[i]= (int) (rotatedPoint[0][0] + x);
            yPoints[i] = (int) (rotatedPoint[1][0] + y);
        }
    }
    public double[][] multiplyMatrices(double[][] A, double[][] B) {
        int m = A.length;
        int n = B[0].length;
        double[][] C = new double[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < A[0].length; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }


}
