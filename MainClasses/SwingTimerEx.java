package MainClasses;

import javax.swing.*;
import java.awt.*;

public class SwingTimerEx extends JFrame {

    public SwingTimerEx() {

        initUI();
    }

    public static void main(String[] args) {

            EventQueue.invokeLater(() -> {
                SwingTimerEx ex = new SwingTimerEx();
                ex.setVisible(true);
            });



    }

    private void initUI() {

        add(new Window());

        setResizable(true);
        pack();
        setSize(800, 300);
        setTitle("Java Painter");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(new Dimension(1200,800));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}