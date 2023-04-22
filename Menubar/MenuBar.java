package Menubar;

import LayerBar.Layer;
import LayerBar.LayersToolBar;
import ShapeBar.ShapeToolBar;
import Toplevelclasses.ActiveButton;
import Toplevelclasses.Toolbar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;


public class MenuBar extends Toolbar {
    private final Method[] methods = MenuBar.class.getDeclaredMethods();
    private ArrayList<Menu> menus;
    private ArrayList<FileButton> fileButtons;
    private ArrayList<File> files;
    //Close button for open submenu
    private ActiveButton close;
    //Setting active submenu for operation
    private String activeSubMenu = "nothing";

    //File and edit menu of the bar
    private Menu fileMenu;
    private Menu editMenu;

    public MenuBar() {
        super(new Point(0, 0), new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()), 20), Color.lightGray, "MenuBar");

        initialize();

    }

    public void paint(Graphics g) {
        //Painting bar bg
        g.setColor(super.backgroundColor);
        g.fillRect(startPosition.x, startPosition.y, (int) dimension.getWidth(), (int) dimension.getHeight());


        //Painting the menus
        for (Menu menu : menus) {
            menu.paintMenu(g);

            // if a submenu is invoked then call its function
            for (Method method : methods) {

                if (method.getName().equals(activeSubMenu)) {
                    try {

                        if (method.getParameterCount() == 1) method.invoke(this, g);
                        else method.invoke(this);
                        break;
                    } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {

                        e.printStackTrace();
                    }

                }
            }

        }


    }


    @Override
    public void initialize() {

        menus = new ArrayList<>();
        fileMenu = new Menu("File", new ArrayList<>(), 0, 0);

        // New
        fileMenu.getSubMenus().add(new SubMenu("New", fileMenu.x, fileMenu.y + 20, fileMenu.width, fileMenu.height));
        // Open
        fileMenu.getSubMenus().add(new SubMenu("Open", fileMenu.x, fileMenu.y + 40 + 1, fileMenu.width, fileMenu.height));
        // Save
        fileMenu.getSubMenus().add(new SubMenu("Save", fileMenu.x, fileMenu.y + 60 + 2, fileMenu.width, fileMenu.height));

        editMenu = new Menu("Edit", new ArrayList<>(), fileMenu.width, 0);

        // Undo
        editMenu.getSubMenus().add(new SubMenu("Undo", editMenu.x, editMenu.y + 20, editMenu.width, editMenu.height));
        // Redo
        editMenu.getSubMenus().add(new SubMenu("Redo", editMenu.x, editMenu.y + 40 + 1, editMenu.width, editMenu.height));

        this.menus.add(fileMenu);
        this.menus.add(editMenu);

        //Close button for Open
        close = new ActiveButton(s_width / 3 + 425, s_height / 3 + 12, 50, 30, "Close");


        //File buttons for the open submenu
        fileButtons = new ArrayList<>();


        Point point = new Point(s_width / 3 + 40, s_height / 3 + 70);
        File[] fileArray = new File("..\\JavaPainter\\files").listFiles();
        files = new ArrayList<>();


        // assert fileArray != null;
        assert fileArray != null;
        Collections.addAll(files, fileArray);


        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".ser")) {
                FileButton temp = new FileButton(point.x, point.y, 200, 20, file.getName(), new Color(100, 150, 255), new Color(255, 100, 100), file);
                fileButtons.add(temp);


                point.y += 30;

            }
        }

    }

    //New, open, save, undo, redo functions of the submenus
    public void Open(Graphics g) {
        if (ShapeToolBar.getActiveShape() != null) {
            ShapeToolBar.getActiveShape().current_image = ShapeToolBar.getActiveShape().image_depressed;
            ShapeToolBar.setActiveShape(null);
        }

        int x = Toolkit.getDefaultToolkit().getScreenSize().width;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height;

        int shadowOffset = 5;
        g.setColor(Color.gray);
        g.fillRect((x / 3) + shadowOffset, (y / 3) + shadowOffset, 500, 400);

        g.setColor(Color.lightGray);

        Point start = new Point(x / 3, y / 3);
        g.fillRect(start.x, start.y, 500, 400);
        g.setColor(Color.white);
        g.fillRect(start.x + 25, start.y + 50, 450, 300);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Files", x / 3 + 30, y / 3 + 35);

        g.setColor(Color.red);
        g.fillRect(close.x, close.y, close.width, close.height);
        g.setColor(Color.white);
        g.drawString(close.title, close.x + 5, close.y + 20);


        g.setFont(new Font("Arial", Font.PLAIN, 12));

        for (FileButton fileButton : fileButtons) {
            fileButton.paintFileButton(g);
        }
    }

    public void New() {
        while (LayersToolBar.getLayers().size() > 1) {
            LayersToolBar.getLayers().remove(LayersToolBar.getLayers().size() - 1);
        }

        LayersToolBar.getLayers().get(0).getShapesEnque().clear();

        activeSubMenu = "Nothing";

    }

    public void Save() throws IOException {

        if (fileButtons.size() < 9) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss");
            long timestamp = System.currentTimeMillis();
            LocalDateTime datetime = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, java.time.ZoneOffset.UTC);
            String timestampStr = datetime.format(formatter);

            // create a new file inside the "files" folder at the specified location with a
            // name based on the current timestamp
            File newFile = new File("..\\JavaPainter\\files", timestampStr + ".ser");
            files.add(newFile);

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(newFile));


            out.writeObject(LayersToolBar.getLayers());


            int y;
            if (fileButtons.size() == 0) y = s_height / 3 + 70;
            else y = fileButtons.get(fileButtons.size() - 1).y + 30;


            fileButtons.add(new FileButton(s_width / 3 + 40, y, 200, 20, newFile.getName(), new Color(100, 150, 255), new Color(255, 100, 100), newFile));
            out.close();

        } else {
            activeSubMenu = "Nothing";
            return;
        }
        activeSubMenu = "Nothing";

    }


    public void Undo() {
        if (LayersToolBar.getActiveLayer() != null) {

            if (LayersToolBar.getActiveLayer().getShapesEnque().size() != 0)

                LayersToolBar.getActiveLayer().getShapesDeque().add(LayersToolBar.getActiveLayer().getShapesEnque().remove(LayersToolBar.getActiveLayer().getShapesEnque().size() - 1));
        }

        activeSubMenu = "nothing";
    }

    public void Redo() {
        if (LayersToolBar.getActiveLayer() != null) if (LayersToolBar.getActiveLayer().getShapesDeque().size() != 0)
            LayersToolBar.getActiveLayer().getShapesEnque().add(LayersToolBar.getActiveLayer().getShapesDeque().remove(LayersToolBar.getActiveLayer().getShapesDeque().size() - 1));
        activeSubMenu = "nothing";
    }


    @Override
    public void mousePress(MouseEvent e) {
        for (Menu menu : menus) {

            if (menu.IsClicked(e.getX(), e.getY())) {
                if (menu.pressed) {
                    for (Menu menu1 : menus) {
                        if (menu1 != menu) {
                            menu1.pressed = false;
                            menu1.c_current = menu1.c_depressed;
                        }

                    }

                }
            }


            for (SubMenu item : menu.getSubMenus()) {
                if (item.IsClicked(e.getX(), e.getY())) {
                    activeSubMenu = item.title;
                }

            }
            if (close.IsClicked(e.getX(), e.getY()) && close != null) {
                activeSubMenu = "nothing";
            }
        }


        if (activeSubMenu.equals("Open")) {
            for (FileButton filebutton : fileButtons) {
                if (filebutton.IsClicked(e.getX(), e.getY())) {
                    try {

                        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filebutton.getFile()));
                        System.out.println("here1");
                        ArrayList<Layer> deserializedList = (ArrayList<Layer>) in.readObject();
                        LayersToolBar.setLayers(deserializedList);

                        in.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }

    }

    @Override
    public void keyPress(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 's' -> {
                try {
                    Save();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            case 'n' -> New();
            case 'o' -> {
                if (activeSubMenu.equals("Open")) activeSubMenu = "nothing";
                else activeSubMenu = "Open";
            }
            case 'u' -> Undo();
            case 'r' -> Redo();
            default -> {
            }

        }


    }

    @Override
    public void mouseRelease(MouseEvent e) {

    }

    @Override
    public void mouseDrag(MouseEvent e) {

    }

    @Override
    public void mouseMove(MouseEvent e) {

    }

}
