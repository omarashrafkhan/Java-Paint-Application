package ColorBar;

import Toplevelclasses.ActiveButton;
import Toplevelclasses.ToggleButton;

import java.awt.*;

public class GradientWindow {
    //Color to show in the edit color panel
    private Color tempColor = Color.black;
    //select button to select color from the gradient
    private ActiveButton selectButton;

    public Color getTempColor() {
        return tempColor;
    }

    public ActiveButton getSelectButton() {
        return selectButton;
    }

    //color gradient for the edit colors
    private Color[][] gradient;

    public ActiveButton getCloseEditColors() {
        return closeEditColors;
    }

    //Closing button for edit colors
    private ActiveButton closeEditColors;
    private static GradientWindow instance = new GradientWindow();
    final int editColors_x = 580;
    final int editColors_y = 30;

    private GradientWindow() {


        closeEditColors = new ActiveButton(editColors_x + 290, editColors_y + 175, 60, 20, "Close");
        selectButton = new ActiveButton(editColors_x + 125, editColors_y + 450, 60, 20, "Select");



        gradient = new Color[200][200];
    }


    public  static GradientWindow getInstance(){
        return instance;
    }

    public void paintGradient(Graphics g){
        int WIDTH = 200;
        int HEIGHT = 200;

        // 2D array to store color gradient

        Color[] colors = {
                new Color(255, 0, 0), // Red
                new Color(255, 255, 0), // Yellow
                new Color(0, 255, 0), // Green
                new Color(0, 255, 255), // Turquoise
                new Color(0, 0, 255), // Blue
                new Color(255, 0, 255) // Purple
        };

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double t = (double) y / HEIGHT; // calculate interpolation factor

                // interpolate RGB values between colors
                int index1 = (int) (t * (colors.length - 1));
                int index2 = index1 + 1;
                double fraction = t * (colors.length - 1) - index1;
                int red = (int) ((1 - fraction) * colors[index1].getRed() + fraction * colors[index2].getRed());
                int green = (int) ((1 - fraction) * colors[index1].getGreen() + fraction * colors[index2].getGreen());
                int blue = (int) ((1 - fraction) * colors[index1].getBlue() + fraction * colors[index2].getBlue());

                gradient[y][x] = new Color(red, green, blue); // create color object and store in gradient array
            }
        }

        int startx = editColors_x + 50;
        int starty = editColors_y + 200;


        g.setColor(Color.gray);
        g.fillRect(startx - 30, starty - 30, 350, 350);


        g.setColor(Color.lightGray);
        g.fillRect(startx - 35, starty - 35, 350, 350);


        for (int x = startx; x < WIDTH + startx; x++) {
            for (int y = starty; y < HEIGHT + starty; y++) {
                g.setColor(gradient[x - startx][y - starty]); // set color of square
                g.fillRect(x, y, 1, 1); // draw small square at current position
            }
        }

        g.setColor(Color.black);
        g.drawRect(startx + 93 - 2, starty + 215 - 2, 25, 25);

        g.setColor(tempColor);
        g.fillRect(startx + 93, starty + 215, 22, 22);

        g.setColor(Color.white);
        g.fillRect(selectButton.x, selectButton.y, selectButton.width, selectButton.height);
        g.setColor(Color.black);
        g.drawString(selectButton.title, selectButton.x + 12, selectButton.y + 14);


        //paint close button
        g.setColor(Color.red);
        g.fillRect(closeEditColors.x, closeEditColors.y, closeEditColors.width, closeEditColors.height);
        g.setColor(Color.white);
        g.drawString(closeEditColors.title, closeEditColors.x + 14, closeEditColors.y + 13);

    }

    public void isGradientClicked(int x, int y) {
        if (x > editColors_x + 50 && x < editColors_x + 50 + 200 && y > editColors_y + 200 && y < editColors_y + 200 + 200)
            tempColor = gradient[x - (editColors_x + 50)][y - (editColors_y + 200)];
    }







}




