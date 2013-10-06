import gui.MainPanel;

import javax.swing.*;

/**
 * Example of simple chat
 *
 * @author KciRay (http://habrahabr.ru/users/kciray/)
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainPanel mainPanel = new MainPanel();
                mainPanel.setVisible(true);
            }
        });
    }
}
