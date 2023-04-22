package Toplevelclasses;

import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;


public class ActiveButton extends Button{


    public ActiveButton(int x, int y, int width, int height, Image i_depressed, Image i_pressed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        image_depressed = i_depressed;
        image_pressed = i_pressed;
        current_image = i_depressed;
        active = true;
    }

    public ActiveButton(int x, int y, int width, int height, Image i_depressed, Image i_pressed, String title) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.title = title;
        image_depressed = i_depressed;
        image_pressed = i_pressed;
        current_image = i_depressed;
        active = true;
    }

    public ActiveButton(int x, int y, int width, int height, String title, Color c_depressed, Color c_pressed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.c_depressed = c_depressed;
        this.c_pressed = c_pressed;
        this.c_current = c_depressed;
        this.title = title;
        active = true;
    }

    public ActiveButton(int x, int y, int width, int height, String title) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.title = title;
        active = true;
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

    // @Override
    public boolean IsClicked(int x, int y) {
        if (active && x > this.x && x < this.x + width && y > this.y && y < this.y + height) {
            pressed = true;
            current_image = image_pressed;
            active = false;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    pressed = false;
                    current_image = image_depressed;
                    active = true;
                    timer.cancel();
                }
            }, 200);
            return true;
        }
        return false;
    }




}
