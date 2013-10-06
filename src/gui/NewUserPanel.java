package gui;

import core.Message;
import core.User;
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
    private final JTextField nickField = new JTextField();
    private final JButton okButton = new JButton("Войти в чат");
    private Socket socket;
    private User newUser = new User();

    public NewUserPanel(Socket socket) throws HeadlessException {
        this.socket = socket;
        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Ник");
        nickField.setText("");
        JPanel panel = GUI.createHPanel(label, nickField);

        mainContainer.add(panel);
        okButton.addActionListener(this);
        add(okButton);

        setSize(300, 90);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Введите ваши данные");
        GUI.toScreenCenter(this);
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

                if(msgFromServer.isResponse()){
                    if(msgFromServer.getContent().equals(Message.Response.NickNotCorrect.toString())){
                        JOptionPane.showMessageDialog(this, "Вы ввели некорректный ник!");
                        return;
                    }
                    if (msgFromServer.getContent().equals(Message.Response.Ok.toString())) {
                        Chat chat = new Chat(newUser,socket);
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
