package Menubar;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import Toplevelclasses.*;
public class Menu extends ToggleButton {

    private ArrayList<SubMenu> subMenus;

    public ArrayList<SubMenu> getSubMenus() {
        return subMenus;
    }

    public Menu(String title, ArrayList<SubMenu> subMenus, int x, int y) {

        super(x, y, 30, 20, Color.BLACK, Color.blue, title);

        this.subMenus = subMenus;

    }

    public void paintMenu(Graphics g) {
        g.setColor(c_current);
        g.fillRect(x, y, width, height);
        g.setColor(Color.white);
        g.drawString(title, x + 5, y + 13);

        if (pressed) {
            for (SubMenu item : subMenus) {
                item.paintMenuItem(g, this);
            }

        }
    }

    @Override
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
