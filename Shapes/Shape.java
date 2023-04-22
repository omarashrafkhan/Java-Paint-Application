package Shapes;

import java.awt.*;
import java.io.Serializable;


public abstract class Shape implements Serializable {
    protected int x, y, strokeVal, radius;
    protected Color fill, stroke;
    protected double angle;
    protected int[] xPoints;
    protected int[] yPoints;

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void draw(Graphics g);


}
