package LayerBar;

import ColorBar.ColorToolBar;
import ShapeBar.ShapeToolBar;
import Shapes.Rectangle;
import Shapes.Shape;
import Shapes.*;
import Toplevelclasses.ActiveButton;
import Toplevelclasses.ToggleButton;
import Toplevelclasses.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import static java.awt.event.KeyEvent.*;


public class LayersToolBar extends Toolbar {
    private static Layer activeLayer;
    private static ArrayList<Layer> layers;
    private ArrayList<ImageIcon> images;
    private JPanel panel;
    private Method[] methods = LayersToolBar.class.getDeclaredMethods();
    private ArrayList<ActiveButton> toolButtons;
    private Circle currentCircle;
    //private boolean isDragging;
    private int startX, startY;
    private Rectangle currentRectangle;
    //Eq triangle
    private EquilateralTriangle currentEqTriangle; // current equilateral triangle being drawn
    private Pentagram currentPentagram;
    private FreeDraw currentFreeDraw;
    // private Point mousePressPoint;
    private Hexagon currentHexagon;
    private RA_Triangle currentRAtriangle;
    private Bezier currentBezierCurve;
    public LayersToolBar(JPanel panel) {
        super(new Point(0, 0), new Dimension(300, Toolkit.getDefaultToolkit().getScreenSize().height), Color.lightGray, "Layers Tool Bar");
        this.panel = panel;
        this.startPosition.x = s_width - this.dimension.width;
        initialize();
    }

    public static Layer getActiveLayer() {
        return activeLayer;
    }

    public static ArrayList<Layer> getLayers() {
        return layers;
    }

    public static void setLayers(ArrayList<Layer> layerz) {
        layers = layerz;
    }

    @Override
    public void paint(Graphics g) {

        for (int i = layers.size() - 1; i >= 0; i--) {
            Layer layer = layers.get(i);
            for (Shape shape : layer.getShapesEnque()) {
                shape.draw(g);
            }
        }


        g.setColor(backgroundColor);
        g.fillRect(startPosition.x, startPosition.y, dimension.width, dimension.height);

        for (ActiveButton toolButton : toolButtons) {
            g.drawImage(toolButton.current_image, toolButton.x, toolButton.y, panel);

        }

        paintLayerPanel(g);


    }

    public void Add() {
        if (layers.size() <= 14) {
            if (layers.size() == 0) {
                layers.add(new Layer(startPosition.x + 30, startPosition.y + 340, 80, 20, "Layer 0"));
            } else {
                int x = layers.get(layers.size() - 1).x;
                int y = layers.get(layers.size() - 1).y;

                layers.add(new Layer(x, y + 20 + 10, 80, 20, "Layer " + layers.size()));

            }
        }

    }

    public void Remove() {
        if (layers.size() != 1) {
            if (activeLayer != null) {
                ToggleButton temp = activeLayer;
                layers.remove(activeLayer);
                if (layers.size() == 0) {
                    activeLayer = null;
                    return;
                }

                if (temp != layers.get(layers.size() - 1)) {
                    //int i = layers.indexOf(temp);
                    int i = 0;

                    for (ToggleButton layer : layers) {
                        layer.title = "Layer " + (i);
                        if (layer.y > temp.y) {
                            layer.y -= (layer.height + 10);
                            //layer.title = "Layer " + (i + 1);
                        }
                        i++;
                    }
                }
            }
            activeLayer = null;
        } else {
            layers.get(0).pressed = false;
            layers.get(0).c_current = layers.get(0).c_depressed;

        }

    }

    public void Up() {
        if (activeLayer != null) {
            int index = layers.indexOf(activeLayer);
            if (index > 0) {
                // Swap the active layer with the layer above it
                int temp = activeLayer.y;
                activeLayer.y = layers.get(index - 1).y;
                layers.get(index - 1).y = temp;
                Collections.swap(layers, index, index - 1);
            }
        }
    }

    public void Down() {
        if (activeLayer != null) {
            int index = layers.indexOf(activeLayer);
            if (index < layers.size() - 1) {
                // Swap the active layer with the layer below it
                int temp = activeLayer.y;
                activeLayer.y = layers.get(index + 1).y;
                layers.get(index + 1).y = temp;
                Collections.swap(layers, index, index + 1);
            }
        }
    }


    public void paintLayerPanel(Graphics g) {


        // Draw shadow
        Color shadowColor = new Color(0, 0, 0, 100); // semi-transparent black
        g.setColor(shadowColor);
        Point panel = new Point(startPosition.x + 25, startPosition.y + 305);
        g.fillRect(panel.x, panel.y, 250, s_height-panel.y-50);

        // Draw panel
        g.setColor(Color.white);
        g.fillRect(panel.x-4, panel.y-5, 250, s_height-panel.y-50);





        g.setColor(Color.black);
        g.drawRect(panel.x-4, panel.y-5, 250, s_height-panel.y-50);


        g.drawLine(startPosition.x + 21, startPosition.y + 330, startPosition.x + 21 + 250, startPosition.y + 330);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Layers", startPosition.x + 30, startPosition.y + 320);
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        for (Layer layer : layers) {

            layer.paint(g);
        }

    }

    public void initialize() {
        images = new ArrayList<>();

        for (String shapeName : new String[]{"ArrowUp", "ArrowDown", "Add", "remove"}) {
            String pressedFilename = String.format("..\\Java Paint Application\\icons\\%s pressed.png", shapeName);
            String unpressedFilename = String.format("..\\Java Paint Application\\icons\\%s unpressed.png", shapeName);
            images.add(new ImageIcon(pressedFilename));
            images.add(new ImageIcon(unpressedFilename));
        }


        toolButtons = new ArrayList<>();
        toolButtons.add(new ActiveButton(startPosition.x + 85, startPosition.y + 150, 50, 50, images.get(1).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), images.get(0).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "Up"));
        toolButtons.add(new ActiveButton(startPosition.x + 150, startPosition.y + 150, 50, 50, images.get(3).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), images.get(2).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "Down"));
        toolButtons.add(new ActiveButton(startPosition.x + 85, startPosition.y + 220, 50, 50, images.get(5).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), images.get(4).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "Add"));
        toolButtons.add(new ActiveButton(startPosition.x + 150, startPosition.y + 220, 50, 50, images.get(7).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), images.get(6).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "Remove"));


        layers = new ArrayList<>();
        layers.add(new Layer(startPosition.x + 30, startPosition.y + 340, 80, 20, "Layer 0"));
    }

    @Override
    public void keyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            Up();return;
        }
        else if(keyCode == VK_DOWN) Down();return;
    }

    private boolean withinBounds(MouseEvent e) {
        return (e.getX() > 120 && e.getX() < 120 + 1096 && e.getY() > 120 && e.getY() < 120 + 704);
    }


    @Override
    public void mousePress(MouseEvent e) {

        for (ActiveButton toolButton : toolButtons) {
            if (toolButton.IsClicked(e.getX(), e.getY())) {

                for (Method method : methods) {
                    if (method.getName().equals(toolButton.title)) {
                        try {
                            method.invoke(this);
                        } catch (IllegalAccessException | InvocationTargetException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }

        }

        for (Layer layer : layers) {
            if (layer.IsClicked(e.getX(), e.getY())) {
                if (activeLayer != layer) {
                    // A different layer was clicked, so select it and deselect others
                    activeLayer = layer;
                    for (Layer otherLayer : layers) {
                        if (otherLayer != layer) {
                            otherLayer.pressed = false;
                            otherLayer.c_current = otherLayer.c_depressed;
                        }
                    }
                    layer.pressed = true;
                } else {
                    // The same layer was clicked again, so deselect it
                    activeLayer = null;
                    layer.pressed = false;
                }

            }

        }

        Color fillColor = ColorToolBar.getFill().c_current;
        Color strokeColor = ColorToolBar.getStroke().c_current;
        int strokeVal = ColorToolBar.getStrokeVal();
        ToggleButton activeShape = ShapeToolBar.getActiveShape();

        if (withinBounds(e)) {
            if (activeShape != null) {
                if (layers.get(0).getShapesDeque().size() > 0) {
                    layers.get(0).getShapesDeque().clear();
                }

                switch (activeShape.title) {
                    case "Circle pressed.png" -> {
                        currentCircle = new Circle(e.getX(), e.getY(), fillColor, strokeColor, strokeVal);
                        layers.get(0).getShapesEnque().add(currentCircle);
                    }
                    case "Rectangle pressed.png" -> {
                        startX = e.getX();
                        startY = e.getY();
                        currentRectangle = new Rectangle(startX, startY, fillColor, strokeColor, strokeVal, 0, 0);
                        layers.get(0).getShapesEnque().add(currentRectangle);
                    }
                    case "EQ triangle pressed.png" -> {
                        currentEqTriangle = new EquilateralTriangle(e.getX(), e.getY(), 0, fillColor, strokeColor, strokeVal);
                        layers.get(0).getShapesEnque().add(currentEqTriangle);
                    }
                    case "pentagram pressed.png" -> {
                        currentPentagram = new Pentagram(e.getX(), e.getY(), 0, fillColor, strokeColor, strokeVal);
                        layers.get(0).getShapesEnque().add(currentPentagram);
                    }
                    case "FreeDraw pressed.png" -> {
                        currentFreeDraw = new FreeDraw(e.getX(), e.getY(), strokeColor, strokeVal);
                        layers.get(0).getShapesEnque().add(currentFreeDraw);
                    }
                    case "Hexagon pressed.png" -> {
                        currentHexagon = new Hexagon(e.getX(), e.getY(), fillColor, strokeColor, strokeVal);
                        layers.get(0).getShapesEnque().add(currentHexagon);
                    }
                    case "RA triangle pressed.png" -> {
                        currentRAtriangle = new RA_Triangle(e.getX(), e.getY(), fillColor, strokeColor, strokeVal);
                        layers.get(0).getShapesEnque().add(currentRAtriangle);
                    }
                    case "Bezier pressed.png" -> {
                        if (strokeVal > 0) {
                            if (currentBezierCurve == null) {
                                currentBezierCurve = new Bezier(e.getX(), e.getY(), strokeColor, strokeVal);
                                layers.get(0).getShapesEnque().add(currentBezierCurve);
                            } else {
                                currentBezierCurve.mousePress(e);
                            }
                        }
                    }
                }

            }

        }


    }


    @Override
    public void mouseRelease(MouseEvent e) {

        if (withinBounds(e)) {

            if (ShapeToolBar.getActiveShape() != null) {
                switch (ShapeToolBar.getActiveShape().title) {
                    case "Circle pressed.png" -> {
                        // currentCircle.resizing = false;
                        currentCircle = null;
                    }
                    case "Rectangle pressed.png" -> currentRectangle = null;
                    case "EQ triangle pressed.png" -> currentEqTriangle = null;
                    case "pentagram pressed.png" -> currentPentagram = null;
                    case "FreeDraw pressed.png" -> currentFreeDraw = null;
                    case "Hexagon pressed.png" -> currentHexagon = null;
                    case "RA triangle pressed.png" -> currentRAtriangle = null;
                    case "Bezier pressed.png" -> {
                        if (currentBezierCurve!=null && currentBezierCurve.getCubicControl() != null) currentBezierCurve = null;
                    }
                }

            }

        }

    }

    @Override
    public void mouseDrag(MouseEvent e) {
        if (withinBounds(e)) {
            if (ShapeToolBar.getActiveShape() != null) {
                switch (ShapeToolBar.getActiveShape().title) {
                    case "Circle pressed.png" -> {
                        int dx_c = e.getX() - currentCircle.getX();
                        int dy_c = e.getY() - currentCircle.getY();
                        currentCircle.setRadius((int) Math.sqrt(dx_c * dx_c + dy_c * dy_c));
                    }
                    case "Rectangle pressed.png" -> {
                        int currentX = e.getX();
                        int currentY = e.getY();
                        int width = Math.abs(currentX - startX);
                        int height = Math.abs(currentY - startY);
                        int x_r = Math.min(startX, currentX);
                        int y_r = Math.min(startY, currentY);
                        currentRectangle.setBounds(x_r, y_r, width, height);
                    }
                    case "EQ triangle pressed.png" -> {
                        currentEqTriangle.setRadius((int) new Point(currentEqTriangle.getX(), currentEqTriangle.getY()).distance(e.getPoint()));
                        double dx = e.getX() - currentEqTriangle.getX();
                        double dy = e.getY() - currentEqTriangle.getY();
                        currentEqTriangle.setAngle(Math.atan2(dy, dx));
                    }
                    case "pentagram pressed.png" -> {
                        int x = e.getX();
                        int y = e.getY();
                        currentPentagram.setRadius((int) Math.sqrt(Math.pow(currentPentagram.getX() - x, 2) + Math.pow(currentPentagram.getY() - y, 2)));
                        double dx_p = e.getX() - currentPentagram.getX();
                        double dy_p = e.getY() - currentPentagram.getY();
                        currentPentagram.setAngle(Math.atan2(dy_p, dx_p));
                    }
                    case "FreeDraw pressed.png" -> currentFreeDraw.incrementPath(e.getX(), e.getY());
                    case "Hexagon pressed.png" -> {
                        int dx_h = e.getX() - currentHexagon.getX();
                        int dy_h = e.getY() - currentHexagon.getY();
                        currentHexagon.setAngle(Math.atan2(dy_h, dx_h));
                        currentHexagon.setRadius((int) (Math.sqrt(dx_h * dx_h + dy_h * dy_h) / Math.sqrt(3)));
                    }
                    case "RA triangle pressed.png" -> currentRAtriangle.setEnd(e.getPoint());
                    case "Bezier pressed.png" -> {
                        if (ColorToolBar.getStrokeVal() > 0) currentBezierCurve.mouseDrag(e);
                    }
                }


            }


        }


    }

    @Override
    public void mouseMove(MouseEvent e) {
        if (e.getX() > startPosition.x + 21 && e.getY() > startPosition.y + 300) {
            for (Layer layer : layers) {
                if (layer.IsHovered(e.getX(), e.getY())) {
                    if (layer.pressed) {
                        tooltip.setMessage("Click to make un-active");

                    } else tooltip.setMessage("Click to make active");
                    tooltip.active = true;
                    break;
                } else tooltip.active = false;
            }
        } else {
            for (ActiveButton toolButton : toolButtons) {

                if (toolButton.IsHovered(e.getX(), e.getY())) {
                    tooltip.setMessage(toolButton.title);
                    tooltip.active = true;
                    break;
                } else tooltip.active = false;
            }
        }


    }


}
