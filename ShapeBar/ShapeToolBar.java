package ShapeBar;

import Toplevelclasses.ToggleButton;
import Toplevelclasses.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ShapeToolBar extends Toolbar {
   private ArrayList<ToggleButton> shapebuttons ;
   private JPanel panel;
   private ArrayList<ImageIcon> images ;
   private static ToggleButton activeShape;

    public static void setActiveShape(ToggleButton activeShape) {
        ShapeToolBar.activeShape = activeShape;
    }

    public static ToggleButton getActiveShape() {
        return activeShape;
    }

    public ShapeToolBar(JPanel panel) {




        super(new Point(0, 0), new Dimension(100, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()),
                Color.lightGray, "Shapes Tool Bar");
        this.panel = panel;
        initialize();
    }

    public void initialize() {

        shapebuttons  = new ArrayList<>(8);
        images = new ArrayList<>();

        for (String shapeName : new String[] { "Circle", "EQ triangle", "Hexagon", "pentagram", "RA triangle",
                "Rectangle", "FreeDraw", "Bezier" }) {
            String pressedFilename = String.format("..\\omar_khan_26985_Assignment4\\icons\\%s pressed.png", shapeName);
            String unpressedFilename = String.format("..\\omar_khan_26985_Assignment4\\icons\\%s unpressed.png", shapeName);
            images.add(new ImageIcon(pressedFilename));
            images.add(new ImageIcon(unpressedFilename));

        }

        int y = 130;
        for (int i = 0; i < 16; i += 2) {
            ImageIcon icon1 = images.get(i + 1);
            ImageIcon icon2 = images.get(i);
            shapebuttons.add(new ToggleButton(28, y, 50, 50,
                    icon1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH),
                    icon2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH),
                    icon2.getDescription().substring(icon2.getDescription().lastIndexOf("\\") + 1)));
            y += 80;
        }




    }


    @Override
    public void paint(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(startPosition.x, startPosition.y, dimension.width, dimension.height);

        for (ToggleButton shapeButton : shapebuttons) {

            g.drawImage(shapeButton.current_image, shapeButton.x, shapeButton.y, panel);
        }
    }


    @Override
    public void mousePress(MouseEvent e) {
        for (ToggleButton shapeButton : shapebuttons) {

            if (shapeButton.IsClicked(e.getX(), e.getY())) {
                if (shapeButton.pressed) {
                    activeShape = shapeButton;
                    for (ToggleButton shapeButton1 : shapebuttons) {
                        if (shapeButton1 != shapeButton) {
                            shapeButton1.pressed = false;
                            shapeButton1.current_image = shapeButton1.image_depressed;
                        }
                    }
                }
            }

        }
    }

    @Override
    public void keyPress(KeyEvent e) {

    }

    @Override
    public void mouseRelease(MouseEvent e) {

    }

    @Override
    public void mouseDrag(MouseEvent e) {

    }
    @Override
    public void mouseMove(MouseEvent e){

        for(ToggleButton shapeButton : shapebuttons){

            if(shapeButton.IsHovered(e.getX(),e.getY())){
                String editTitle = shapeButton.title.split("pressed",0)[0];
                tooltip.setMessage(editTitle);
                tooltip.active = true; break;

            }
            else  {

                tooltip.active =false;

            }
        }
    }

}
