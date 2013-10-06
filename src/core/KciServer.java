package core;

import gui.NewUserPanel;
import gui.ServerConsole;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * @author KciRay (http://habrahabr.ru/users/kciray/)
 */
public class KciServer extends ServerSocket implements Runnable {
    private Thread thread;

    public LinkedList<User> getUsers() {
        return users;
    }

    private LinkedList<User> users = new LinkedList<User>();

    public static KciServer getInstance() {
        return instance;
    }

    private static KciServer instance;

    public void setConsole(ServerConsole console) {
        this.console = console;
    }

    public ServerConsole getConsole() {
        return console;
    }

    private ServerConsole console;

    public KciServer(int port) throws IOException {
        super(port);

        thread = new Thread(this);
        thread.start();
        instance = this;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = accept();
                User user = new User(socket,this);
                users.add(user);
                if(console != null){
                    console.addMessage("Принят сокет - "+socket);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
