package gui;

import core.KciServer;
import utils.GUI;
import utils.Q;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * @author KciRay (http://habrahabr.ru/users/kciray/)
 */
public class ServerConsole extends JFrame {
    private RichTextViewer textConsole = new RichTextViewer();

    public ServerConsole(KciServer server) {
        setSize(new Dimension(600, 400));
        setTitle("Консоль сервера");
        GUI.toScreenCenter(this);

        add(textConsole);

        addMessage("Сервер успешно запущен!");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void addMessage(String str, String color) {
        String htmlText = "";
        String time = String.format("[%tT]", new Date());
        htmlText += "<span style='color:#010298'>" + time + "</span> ";
        htmlText += "<span style='color:" + color + "'>" + str + "</span> ";

        textConsole.addHtml(htmlText);
    }

    public void addMessage(String str) {
        addMessage(str,"#000000");
    }
}

