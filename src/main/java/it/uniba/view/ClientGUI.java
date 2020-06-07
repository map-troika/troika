package it.uniba.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ClientGUI {
    private JPanel mainPanel;


    private JTextArea outputClientText;
    private JButton startClientButton;
    private JButton endSessionButton;
    private JScrollPane scrollBar;


    public ClientGUI () {
        this.startGUI();
        startClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                try {
                    client.runThreadClient();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                startClientButton.setEnabled(false);
            }
        });
        endSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void startGUI () {

        JFrame frame = new JFrame("Client");
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

    public void appendOutputClientText (String textToAppend) throws InterruptedException {
        outputClientText.append(textToAppend);
        scroll();
    }

    public void scroll() throws InterruptedException {
        Thread.sleep(10);
        //scrolla alla fine della JTextArea
        this.scrollBar.getVerticalScrollBar().setValue(this.scrollBar.getVerticalScrollBar().getMaximum());
    }
}
