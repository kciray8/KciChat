package core;

import gui.Chat;
import utils.Q;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author KciRay (http://habrahabr.ru/users/kciray/)
 */
public class ChatEventListener extends Thread {
    private Chat chat;


    public ChatEventListener(Chat chat) {
        this.chat = chat;
        start();
    }

    @Override
    public void run() {
        super.run();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(chat.getSocket().getInputStream()));

            while (true) {
                Message message = new Message(reader.readLine());
                if(message.getType() == Message.Type.EnteredNewUser){
                    String newUserName = message.getContent();

                    String htmlText = "<i>Пользователь <b>" + newUserName + "</b> вошёл в чат</i>";

                    chat.addHtml(htmlText);
                }
                if (message.getType() == Message.Type.LeaveUser) {
                    String name = message.getContent();

                    String htmlText = "<i>Пользователь <b>" + name + "</b> покинул чат</i>";

                    chat.addHtml(htmlText);
                }
                if (message.getType() == Message.Type.SendMessage) {
                    String htmlText = Chat.getFormattedTime()+ " <b>"+message.getUser()+"</b>: "+message.getContent();

                    chat.addHtml(htmlText);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
