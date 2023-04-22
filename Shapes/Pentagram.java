package Shapes;

import java.awt.*;

public class Pentagram extends Shape {

   // public int[] xPoints;
   // public int[] yPoints;



            //public int radius;
    //public double angle;

    public Pentagram(int x, int y, int radius, Color fill, Color stroke, int strokeVal) {
        this.x = x;
        this.y = y ;

        //this.centerX = x;
        //this.centerY = y;
        this.fill = fill;
        this.stroke = stroke;
        this.strokeVal = strokeVal;
        this.radius = radius;
        this.angle = 0;
        xPoints = new int[10];
        yPoints = new int [10];


    }

    @Override
    public void draw(Graphics g) {

        for (int i = 0; i < 10; i++) {
            double angle = 2 * Math.PI * i / 10 - Math.PI / 2;
            if (i % 2 == 0) {
                xPoints[i] = x + (int) (radius * Math.sin(angle));
                yPoints[i] = y - (int) (radius * Math.cos(angle));
            } else {
                xPoints[i] = x + (int) (radius * Math.sin(angle) * 0.5);
                yPoints[i] = y - (int) (radius * Math.cos(angle) * 0.5);
            }
        }

        rotateStar(angle);

        if (fill != null) {
            g.setColor(fill);
            g.fillPolygon(xPoints, yPoints, 10);
        }

        if (strokeVal > 0) {

            if (strokeVal > 0) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(stroke);
                g2d.setStroke(new BasicStroke(strokeVal+radius/6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
                // Draw lines joining the specified points

                g2d.drawLine(xPoints[6], yPoints[6], xPoints[0], yPoints[0]); // 7th point to 1st point
                g2d.drawLine(xPoints[0], yPoints[0], xPoints[4], yPoints[4]); // 1st point to 5th point
                g2d.drawLine(xPoints[4], yPoints[4], xPoints[8], yPoints[8]); // 5th point to 9th point
                g2d.drawLine(xPoints[8], yPoints[8], xPoints[2], yPoints[2]); // 9th point to 3rd point
                g2d.drawLine(xPoints[2], yPoints[2], xPoints[6], yPoints[6]); // 3rd point to 7th point
                g2d.dispose();
            }




        }
    }




    public void rotateStar(double angle) {



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
