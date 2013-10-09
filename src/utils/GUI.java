package utils;

import com.sun.imageio.plugins.common.ImageUtil;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

    public static void addDefaultContextMenu(final JTextComponent textComponent){
        JPopupMenu popup = new JPopupMenu();
        textComponent.add(popup);
        textComponent.setComponentPopupMenu(popup);

        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.setIcon(createImageIcon("/img/copy.png"));
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
        copyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textComponent.copy();
            }
        });
        popup.add(copyItem);

        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.setIcon(createImageIcon("/img/paste.png"));
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));
        pasteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textComponent.paste();
            }
        });
        popup.add(pasteItem);

        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.setIcon(createImageIcon("/img/cut.png"));
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        cutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textComponent.cut();
            }
        });
        popup.add(cutItem);
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = GUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find image file: " + path);
            return null;
        }
    }
}
