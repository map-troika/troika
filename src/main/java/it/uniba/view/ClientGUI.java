package it.uniba.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI {
    private JButton startClientButton;
    private JTextArea outputClientText;
    private JButton endSessionButton;
    private JPanel mainPanel;

    public ClientGUI () {
        this.startGUI();
        startClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                client.runThreadServer();

                startClientButton.setEnabled(false);
            }
        });
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

    public void appendOutputClientText (String textToAppend) {
        outputClientText.append(textToAppend);
    }
}
