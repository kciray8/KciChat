package gui;

import utils.GUI;
import utils.Q;

import javax.swing.*;
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
        add(connectButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connectButton) {
            try {
                String[] ipStr = ipField.getText().split("\\.");
                byte[] ip = new byte[4];

                for(int i=0;i<4;i++){
                    ip[i] = Byte.parseByte(ipStr[i]);
                }
                int port = Integer.parseInt(portField.getText());

                Socket socket = new Socket(InetAddress.getByAddress(ip), port);

                NewUserPanel userPanel = new NewUserPanel(socket);
                userPanel.setVisible(true);

                mainPanel.dispose();
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(this, "Не удалось подключиться к серверу! . Ошибка - " + exc.getMessage());
            }

        }
    }
}
