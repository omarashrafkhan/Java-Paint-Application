package Menubar;

import Toplevelclasses.ActiveButton;

import java.awt.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class FileButton extends ActiveButton {
    private File file;

    public FileButton(int x, int y, int width, int height, String title, Color c_depressed, Color c_pressed, File file) {
        super(x, y, width, height, title, c_depressed, c_pressed);

        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void paintFileButton(Graphics g) {
        g.setColor(c_current);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawString(title, x + 5, y + 15);
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
