package gui;

import core.ChatEventListener;
import core.Message;
import core.User;
import utils.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.Date;

/**
 * @author KciRay (http://habrahabr.ru/users/kciray/)
 */
public class Chat extends JFrame implements ActionListener {
    RichTextViewer chatViewer = new RichTextViewer();
    JTextField messageField = new JTextField();
    JButton sendMessageButton = new JButton("Сказать");
    User user;
    private final ChatEventListener chatEventListener;

    public Socket getSocket() {
        return socket;
    }

    Socket socket;

    public Chat(User user, Socket socket) {
        this.user = user;
        this.socket = socket;
        setSize(new Dimension(600, 400));
        setTitle("Чат");
        addMessage("Чат успешно запущен");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        mainContainer.add(chatViewer);

        messageField.setMaximumSize(new Dimension(500, messageField.getMinimumSize().height));

        JPanel ctrlPanel = GUI.createHPanel(messageField, sendMessageButton);
        mainContainer.add(ctrlPanel);

        sendMessageButton.addActionListener(this);

        GUI.toScreenCenter(this);

        chatEventListener = new ChatEventListener(this);
    }

    public static String getFormattedTime(){
        String time = String.format("[%tT]", new Date());
        return "<span style='color:#010298'>" + time + "</span> ";
    }

    public void addMessage(String str, String color) {
        String htmlText = "";
        htmlText += getFormattedTime();
        htmlText += "<b><span style='color:" + color + "'>" + user.getNick() + "</span></b>: ";
        htmlText += "<span style='color:" + color + "'>" + str + "</span> ";

        chatViewer.addHtml(htmlText);
    }

    public void addMessage(String str) {
        addMessage(str, "#000000");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendMessageButton) {
            String strMessage = messageField.getText();
            if (!strMessage.equals("")) {
                messageField.setText("");
                addMessage(strMessage);

                Message messageToServer = new Message(Message.Type.SendMessage);
                messageToServer.setContent(strMessage);
                messageToServer.sendToSocket(socket);
            }
        }
    }

    public void addHtml(String htmlText) {
        chatViewer.addHtml(htmlText);
    }
}

