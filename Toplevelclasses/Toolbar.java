package Toplevelclasses;

import ToolTipClass.Tooltip;

import java.awt.*;
import java.awt.event.MouseEvent;


public abstract class Toolbar implements toolbarFuncs {
    public Point startPosition;
    public Dimension dimension;
    public Color backgroundColor;

    public String title;

    public final int s_width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public final int s_height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public Tooltip tooltip;

    public abstract void paint(Graphics g);
    public abstract void initialize();

    public boolean isWithinBounds(int x, int y) {
        return x> startPosition.x && x<startPosition.x+dimension.width && y>startPosition.y && y< startPosition.y+dimension.height;
    }

    public Toolbar(Point startPosition, Dimension dimension, Color backgroundColor, String title) {
        this.startPosition = startPosition;
        this.dimension = dimension;
        this.backgroundColor = backgroundColor;
        this.title = title;
        tooltip = Tooltip.getInstance();
    }

    
}
