package gui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

/**
 * @author KciRay (http://habrahabr.ru/users/kciray/)
 */
public class RichTextViewer extends JScrollPane{
    JEditorPane editorPane = new JEditorPane();

    public RichTextViewer() {

        editorPane.setEditable(false);
        editorPane.setContentType("text/html");
        editorPane.setVisible(true);

        setViewportView(editorPane);
        setBorder(BorderFactory.createLineBorder(null,0));
    }

    public void addHtml(String html) {
        HTMLDocument doc = (HTMLDocument) editorPane.getDocument();
        HTMLEditorKit editorKit = (HTMLEditorKit) editorPane.getEditorKit();

        try {
            editorKit.insertHTML(doc, doc.getLength(), html, 0, 0, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollBar vertical = getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }
}
