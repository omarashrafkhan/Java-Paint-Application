package ColorBar;

import ShapeBar.ShapeToolBar;
import Toplevelclasses.ActiveButton;
import Toplevelclasses.ToggleButton;
import Toplevelclasses.Toolbar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class ColorToolBar extends Toolbar {

    private static ToggleButton fill;
    private static ToggleButton stroke;
    private static int strokeVal = 0;
    private ActiveButton removeFill;
    private ArrayList<ToggleButton> colorButtons;
    private ArrayList<ToggleButton> colorButtons1;
    private ToggleButton editColors;
    private ActiveButton strokePlus;
    private ActiveButton strokeMinus;
    private ArrayList<ToggleButton> fillStroke;
    private Color activeColor = Color.black;
    private GradientWindow gradientWindow;

    public ColorToolBar() {
        super(new Point(0, 20), new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 80), new Color(150, 150, 150), "Color Tool Bar");
        initialize();

    }

    public static ToggleButton getFill() {
        return fill;
    }

    public static ToggleButton getStroke() {
        return stroke;
    }

    public static int getStrokeVal() {
        return strokeVal;
    }

    public void initialize() {

        colorButtons = createRandomColorButtons(120, 35, 2, 10);

        gradientWindow = GradientWindow.getInstance();


        editColors = new ToggleButton(580, 30, 50, 60, Color.black, new Color(90, 90, 90), "Edit Colors");


        colorButtons1 = createRandomColorButtons(editColors.x + 280, editColors.y + 255, 5, 2);


        fill = new ToggleButton(430, 30, 50, 60, "Fill", activeColor);
        stroke = new ToggleButton(490, 30, 50, 60, "Stroke", activeColor);
        strokePlus = new ActiveButton(stroke.x + stroke.width + 5, stroke.y, 15, 15, "+", Color.black, Color.blue);
        strokeMinus = new ActiveButton(stroke.x + stroke.width + 5, stroke.y + 20, 15, 15, "-", Color.black, Color.blue);
        removeFill = new ActiveButton(635, 30, 50, 60, "Remove Fill", new Color(138, 83, 19), new Color(236, 137, 8));
        fillStroke = new ArrayList<>();


        fillStroke.add(fill);
        fillStroke.add(stroke);

    }


    public void paintColorButtons(ArrayList<ToggleButton> buttons, Graphics g, int xOffset, int yOffset) {
        g.setColor(Color.white);
        if (buttons == colorButtons1) {
            g.fillRect(xOffset, yOffset, 60, 157);
        } else g.fillRect(xOffset, yOffset, 310, 60);


        for (ToggleButton button : buttons) {
            if (button.pressed) {
                buttonSelected(button, g);
            }

            g.setColor(button.c_current);
            g.fillRect(button.x, button.y, button.width, button.height);
        }
    }


    public void buttonSelected(ToggleButton button, Graphics g) {

        g.setColor(Color.black);
        g.drawRect(button.getX() - 2, button.getY() - 2, button.getWidth() + 4, button.getHeight() + 4);
    }

    public void paintEditColors(Graphics g) {

        g.setColor(editColors.c_current);
        g.fillRect(editColors.x, editColors.y, editColors.width, editColors.height);
        g.setColor(Color.white);
        g.drawString(editColors.title.substring(0, 4), editColors.x + 14, editColors.y + 17);
        g.drawString(editColors.title.substring(4), editColors.x + 5, editColors.y + 30);
    }

    public void paint(Graphics g) {
        g.setColor(this.backgroundColor);
        g.fillRect(super.startPosition.x, super.startPosition.y, dimension.width, dimension.height);


        paintColorButtons(colorButtons, g, 110, 30);


        paintFillStroke(g);

        paintEditColors(g);
        if (editColors.pressed) {
            if (ShapeToolBar.getActiveShape() != null) {
                ShapeToolBar.getActiveShape().current_image = ShapeToolBar.getActiveShape().image_depressed;
                ShapeToolBar.setActiveShape(null);
            }
            buttonSelected(editColors, g);
            gradientWindow.paintGradient(g);


            paintColorButtons(colorButtons1, g, editColors.x + 275, editColors.y + 245);


        }


        if (fill.pressed) {
            buttonSelected(fill, g);
        }
        if (stroke.pressed) {
            buttonSelected(stroke, g);
        }

    }

    public ArrayList<ToggleButton> createRandomColorButtons(int startX, int startY, int rows, int cols) {
        ArrayList<ToggleButton> buttons = new ArrayList<>();
        Random r = new Random();

        int x = startX;
        int y = startY;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                int R = (r.nextInt(256));
                int G = (r.nextInt(256));
                int B = (r.nextInt(256));

                buttons.add(new ToggleButton(x, y, 20, 20, "r:" + R + " g:" + G + " b:" + B, new Color(R, G, B)));
                x += 30;
            }
            y += 30;
            x = startX;
        }

        return buttons;
    }


    public void paintFillStroke(Graphics g) {

        g.setColor(Color.white);
        g.fillRect(fill.x, fill.y, fill.width, fill.height);
        g.fillRect(stroke.x, stroke.y, stroke.width, stroke.height);

        g.setColor(Color.black);
        g.drawString(fill.title, fill.x + 17, fill.y + 15);
        g.drawString(stroke.title, stroke.x + 7, stroke.y + 15);

        g.drawRect(fill.x + 15, fill.y + 25, 20, 20);

        g.drawRect(stroke.x + 15, stroke.y + 25, 20, 20);


        if (fill.c_current != null) {
            g.setColor(fill.c_current);
        } else {
            g.setColor(Color.white);

        }


        g.fillRect(fill.x + 15 + 2, fill.y + 25 + 2, 17, 17);
        g.setColor(stroke.c_current);
        g.fillRect(stroke.x + 17, stroke.y + 25 + 2, 17, 17);


        //painting stroke plus
        g.setColor(strokePlus.c_current);
        g.fillRect(strokePlus.x, strokePlus.y, strokePlus.width, strokePlus.height);
        g.setColor(Color.white);
        g.drawString(strokePlus.title, strokePlus.x + 4, strokePlus.y + 11);

        //painting stroke minus
        g.setColor(strokeMinus.c_current);
        g.fillRect(strokeMinus.x, strokeMinus.y, strokeMinus.width, strokeMinus.height);
        g.setColor(Color.white);
        g.drawString(strokeMinus.title, strokeMinus.x + 6, strokeMinus.y + 11);
        g.drawString(String.valueOf(strokeVal), stroke.x + 60, stroke.y + 50);


        g.setColor(removeFill.c_current);
        g.fillRect(removeFill.x, removeFill.y, removeFill.width, removeFill.height);
        g.setColor(Color.white);
        g.drawString(removeFill.title.substring(0, 6), removeFill.x + 2, removeFill.y + 27);
        g.drawString(removeFill.title.substring(7, 11), removeFill.x + 16, removeFill.y + 40);

        if (removeFill.pressed) {
            g.setColor(Color.black);
            g.drawRect(removeFill.x - 2, removeFill.y - 2, removeFill.width + 4, removeFill.height + 4);
        }

    }


    @Override
    public void mousePress(MouseEvent e) {
        for (ToggleButton colorButton : colorButtons) {


            if (colorButton.IsClicked(e.getX(), e.getY())) {
                if (colorButton.pressed) {
                    activeColor = colorButton.c_current;
                }

                // Preventing other colours from being selected at the same time
                for (ToggleButton button1 : colorButtons) {
                    if (button1.pressed) {
                        button1.pressed = false;
                        colorButton.pressed = true;

                    }
                }
            }


        }

        // Checking edit Colors dialog box
        editColors.IsClicked(e.getX(), e.getY());

        // Checking whether fill is selected or stroke
        for (ToggleButton fillOrStroke : fillStroke) {

            if (fillOrStroke.IsClicked(e.getX(), e.getY())) {
                if (fillOrStroke.pressed) {
                    for (ToggleButton fillOrStroke1 : fillStroke) {
                        if (fillOrStroke1 != fillOrStroke) {
                            fillOrStroke1.pressed = false;
                        }
                    }
                }
            }


            if (fillOrStroke.pressed) {

                fillOrStroke.c_current = activeColor;
            }
        }

        // checking if you want to edit colors so u can check whether gradient is
        // clicked or not
        if (editColors.pressed) {
            // If the gradient is clicked it updates the temp color to a color from the
            // gradient
            gradientWindow.isGradientClicked(e.getX(), e.getY());

            // Calling so select goes back to being unpressed
            gradientWindow.getSelectButton().IsClicked(e.getX(), e.getY());
            if (gradientWindow.getSelectButton().pressed) {

                for (ToggleButton button : colorButtons) {
                    if (button.pressed) {


                        button.c_current = gradientWindow.getTempColor();

                        for (ToggleButton fillStrokeButton : fillStroke) {
                            if (fillStrokeButton.pressed) {

                                fillStrokeButton.c_current = button.c_current;
                            }
                        }
                        activeColor = button.c_current;
                        break;
                    }

                }

            }


            for (ToggleButton colorButton1 : colorButtons1) {


                if (colorButton1.IsClicked(e.getX(), e.getY())) {
                    if (colorButton1.pressed) {
                        activeColor = colorButton1.c_current;
                    }

                    // Preventing other colours from being selected at the same time
                    for (ToggleButton button1 : colorButtons1) {
                        if (button1 != colorButton1) {
                            button1.pressed = false;

                        }

                    }
                }
            }
        }

        if (strokeVal == 0 || (strokeVal > 0 && strokeVal <= 10)) {
            if (strokePlus.IsClicked(e.getX(), e.getY())) {
                strokeVal++;
                if (strokeVal > 10) {
                    strokeVal = 10;
                }
            } else if (strokeMinus.IsClicked(e.getX(), e.getY())) {
                strokeVal--;
                if (strokeVal < 0) {
                    strokeVal = 0;
                }
            }
        }


        if (gradientWindow.getCloseEditColors().IsClicked(e.getX(), e.getY())) {
            editColors.pressed = false;
            editColors.c_current = editColors.c_depressed;
        }

        if (removeFill.IsClicked(e.getX(), e.getY())) {
            fill.pressed = false;
            fill.c_current = null;

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
    public void mouseMove(MouseEvent e) {
        if (e.getX() > 430 && e.getX() < 540 && e.getY() > 30 && e.getY() < 90) {
            for (ToggleButton fillORstroke : fillStroke) {
                if (fillORstroke.IsHovered(e.getX(), e.getY())) {

                    tooltip.setMessage(fillORstroke.title);
                    tooltip.active = true;
                    break;
                } else tooltip.active = false;
            }
        }
        else if (e.getX() > 120 && e.getX() < 500 && e.getY() > 35 && e.getY() < 100) {
            for (ToggleButton colorButton : colorButtons) {

                if (colorButton.IsHovered(e.getX(), e.getY())) {


                    tooltip.setMessage(colorButton.title);

                    tooltip.active = true;

                    break;

                } else tooltip.active = false;
            }

        }
        else if (e.getX() > 545 && e.getX() < 560 && e.getY() > 30 && e.getY() < 45) {
            if (strokePlus.IsHovered(e.getX(), e.getY())) {
                tooltip.setMessage("Increase stroke width");
                tooltip.active = true;
            } else tooltip.active = false;
        }
        else if (e.getX() > 545 && e.getX() < 560 && e.getY() > 50 && e.getY() < 65) {
            if (strokeMinus.IsHovered(e.getX(), e.getY())) {
                tooltip.setMessage("Decrease stroke width");
                tooltip.active = true;
            } else tooltip.active = false;
        }
        else if (e.getX() > 580 && e.getX() < 630 && e.getY() > 30 && e.getY() < 70) {
            if (editColors.IsHovered(e.getX(), e.getY())) {
                tooltip.setMessage("Choose from gradient");
                tooltip.active = true;
            } else tooltip.active = false;

        }

        else if (e.getX() > 635 && e.getX() < 635 + 50 && e.getY() > 30 && e.getY() < 30 + 60) {
            if (removeFill.IsHovered(e.getX(), e.getY())) {
                tooltip.setMessage("Click to remove fill");
                tooltip.active = true;
            }
            else tooltip.active = false;

        }
        else tooltip.active = false;


    }


}
