package MainClasses;

import ColorBar.ColorToolBar;
import LayerBar.LayersToolBar;
import Menubar.MenuBar;
import ShapeBar.ShapeToolBar;
import Toplevelclasses.GridButton;
import Toplevelclasses.Toolbar;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Window extends JPanel implements MouseInputListener, KeyListener {

    public final int s_width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public final int s_height = Toolkit.getDefaultToolkit().getScreenSize().height;


    JPanel panel = this;
    ArrayList<Toolbar> toolbars;
    GridButton gridButton;

    private Timer timer;

    public Window() {

        initBoard();
    }

    private void InitializeAssets() {
        toolbars = new ArrayList<>();

        toolbars.add(new MenuBar());
        toolbars.add(new ColorToolBar());
        toolbars.add(new ShapeToolBar(panel));
        toolbars.add(new LayersToolBar(panel));

        gridButton = GridButton.getInstance();


    }

    private void initBoard() {

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        setBackground(Color.black);
        setPreferredSize(new Dimension(400, 400));
        setFocusable(true);


        InitializeAssets();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Toolkit.getDefaultToolkit().sync();
                repaint();
            }
        }, 0, 25);

    }

    void paintPanel(Graphics g) {


        g.setColor(Color.gray);

        g.fillRect(0, 0, s_width - toolbars.get(3).dimension.width, toolbars.get(0).dimension.height + toolbars.get(1).dimension.height + 20);

        g.fillRect(0, 0, toolbars.get(2).dimension.width + 20, s_height);
        g.fillRect(0, s_height - 40, s_width - toolbars.get(3).dimension.width, 40);
        g.fillRect(toolbars.get(3).startPosition.x - 20, 0, 20, s_height);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.white);
        int width = s_width - toolbars.get(2).dimension.width - toolbars.get(3).dimension.width - 40;
        int height = s_height - (toolbars.get(0).dimension.height + toolbars.get(1).dimension.height) - 60;
        g.fillRect(toolbars.get(2).startPosition.x + toolbars.get(2).dimension.width + 20, toolbars.get(1).startPosition.y + toolbars.get(1).dimension.height + 20, width, height);

        paintGridLines(g);


        toolbars.get(3).paint(g);
        paintPanel(g);
        toolbars.get(2).paint(g);
        toolbars.get(1).paint(g);
        toolbars.get(0).paint(g);

        gridButton.paintGridButton(g);

        for(Toolbar toolbar : toolbars)
        {
            if(toolbar.tooltip.active) {
                Point mousePosition = MouseInfo.getPointerInfo().getLocation();
                toolbar.tooltip.showTooltip(g, mousePosition.x+5, mousePosition.y+5);
            }
        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gridButton.IsClicked(e.getX(), e.getY());
    }

    public void paintGridLines(Graphics g) {
        g.setColor(Color.lightGray);
        if (gridButton.getVal() != 0) {
            int x = toolbars.get(2).startPosition.x + toolbars.get(2).dimension.width + 20;
            int y = toolbars.get(1).startPosition.y + toolbars.get(1).dimension.height + 20;
            int width = s_width - toolbars.get(2).dimension.width - toolbars.get(3).dimension.width - 40;
            int height = s_height - (toolbars.get(0).dimension.height + toolbars.get(1).dimension.height) - 60;
            int horizLines = height / gridButton.getVal();
            int vertLines = width / gridButton.getVal();

            for (int i = 0; i < vertLines+1; i++) {
                g.drawLine(x, y, x, y + height);
                x += gridButton.getVal();
            }

            x = toolbars.get(2).startPosition.x + toolbars.get(2).dimension.width + 20;
            for (int i = 0; i < horizLines; i++) {
                g.drawLine(x, y, x + width, y);
                y += gridButton.getVal();
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

        for (Toolbar toolbar : toolbars)
            toolbar.mousePress(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        toolbars.get(3).mouseRelease(e);

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        try{
        toolbars.get(3).mouseDrag(e);

        }catch(Exception ignore){
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(Toolbar toolbar:toolbars){
            if(toolbar.isWithinBounds(e.getX(),e.getY())){
                toolbar.mouseMove(e);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (Toolbar toolbar : toolbars) {
            try {
                toolbar.keyPress(e);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}