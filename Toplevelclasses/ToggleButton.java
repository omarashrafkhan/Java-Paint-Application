package Toplevelclasses;

import java.awt.*;
import java.io.Serializable;

public class ToggleButton extends Button implements Serializable {




    public ToggleButton(int x, int y, int width, int height, Image i_depressed, Image i_pressed, String title) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.title = title;
        image_depressed = i_depressed;
        image_pressed = i_pressed;
        current_image = i_depressed;
        pressed = false;
    }


    public ToggleButton(int x, int y, int width, int height, Color c_depressed, Color c_pressed, String title) {
        super(x,y,width,height,c_depressed,c_pressed,title);

    }

    public ToggleButton(int x, int y, int width, int height, String title, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.title = title;
        this.c_current = color;
        pressed = false;
    }

    public void Color_paint(Graphics g) {

    }

    public void Image_paint(Graphics g) {

    }


    public Image GetImage() {
        return current_image;
    }

    public Boolean IsPressed() {
        return pressed;
    }

    public void SetPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean IsClicked(int x, int y) {
        if (x > this.x && x < this.x + width && y > this.y && y < this.y + height) {
            pressed = !pressed;
            if (pressed) {
                current_image = image_pressed;
            } else {
                current_image = image_depressed;
            }
            return true;
        }
        return false;
    }


}
