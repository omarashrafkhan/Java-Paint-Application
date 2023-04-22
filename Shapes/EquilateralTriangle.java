package Shapes;

import java.awt.*;

public class EquilateralTriangle extends Shape {
    //public double angle;
   // int[] xPoints;
    //int[] yPoints;
    //private int radius;




    public EquilateralTriangle(int x, int y, int radius, Color fill, Color stroke, int strokeVal) {
        this.radius = radius;
        this.fill = fill;
        this.stroke = stroke;
        this.strokeVal = strokeVal;
        this.x = x;
        this.y = y - radius;
        this.angle = 0;

    }

//    public void setRadius(int radius) {
//        this.radius = radius;
//    }
//
    @Override
    public void draw(Graphics g) {



        // calculate the coordinates of the three vertices of the triangle
        int x1 = x;
        int y1 = y - radius;
        int x2 = (int) (x - radius * Math.sin(Math.PI / 3));
        int y2 = (int) (y + radius * Math.cos(Math.PI / 3));
        int x3 = (int) (x + radius * Math.sin(Math.PI / 3));
        int y3 = y2;

        // rotate the triangle
        xPoints = new int[]{x1, x2, x3};
        yPoints = new int[]{y1, y2, y3};
        rotateEquilateralTriangle(angle);

        if (fill != null) {
            g.setColor(fill);
            g.fillPolygon(xPoints, yPoints, 3);


        }

        if(strokeVal >0)
        {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(stroke);
            g2d.setStroke(new BasicStroke(strokeVal));


            g2d.drawPolygon(xPoints, yPoints, 3);
            g2d.dispose();
        }




    }



    public void rotateEquilateralTriangle(double angle) {
        // calculate the center of the triangle
        int centerX = (xPoints[0] + xPoints[1] + xPoints[2]) / 3;
        int centerY = (yPoints[0] + yPoints[1] + yPoints[2]) / 3;

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
                    {xPoints[i] - centerX},
                    {yPoints[i] - centerY},
                    {1}
            };

            // apply the rotation matrix
            double[][] rotatedPoint = multiplyMatrices(rotationMatrix, point);

            // translate the point back to its original position
            xPoints[i] = (int) (rotatedPoint[0][0] + centerX);
            yPoints[i] = (int) (rotatedPoint[1][0] + centerY);
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