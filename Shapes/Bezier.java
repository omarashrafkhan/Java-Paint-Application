package Shapes;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Bezier extends Shape {


    ArrayList<Point> points;
    private Point end, control, cubicControl;

    public Bezier(int x, int y, Color stroke, int strokeVal) {


        this.x = x;
        this.y = y;
        this.stroke = stroke;
        this.strokeVal = strokeVal;
        points = new ArrayList<>();

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
        if (end == null) {


            end = e.getPoint();
        } else if (control == null) {

            control = e.getPoint();
//
        } else if (cubicControl == null) {

            cubicControl = e.getPoint();
//
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


    public void drawQuadratic(Graphics g) {
        points.clear();
        for (double t = 0; t < 1; t += 0.001) {
            int x1 = quadratic(x, control.x, end.x, t);
            int y1 = quadratic(y, control.y, end.y, t);

            points.add(new Point(x1, y1));
        }
        for (int i = 0; i < points.size() - 1; i++) {
            g.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
        }
    }


    private void drawCubic(Graphics g) {
        points.clear();
        for (double t = 0; t < 1; t += 0.001) {
            int x1 = cubic(x, control.x, cubicControl.x, end.x, t);
            int y1 = cubic(y, control.y, cubicControl.y, end.y, t);

            points.add(new Point(x1, y1));
        }
        for (int i = 0; i < points.size() - 1; i++) {
            g.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(stroke);
        g2d.setStroke(new BasicStroke(strokeVal, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));


//      temp line
        if (x != -1 && end == null) {

            PointerInfo pointerInfo = MouseInfo.getPointerInfo();
            drawLine(g2d, x, y, pointerInfo.getLocation().x, pointerInfo.getLocation().y - 22);
        }


//      after end is initialized
        if (x != -1 && end != null && control == null) {
            drawLine(g2d, x, y, end.x, end.y);
        }


        if (x != -1 && end != null && control != null && cubicControl == null) {
            drawQuadratic(g2d);
        } else if (cubicControl != null) {
            drawCubic(g2d);
        }


        g2d.dispose();
    }


}
