package gui.base;

import utils.GUI;

import javax.swing.*;

/**
 * @author KciRay (http://habrahabr.ru/users/kciray/)
 */
public class KTextField extends JTextField{
    public KTextField() {
        GUI.addDefaultContextMenu(this);
    }
}
