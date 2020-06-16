package it.uniba.view;

import java.io.BufferedReader;
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

    private final int threadSleep = 2000;
    private final int threadSleep2 = 100;

    public static void main(final String[] argv) {
        cGUI = new ClientGUI();

    }

    /**
     * reimplementazione di run dell'interfacca Runnable per avviare il client come thread
     */
    @Override
    public void run() {
        System.out.println(
                "<br><font color='orange' face=\"Verdana\"><b>"
                        + "Client: "
                        + "</b></font> "
                        + clientName);
        try {
            cGUI.appendText("\n" + "<br>"
                    + "<font color='orange' face=\"Verdana\"><b>"
                    + "Console:" + "</b></font> "
                    + "Apre una comunicazione socket");
            Socket s = new Socket("localhost", PORT_NUMBER);

            // Apre i canali di comunicazione e la connessione con il  view
            BufferedReader sbr = new BufferedReader(new InputStreamReader(s.getInputStream()));
            sps = new PrintStream(s.getOutputStream(), true);
            // Console BufferedReader
            BufferedReader cbr = new BufferedReader(new InputStreamReader(System.in));
            // Console PrintStream
            PrintStream cps = new PrintStream(System.out, true);

            while (!quitThread) {
                cGUI.startSession(); //avvia sessione lato GUI

                // Jframe inserimento credenziali
                LoginRequestGUI credentialRequestGUI;

                // Stampa messaggio ricevuto
                String response = new String(Base64.getDecoder().decode(sbr.readLine()));
                if (response.contains("username:") || response.contains("password:")) {

                    //inizializzazione e avvia thread finestra "richiesta credenziale
                    credentialRequestGUI = new LoginRequestGUI(response);
                    Thread reqGUIt = new Thread(credentialRequestGUI, "thread credential req");
                    reqGUIt.start();
                    cGUI.appendText("\n" + "<br>"
                            + "<font color='orange' face=\"Verdana\"><b>"
                            + "Console:" + "</b></font> "
                            + "In attesa della credenziale"
                    ); //imposta titolo
                    while (reqGUIt.isAlive()) {

                        // Sospende l’esecuzione del thread corrente e si rende disponibile del tempo per l’esecuzione di altri thread
                        Thread.sleep(threadSleep);
                    }
                    cps.print(credentialRequestGUI.getStringUserResponse());

                    String userCredential = ""; //credenziale inserita dall'utente
                    userCredential = credentialRequestGUI.getStringUserResponse();
                    sendRequestToServer(userCredential);
                    cps.print(response);

                } else if (response.contains("Hai vinto!")) {
                    cGUI.clearOutputText();
                    cGUI.appendText(
                            response.replaceAll("\u001B\\[2J\u001B\\[H", ""));
                    cGUI.appendText("<br>"
                            + "<font color='orange' face=\"Verdana\"><b>"
                            + "Console:" + "</b></font> " + "sessione terminata");
                    cGUI.quitSession();
                    quitThread = true;
                } else if (response.contains("Uscita in corso...")) {
                    cGUI.clearOutputText();
                    cGUI.appendText(
                            response.replaceAll("\u001B\\[2J\u001B\\[H", ""));
                    cGUI.appendText("<br>"
                            + "<font color='orange' face=\"Verdana\"><b>"
                            + "Console:" + "</b></font> " + "sessione terminata");
                    cGUI.quitSession();
                    quitThread = true;
                } else if (response.contains("Sei stato ucciso, hai perso!")) {
                    cGUI.clearOutputText();
                    cGUI.appendText(
                            response.replaceAll("\u001B\\[2J\u001B\\[H", ""));
                    cGUI.appendText("<br>" + "<font color='orange' face=\"Verdana\"><b>"
                            + "Console:" + "</b></font> " + "sessione terminata");
                    cGUI.quitSession();
                    quitThread = true;
                } else {
                    cGUI.clearOutputText();
                    cGUI.appendText(
                            response.replaceAll("\u001B\\[2J\u001B\\[H", "")
                                    .replaceAll("\\{[^{}]*}", "")
                                    .replaceAll("[\\[\\]]", " ")
                    );
                    //cGUI.appendText("<br>" + "command (help): ");
                }


                Thread.sleep(threadSleep2);
            }

            // Disalloca le risorse impiegate: chiude i canali di comunicazione e la connessione con il Server
            cbr.close();
            sps.close();
            sbr.close();
            s.close();
            System.out.println("Client end");

        } catch (Exception e) {
            cGUI.appendText("\n" + "<br>" + "<font color='red' face=\"Verdana\"><b>"
                    + "Console:" + "</b></font> Nessun server attivo");

            cGUI.appendText("<br>" + "<font color='orange' face=\"Verdana\"><b>"
                    + "Console:" + "</b></font> " + "Sessione terminata");
            cGUI.quitSession();


            closeServerComunications();
        }
    }

    public void sendRequestToServer(final String userRequest) {

        // Send messaggio letto dal console a view
        String str = userRequest;
        str = Base64.getEncoder().encodeToString(str.getBytes());
        if (sps != null) {
            sps.println(str);
        }
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
        cGUI.appendText("\n" + "<br>"
                + "<font color='orange' face=\"Verdana\"><b>"
                + "Console:" + "</b></font> " + "Client start");
        Runnable c = this;
        Thread t = new Thread(c); // Create task (Application)
        t.start();
        return t;
    }
}
