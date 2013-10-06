package core;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.Socket;

/**
 * @author KciRay (http://habrahabr.ru/users/kciray/)
 */
public class Message {
    public enum Type {RegisterLogin, Response, SendMessage, EnteredNewUser, LeaveUser}

    public enum Response {Ok, NickAlreadyUse, NickNotCorrect}

    public Type getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String user = "";
    private Type type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content = "";

    public Message(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message[Type= " + type + " ,content= " + content + " ]";
    }

    public Message(String str) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new StringReader(str));
            Element root = doc.getRootElement();
            type = Type.valueOf(root.getAttribute("type").getValue());
            content = root.getAttribute("content").getValue();
            if (root.getAttribute("user") != null) {
                user = root.getAttribute("user").getValue();
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isResponse() {
        return (type == Type.Response);
    }


    private String toXML() {
        Element root = new Element("message");
        root.setAttribute("type", type.toString());
        root.setAttribute("content", content);
        if (!user.equals("")) {
            root.setAttribute("user", user);
        }
        Document doc = new Document(root);

        String xmlString = new XMLOutputter().outputString(doc);
        //Replace /n or /r/n to ' '
        xmlString = xmlString.replace(System.getProperty("line.separator"), " ");

        return xmlString;
    }

    public void sendToSocket(Socket socket) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            //Our message end by \n
            outputStream.write((toXML() + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
