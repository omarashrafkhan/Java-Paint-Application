package Shapes;

import java.awt.*;

public class Circle extends Shape {

    //private int radius; // radius of circle
    //public boolean resizing = false; // flag to indicate if circle is being resized


//    public void setRadius(int radius) {
//        this.radius = radius;
//    }


    public Circle(int x, int y, Color fill, Color stroke, int strokeVal) {
        this.x = x;
        this.y = y;
        this.radius = 0; // set initial radius to zero
        this.fill = fill;
        this.stroke = stroke;
        this.strokeVal = strokeVal;
    }

    // draw method to draw the circle using Graphics object
    @Override
    public void draw(Graphics g) {
        if(fill != null){
            g.setColor(fill);
            g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        }

        if(strokeVal>0){

            Graphics2D g2 = (Graphics2D) g.create(); // create new Graphics2D object
            g2.setStroke(new BasicStroke(strokeVal));
            g2.setColor(stroke);
            g2.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
            g2.dispose();
        }
    }

    // details method to return details of the circle as a string

}
