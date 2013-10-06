package core;

import utils.Q;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class User extends Thread {
    public Socket getSocket() {
        return socket;
    }

    private Socket socket;
    private KciServer kciServer;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    private String nick = "";//"" - user not register yet

    public User() {
    }

    public User(Socket socket, KciServer kciServer) {
        this.socket = socket;
        this.kciServer = kciServer;
        start();
    }

    @Override
    public void run() {
        super.run();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String strMsg = reader.readLine();
                Message msgFromUser = new Message(strMsg);
                if ((msgFromUser.getType() == Message.Type.RegisterLogin) && (nick.equals(""))) {
                    nick = msgFromUser.getContent();
                    Message msgToUser = new Message(Message.Type.Response);

                    if (nick.equals("")) {
                        msgToUser.setContent(Message.Response.NickNotCorrect.toString());
                    } else {
                        msgToUser.setContent(Message.Response.Ok.toString());

                        String consoleMsg = "Пользователь c ником " + nick + " вошёл в чат";
                        kciServer.getConsole().addMessage(consoleMsg);

                        Message messageToAllClient = new Message(Message.Type.EnteredNewUser);
                        messageToAllClient.setContent(nick);
                        sendMessageToAllOther(messageToAllClient);
                    }

                    msgToUser.sendToSocket(socket);
                }
                if(msgFromUser.getType() == Message.Type.SendMessage){
                    Message messageToAllClient = new Message(Message.Type.SendMessage);
                    messageToAllClient.setContent(msgFromUser.getContent());
                    messageToAllClient.setUser(nick);
                    sendMessageToAllOther(messageToAllClient);
                }

            }
        } catch (SocketException e) {
            if (e.getMessage().equals("Connection reset")) {
                String consoleMsg = "Пользователь c ником " + nick + " сбросил соединение";
                kciServer.getConsole().addMessage(consoleMsg);

                Message messageToAllClient = new Message(Message.Type.LeaveUser);
                messageToAllClient.setContent(nick);
                sendMessageToAllOther(messageToAllClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToAllOther(Message message) {
        for (User user : KciServer.getInstance().getUsers()) {
            if (user != this) {
                Socket userSocket = user.getSocket();
                message.sendToSocket(userSocket);
            }
        }
    }
}
