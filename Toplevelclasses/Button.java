package Toplevelclasses;

import java.awt.*;
import java.io.Serializable;


public abstract class Button implements Serializable {
    public int x;
    public int y;
    public int width;
    public int height;
    public Image image_depressed;
    public Image image_pressed;
    public Image current_image;
    public Color c_depressed;
    public Color c_pressed;
    public Color c_current;
    public boolean pressed;
    public boolean active;
    public String title;

    public Button() {

    }


    //Specifically for layers
    public Button(int x, int y, int width, int height, Color c_depressed, Color c_pressed, String title) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.title = title;
        this.c_depressed = c_depressed;
        this.c_pressed = c_pressed;
        c_current = c_depressed;

        pressed = false;
    }




    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract boolean IsClicked(int x, int y);

    public boolean IsHovered(int x, int y) {
        return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
    }

}
