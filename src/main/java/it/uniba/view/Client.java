package it.uniba.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Base64;

public final class Client implements Runnable {

    private String clientName = "nuovoClient";
    static final int PORT_NUMBER = 4000;

    private static ClientGUI cGUI;

    public static void main(final String[] argv) {
        cGUI = new ClientGUI();

    }

    public Client() {
    }

    @Override
    public void run() {
        System.out.println("Client: " + clientName);

        try {
            cGUI.appendOutputClientText("\n" + "Apre una comunicazione socket");
            Socket s = new Socket("localhost", PORT_NUMBER);
            // Apre i canali di comunicazione e la connessione con il  view

            // Server BufferedReader
            BufferedReader sbr = new BufferedReader(new InputStreamReader(s.getInputStream()));

            // Server PrintStream
            PrintStream sps = new PrintStream(s.getOutputStream(), true);

            // Console BufferedReader
            BufferedReader cbr = new BufferedReader(new InputStreamReader(System.in));

            // Console PrintStream
            PrintStream cps = new PrintStream(System.out, true);



            loop: while (true) {
                String userRequest = "";

                //jframe inserimento credenziali
                LoginRequestGUI credentialRequestGUI;

                // Stampa messagio ricevuto
                String response = new String(Base64.getDecoder().decode(sbr.readLine()));
                if (response.contains("username:") || response.contains("password:")) {

                    //inizializzazione e avvia thread finestra "richiesta credenziale
                    credentialRequestGUI = new LoginRequestGUI(response);
                    Thread reqGUIt = new Thread(credentialRequestGUI, "thread credential req");
                    reqGUIt.start();
                    cGUI.appendOutputClientText("\n" + "in attesa della credenziale"); //imposta titolo
                    while (reqGUIt.isAlive()) {

                        Thread.sleep(2000);
                    }
                    cps.print(credentialRequestGUI.getStringUserResponse());
                    userRequest = credentialRequestGUI.getStringUserResponse();
                    cps.print(response);

                } else {
                    cGUI.appendOutputClientText("\n" + "Response:\n" + response);
                    cGUI.appendOutputClientText("\n" + "command (help): ");
                    userRequest = cbr.readLine();
                }


                // Send messaggio letto dal console a view
                userRequest = Base64.getEncoder().encodeToString(userRequest.getBytes());
                sps.println(userRequest);

                if (userRequest.trim().equals("quit")) {
                    break loop;
                }
            }

            // Chiude i canali di comunicazione e la connessione con il view
            cbr.close();
            sps.close();
            sbr.close();
            s.close();

        } catch (Exception e) {
            try {
                cGUI.appendOutputClientText("\n" + e.getMessage());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    public void runThreadClient () throws InterruptedException {
        cGUI.appendOutputClientText("\n" + "Client start");
        Runnable c = this;
        Thread t = new Thread(c); // Create task (Application)
        t.start();
    }
}
