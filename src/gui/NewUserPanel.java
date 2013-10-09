package gui;

import core.Message;
import core.User;
import gui.base.KTextField;
import utils.GUI;
import utils.Q;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author KciRay (http://habrahabr.ru/users/kciray/)
 */
public class NewUserPanel extends JFrame implements ActionListener {
    private final KTextField nickField = new KTextField();
    private final JButton okButton = new JButton("Войти в чат");
    private Socket socket;
    private User newUser = new User();

    public NewUserPanel(Socket socket) throws HeadlessException {
        this.socket = socket;

        JLabel label = new JLabel("Ник");
        nickField.setText("");
        JPanel panel = GUI.createHPanel(label, nickField);

        okButton.addActionListener(this);
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel nickEnterPanel = GUI.createVPanel(panel, okButton);
        getContentPane().add(nickEnterPanel);

        setResizable(false);
        setMinimumSize(new Dimension(300, getMinimumSize().height));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Введите ваши данные");
        GUI.toScreenCenter(this);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            Message msgToServer = new Message(Message.Type.RegisterLogin);
            newUser.setNick(nickField.getText());
            msgToServer.setContent(newUser.getNick());
            msgToServer.sendToSocket(socket);

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Message msgFromServer = new Message(reader.readLine());
                Q.out(msgFromServer);

                if (msgFromServer.isResponse()) {
                    if (msgFromServer.getContent().equals(Message.Response.NickNotCorrect.toString())) {
                        JOptionPane.showMessageDialog(this, "Вы ввели некорректный ник!");
                        return;
                    }
                    if (msgFromServer.getContent().equals(Message.Response.Ok.toString())) {
                        Chat chat = new Chat(newUser, socket);
                        chat.setVisible(true);

                        dispose();
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
