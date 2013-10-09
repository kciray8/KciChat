package gui;

import utils.GUI;
import utils.Q;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainPanel extends JFrame implements ActionListener {
    private JRadioButton rbCreateServer = new JRadioButton("Создать сервер");
    private JRadioButton rbConnectToServer = new JRadioButton("Соединиться с сервером");
    private JPanel selectedPanel = new JPanel();
    private RunServerPanel runServerPanel;
    private ConnectPanel connectToServerPanel;

    public MainPanel() {
        super("KciChat 1.0");
        createMenu();

        //Configure frame
        setSize(500, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        rbCreateServer.addActionListener(this);
        rbConnectToServer.addActionListener(this);

        JPanel rbPanel = GUI.createHPanel(rbCreateServer, rbConnectToServer);
        GUI.groupRadioButtons(rbCreateServer, rbConnectToServer);

        selectedPanel.setLayout(new BoxLayout(selectedPanel, BoxLayout.Y_AXIS));

        JPanel mainPanel = GUI.createVPanel(rbPanel, selectedPanel);
        getContentPane().add(mainPanel);

        runServerPanel = new RunServerPanel(this);
        connectToServerPanel = new ConnectPanel(this);
        rbCreateServer.doClick();

        GUI.toScreenCenter(this);
    }

    private void createMenu() {
    /*
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAbout = new JMenu("О программе");
        menuBar.add(menuAbout);

        setJMenuBar(menuBar);*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rbCreateServer) {
            selectedPanel.remove(connectToServerPanel);
            selectedPanel.add(runServerPanel);
        }
        if (e.getSource() == rbConnectToServer) {
            selectedPanel.remove(runServerPanel);
            selectedPanel.add(connectToServerPanel);
        }

        repaint();
        pack();
    }
}
