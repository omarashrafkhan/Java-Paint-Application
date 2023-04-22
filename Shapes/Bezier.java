package Shapes;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Bezier extends Shape {



    private Point end, control, cubicControl;

    public Bezier(int x, int y, Color stroke, int strokeVal) {

        // start = new Point(x, y);

        this.x = x;
        this.y = y;
        this.stroke = stroke;
        this.strokeVal = strokeVal;
    }


    public Point getCubicControl() {
        return cubicControl;
    }


    private int lerp(int a, int b, double t) {
        return (int) (a + t * (b - a));
    }

    public int quadratic(int a, int b, int c, double t) {
        return lerp(lerp(a, b, t), lerp(b, c, t), t);
    }

    public int cubic(int a, int b, int c, int d, double t) {
        return lerp(quadratic(a, b, c, t), quadratic(b, c, d, t), t);
    }


    public void mousePress(MouseEvent e) {
//        if (start == null) {
//            start = e.getPoint();
        if (end == null) {


            end = e.getPoint();
        } else if (control == null) {

            control = new Point((int) (x + (end.x - x) * 0.25), (int) (y + (end.y - y) * 0.25));
        } else if (cubicControl == null) {

            cubicControl = new Point((int) (x + (end.x - x) * 0.75), (int) (y + (end.y - y) * 0.75));
        }
    }


    public void mouseDrag(MouseEvent e) {
        if (cubicControl == null) {

            control.x += e.getX() - control.x;
            control.y += e.getY() - control.y;
        } else {
            cubicControl.x += e.getX() - cubicControl.x;
            cubicControl.y += e.getY() - cubicControl.y;

        }
    }


    private void drawLine(Graphics g, int x, int y, int a, int b) {
        g.drawLine(x, y, a, b);
    }


    public void drawQuadratic(Graphics g){
        for (double t = 0; t < 1; t += 0.01) {
            int x1 = quadratic(x, control.x, end.x, t);
            int y1 = quadratic(y, control.y, end.y, t);
            int x2 = quadratic(x, control.x, end.x, t + 0.01);
            int y2 = quadratic(y, control.y, end.y, t + 0.01);
            g.drawLine(x1, y1, x2, y2);
        }
    }



    private void drawCubic(Graphics g){
        for (double t = 0; t < 1; t += 0.01) {
            int x1 = cubic(x, control.x, cubicControl.x, end.x, t);
            int y1 = cubic(y, control.y, cubicControl.y, end.y, t);
            int x2 = cubic(x, control.x, cubicControl.x, end.x, t + 0.01);
            int y2 = cubic(y, control.y, cubicControl.y, end.y, t + 0.01);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(stroke);
        g2d.setStroke(new BasicStroke(strokeVal, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (x != -1 && end == null) {

            PointerInfo pointerInfo = MouseInfo.getPointerInfo();
            drawLine(g2d, x, y, pointerInfo.getLocation().x, pointerInfo.getLocation().y - 22);
        }


        if (x != -1 && end != null && control == null) {
            drawLine(g2d, x, y, end.x, end.y);
        }


        if (x != -1 && end != null && control != null && cubicControl == null) {
            drawQuadratic(g2d);
//            for (double t = 0; t < 1; t += 0.01) {
//                int x1 = quadratic(x, control.x, end.x, t);
//                int y1 = quadratic(y, control.y, end.y, t);
//                int x2 = quadratic(x, control.x, end.x, t + 0.01);
//                int y2 = quadratic(y, control.y, end.y, t + 0.01);
//                g2d.drawLine(x1, y1, x2, y2);
//            }
        } else if (cubicControl != null) {

            drawCubic(g2d);
//            for (double t = 0; t < 1; t += 0.01) {
//                int x1 = cubic(x, control.x, cubicControl.x, end.x, t);
//                int y1 = cubic(y, control.y, cubicControl.y, end.y, t);
//                int x2 = cubic(x, control.x, cubicControl.x, end.x, t + 0.01);
//                int y2 = cubic(y, control.y, cubicControl.y, end.y, t + 0.01);
//                g2d.drawLine(x1, y1, x2, y2);
//            }
        }


        g2d.dispose();
    }



}
