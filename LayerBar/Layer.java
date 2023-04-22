package LayerBar;

import Shapes.Shape;
import Toplevelclasses.ToggleButton;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Layer extends ToggleButton  {

   private ArrayList<Shape> shapesEnque;
   private ArrayList<Shape> shapesDeque;

    public ArrayList<Shape> getShapesEnque() {
        return shapesEnque;
    }

    public ArrayList<Shape> getShapesDeque() {
        return shapesDeque;
    }



    public Layer(int x, int y, int width, int height, String title) {

        super(x,y,width,height, new Color(100, 200, 255), new Color(255, 100, 100), title);
        shapesEnque = new ArrayList<>();
        shapesDeque = new ArrayList<>();
    }





    public void paint(Graphics g) {
        g.setColor(c_current);

        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawString(title, x + 8, y + 15);
    }


    public boolean IsClicked(int x, int y) {

        if (x > this.x && x < this.x + width && y > this.y && y < this.y + height) {
            pressed = !pressed;
            if (pressed) {
                c_current = c_pressed;
            } else {
                c_current = c_depressed;
            }
            return true;
        }
        return false;
    }
}
