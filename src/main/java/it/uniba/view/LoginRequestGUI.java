package it.uniba.view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.Toolkit;
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

    private final int numMaxChar = 40; //costante numero massimo caratteri textArea

    private final int threadSleep = 500;

    public LoginRequestGUI(final String title) {
        this.startGUI(title);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                confirmCredential();
            }
        });

        textFieldCredential.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    confirmCredential();
                }
            }
        });

        /**
         * listender per verificare limite caratteri
         */
        textFieldCredential.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                if (textFieldCredential.getText().length() >= numMaxChar) {
                    e.consume(); // limita caratteri
                }
            }
        });
    }

    /**
     * Inizializza la gui della finestra credenziale
     * @param title titolo della credenziale richiesta
     */
    public void startGUI(final String title) {
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

    /**
     * metodo per ottenere il testo inserito dall'utente del game JTextField
     * @return
     */
    public String getStringUserResponse() {
        return stringUserResponse;
    }

    private void confirmCredential() {
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
                Thread.sleep(threadSleep);
                //System.out.println("Inserimento dati login");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("chiusura thread inserimento credenziale");
    }
}
