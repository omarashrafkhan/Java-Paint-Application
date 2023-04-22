package ToolTipClass;

import java.awt.*;


public class Tooltip implements ITooltip {
    private static final Tooltip instance = new Tooltip();
    public boolean active;
   private  int x;
    private int y;
    private String message;
    private int width;

    // private constructor to prevent instantiation from outside
    private Tooltip() {
        active = false;
    }

    // get instance of Tooltip class
    public static Tooltip getInstance() {
        return instance;
    }

    // set message to be displayed in tooltip
    public void setMessage(String message) {
        this.message = message;
    }

    public  void setWidth(int width){
        this.width = width;
    }

    public void showTooltip(Graphics graphics, int x, int y) {
        int height = 30;
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(message);

        // adjust tooltip width to fit text
//        if (textWidth > width - 20) {
//        }
            width = textWidth + 20;
        graphics.setColor(new Color(0, 0, 0, 50));

        graphics.fillRoundRect(x + 2, y + 2, width, height, 10, 10);
        // draw tooltip box
        graphics.setColor(Color.WHITE);

        graphics.fillRoundRect(x, y, width, height, 10, 10);


        //graphics.drawRect(x, y, width, height);
        graphics.setColor(new Color(0, 0, 0));

        // draw message in tooltip
        graphics.drawString(message, x + width / 2 - textWidth / 2, y + 20);
    }
}



