package Shapes;

import java.awt.*;

public class Rectangle extends Shape {

    private int width;
    private int height;

    public Rectangle(int x, int y, Color fill, Color stroke, int strokeVal, int width, int height) {
        this.x = x;
        this.y = y;
        this.fill = fill;
        this.stroke =stroke;
        this.strokeVal =strokeVal;
        this.width = width;
        this.height = height;
    }

    // draw method to draw the rectangle using Graphics object
    @Override
    public void draw(Graphics g) {
        // draw fill color
        if (fill != null) {
            g.setColor(fill);
            g.fillRect(x, y, width, height);
        }
        // draw stroke
        if (strokeVal > 0) {
            Graphics2D g2d = (Graphics2D) g.create();
            //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(stroke);
            g2d.setStroke(new BasicStroke(strokeVal));
            g2d.drawRect(x, y, width, height);
            g2d.dispose();
        }
    }


    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }




}
