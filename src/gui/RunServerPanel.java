package gui;

import core.KciServer;
import utils.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RunServerPanel extends JPanel implements ActionListener {

    private final JButton runButton = new JButton("Запустить сервер");
    private final JTextField portField = new JTextField(5);
    private MainPanel mainPanel;

    public RunServerPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        JLabel portLabel = new JLabel("Порт для прослушивания ");
        portField.setText("888");
        JPanel portPanel = GUI.createHPanel(portLabel, portField);

        add(portPanel);
        runButton.addActionListener(this);
        runButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(runButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runButton) {
            try {
                int port = Integer.parseInt(portField.getText());

                createServer(port);
            } catch (NumberFormatException exc) {
                JOptionPane.showMessageDialog(this, "Вы ввели некорректное число!");
                return;
            }
        }
    }

    private void createServer(int port) {
        try {
            KciServer server = new KciServer(port);
            ServerConsole serverConsole = new ServerConsole(server);
            server.setConsole(serverConsole);
            serverConsole.setVisible(true);
            mainPanel.dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Не удалось поднять сервер используя порт " +
                    port + ". Ошибка - " + e.getMessage());
        }
    }
}
