package Menubar;

import Toplevelclasses.ActiveButton;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class SubMenu extends ActiveButton {


    public SubMenu(String title, int x, int y, int width, int height) {
        super(x, y, width, height, title, Color.green, Color.BLACK);


    }


    public void paintMenuItem(Graphics g, Menu menu) {
        for (SubMenu submenu : menu.getSubMenus()) {
            // outline
            g.setColor(Color.black);
            g.drawRect(submenu.x, submenu.y, submenu.width, submenu.height);
            // Rectangle

            g.setColor(submenu.c_current);
            g.fillRect(submenu.x, submenu.y, submenu.width, submenu.height);
            // title/String
            g.setColor(Color.white);
            g.drawString(submenu.title, submenu.x + 1, submenu.y + 13);
        }

    }

    public boolean IsClicked(int x, int y) {
        if (active && x > this.x && x < this.x + width && y > this.y && y < this.y + height) {

            pressed = true;
            c_current = c_pressed;
            active = false;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {


                    pressed = false;
                    c_current = c_depressed;
                    active = true;
                    timer.cancel();
                }
            }, 200);
            return true;
        }
        return false;
    }

}
