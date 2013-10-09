package utils;

import javax.swing.*;
import java.awt.*;

/**
 * Quick & simple do some GUI functions
 */
public class GUI {
    private static int margin = 5;

    public static void groupRadioButtons(JRadioButton ... group){
        ButtonGroup buttonGroup = new ButtonGroup();
        for (JRadioButton radioButton:group){
            buttonGroup.add(radioButton);
        }
    }

    public static void toScreenCenter(JFrame frame){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    public static void setNimbusLookAndFeel(){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//new look & feel - from Java SE 6 Update 10 Beta
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static JPanel createHPanel(Component... components) {
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, margin)));

        for (Component component : components) {
            panel.add(Box.createRigidArea(new Dimension(margin,0)));
            panel.add(component);
        }
        panel.add(Box.createRigidArea(new Dimension(margin, 0)));

        return panel;
    }

    public static JPanel createVPanel(Component... components) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createRigidArea(new Dimension(0, margin)));
        for (Component component : components) {
            panel.add(component);
            panel.add(Box.createRigidArea(new Dimension(0, margin)));
        }
       // panel.add(Box.createRigidArea(new Dimension(0, margin)));

        return panel;
    }
}
