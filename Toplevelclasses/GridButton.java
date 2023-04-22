package Toplevelclasses;

import ColorBar.ColorToolBar;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GridButton extends Button {

    public int val;

    public GridButton() {
        x = ColorToolBar.getStroke().x + 200;
        y = ColorToolBar.getStroke().y;
        width = ColorToolBar.getStroke().width;
        height = ColorToolBar.getStroke().height;
        //c_current = new Color(255, 200, 100);

        c_depressed= new Color(255, 200, 100);
        c_pressed = new Color(173, 129, 49);
        c_current = c_depressed;
        active =true;
        val = 0;
        title = "GRID";
    }


    public void paintGridButton(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        g.setColor(c_current);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawString(title, x + width / 2 - fm.stringWidth(title) / 2, y+50);
        if (val == 0) {
            g.drawString("OFF", x + width / 2 - fm.stringWidth("OFF") / 2, y + 20);
        } else {

            g.drawString(String.valueOf(val), x + width / 2 - fm.stringWidth(String.valueOf(val)) / 2, y + 20);
        }

    }

    @Override
    public boolean IsClicked(int x, int y) {
        if (active && x > this.x && x < this.x + width && y > this.y && y < this.y + height) {
            c_current = c_pressed;
            active = false;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {

                    c_current = c_depressed;
                    active = true;
                    timer.cancel();
                }
            }, 200);
            if (val == 0) {
                val = 2;
            } else {
                val = (int) Math.pow(2, Math.ceil(Math.log(val) / Math.log(2)) + 1);
            }
            if (val > 64) val = 0;


        }
        return false;
    }


}
