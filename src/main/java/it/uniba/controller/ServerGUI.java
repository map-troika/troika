package it.uniba.controller;

import it.uniba.controller.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI {
    private JPanel mainPanel;
    private JButton avviaServerButton;
    private JTextArea outputServerText;


    public ServerGUI() {
        this.startGUI();
        avviaServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //avvia thread server
                Server server = new Server();
                server.runThreadServerInstance();

                //disabilita bottone avvia server
                avviaServerButton.setEnabled(false);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void startGUI () {
        JFrame frame = new JFrame("Server");
        frame.setContentPane(this.mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        //posiziona frame al centro dello schermo
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public void appendOutputServerText (String textToAppend) {
        outputServerText.append(textToAppend);
    }
}
