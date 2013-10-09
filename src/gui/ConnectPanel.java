package gui;

import utils.GUI;
import utils.Q;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class ConnectPanel extends JPanel implements ActionListener {

    private final JButton connectButton;
    private final JTextField portField = new JTextField(8);
    private final JTextField ipField = new JTextField(8);
    private MainPanel mainPanel;

    public ConnectPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel ipLabel = new JLabel("IP-адресс");
        ipField.setText("127.0.0.1");
        JPanel ipPanel = GUI.createHPanel(ipLabel, ipField);
        add(ipPanel);

        JLabel portLabel = new JLabel("Порт");
        portField.setText("888");
        JPanel portPanel = GUI.createHPanel(portLabel, portField);
        add(portPanel);

        connectButton = new JButton("Подключиться");
        connectButton.addActionListener(this);
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(connectButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connectButton) {
            try {
                int port = Integer.parseInt(portField.getText());

                Socket socket = new Socket(InetAddress.getByName(ipField.getText()), port);

                NewUserPanel userPanel = new NewUserPanel(socket);
                userPanel.setVisible(true);

                mainPanel.dispose();
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(this, "Не удалось подключиться к серверу! . Ошибка - " + exc.getMessage());
            }

        }
    }
}
