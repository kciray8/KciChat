import gui.MainPanel;
import utils.GUI;

import javax.swing.*;

/**
 * Example of simple chat
 *
 * @author KciRay (http://habrahabr.ru/users/kciray/)
 */

public class Main {
    public static void main(String[] args) {
        GUI.setNimbusLookAndFeel();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainPanel mainPanel = new MainPanel();
                mainPanel.setVisible(true);
            }
        });
    }
}