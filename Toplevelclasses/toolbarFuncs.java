package Toplevelclasses;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

public interface toolbarFuncs {
    public void mousePress(MouseEvent e);
    public void keyPress(KeyEvent e) throws FileNotFoundException;

    public void mouseRelease(MouseEvent e);

    public void mouseDrag(MouseEvent e);

    public void mouseMove(MouseEvent e);
}
