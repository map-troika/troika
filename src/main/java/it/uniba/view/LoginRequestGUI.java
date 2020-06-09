package it.uniba.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRequestGUI implements Runnable {
    private boolean isdataConfirmed = false;

    private JPanel mainPanel;
    private JTextField textFieldCredenziale;
    private JButton confermaButton;
    private JLabel requestedValue;

    private JFrame frame;

    private String stringUserResponse = "error";

    public LoginRequestGUI (String title) {
        this.startGUI(title);
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //bottone conferma premuto
                stringUserResponse = textFieldCredenziale.getText(); //set variabile credenziale inserita

                //dati inseriti il thread può terminare
                isdataConfirmed = true;

                //distruggi finestra
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    public void startGUI (String title) {
        frame = new JFrame(title);
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

    public String getStringUserResponse() {
        return stringUserResponse;
    }

    @Override
    public void run() {
        while (!isdataConfirmed) {
            try {
                Thread.sleep(1000);
                System.out.println("Inserimento dati login");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("chiusura thread inserimento credenziale");
    }
}
