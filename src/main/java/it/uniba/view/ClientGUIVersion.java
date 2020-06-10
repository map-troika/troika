package it.uniba.view;

import javax.swing.text.BadLocationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Base64;

/**
 * La classe <code>ClientGUIVersion</code> è la versione dell'applicativo <code>Client</code> con
 * interfaccia Swing
 *
 * @author Stefano Romanelli
 */
public final class ClientGUIVersion implements Runnable {

    private String clientName = "nuovoClient";
    static final int PORT_NUMBER = 4000;

    private static ClientGUI cGUI;

    private boolean quitThread = false;

    private PrintStream sps;

    public static void main(final String[] argv) {
        cGUI = new ClientGUI();

    }

    /**
     * reimplementazione di run dell'interfacca Runnable per avviare il client come thread
     */
    @Override
    public void run() {
        System.out.println("Client: " + clientName);


        try {
            cGUI.appendText("\n" + "<br>" + "Apre una comunicazione socket");
            Socket s = new Socket("localhost", PORT_NUMBER);

            // Apre i canali di comunicazione e la connessione con il  view
            BufferedReader sbr = new BufferedReader(new InputStreamReader(s.getInputStream()));
            sps = new PrintStream(s.getOutputStream(), true);

            // Console BufferedReader
            BufferedReader cbr = new BufferedReader(new InputStreamReader(System.in));

            // Console PrintStream
            PrintStream cps = new PrintStream(System.out, true);


            while (!quitThread) {
                //jframe inserimento credenziali
                LoginRequestGUI credentialRequestGUI;

                // Stampa messagio ricevuto
                String response = new String(Base64.getDecoder().decode(sbr.readLine()));
                if (response.contains("username:") || response.contains("password:")) {

                    //inizializzazione e avvia thread finestra "richiesta credenziale
                    credentialRequestGUI = new LoginRequestGUI(response);
                    Thread reqGUIt = new Thread(credentialRequestGUI, "thread credential req");
                    reqGUIt.start();
                    cGUI.appendText("\n" + "<br>" + "in attesa della credenziale"); //imposta titolo
                    while (reqGUIt.isAlive()) {

                        Thread.sleep(2000);
                    }
                    cps.print(credentialRequestGUI.getStringUserResponse());

                    String userCredential = ""; //credenziale inserita dall'utente
                    userCredential = credentialRequestGUI.getStringUserResponse();
                    sendRequestToServer(userCredential);
                    cps.print(response);

                } else {
                    cGUI.clearOutputText();
                    cGUI.appendText("<br>" + "<br>" + "Response:" + "<br>" + response + "<br>");
                    //cGUI.appendText("<br>" + "command (help): ");
                }


                Thread.sleep(100);
            }

            // Disalloca le risorse impiegate: chiude i canali di comunicazione e la connessione con il Server
            cbr.close();
            sps.close();
            sbr.close();
            s.close();
            System.out.println("Client end");

        } catch (Exception e) {
            cGUI.appendText("\n" + "<br>" + e.getMessage());
        }
    }

    public void sendRequestToServer(String userRequest) {
        // Send messaggio letto dal console a view
        userRequest = Base64.getEncoder().encodeToString(userRequest.getBytes());
        sps.println(userRequest);
    }

    public void closeServerComunications() {
        quitThread = true;
    }

    /**
     * Il metodo <code>runThreadClient</code> permette di avviare un flusso che gestisce le operazioni
     * del Client
     *
     * @throws InterruptedException
     */
    public Thread runThreadClient() throws InterruptedException {
        cGUI.appendText("\n" + "<br>" + "Client start");
        Runnable c = this;
        Thread t = new Thread(c); // Create task (Application)
        t.start();
        return t;
    }
}
