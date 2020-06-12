package it.uniba.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginRequestGUI implements Runnable {
    private boolean isdataConfirmed = false;

    private JPanel mainPanel;
    private JTextField textFieldCredential;
    private JButton confirmButton;
    private JLabel requestedValue;

    private JFrame frame;

    private String stringUserResponse = "error";

    private final int NUM_MAX_LENGHT_CHAR_JTP=40; //costante numero massimo caratteri textArea

    public LoginRequestGUI (String title) {
        this.startGUI(title);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmCredential();
            }
        });

        textFieldCredential.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    confirmCredential();
                }
            }
        });

        /**
         * listender per verificare limite caratteri
         */
        textFieldCredential.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textFieldCredential.getText().length() >= NUM_MAX_LENGHT_CHAR_JTP ) // limita caratteri
                    e.consume();
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

    private void confirmCredential () {
        //bottone conferma premuto
        stringUserResponse = textFieldCredential.getText(); //set variabile credenziale inserita

        //dati inseriti il thread può terminare
        isdataConfirmed = true;

        //distruggi finestra
        frame.setVisible(false);
        frame.dispose();
    }

    /**
     * codice del thread loginRequest
     */
    @Override
    public void run() {
        while (!isdataConfirmed) {
            try {
                Thread.sleep(1000);
                //System.out.println("Inserimento dati login");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("chiusura thread inserimento credenziale");
    }
}
