package it.uniba.view;

import it.uniba.controller.Server;
import it.uniba.controller.ServerGUI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Base64;

public final class Client implements Runnable {

    private String clientName;
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
                // Stampa messagio ricevuto
                String response = new String(Base64.getDecoder().decode(sbr.readLine()));
                if (response.contains("username:") || response.contains("password:")) {
                    cps.print(response);
                } else {
                    cps.println("Response:\n" + response);
                    cps.print("command (help): ");
                }

                // Lettura di un messaggio ricevuto dalla console
                String request = cbr.readLine();

                // Send messaggio letto dal console a view
                request = Base64.getEncoder().encodeToString(request.getBytes());
                sps.println(request);

                if (request.trim().equals("quit")) {
                    break loop;
                }
            }

            // Chiude i canali di comunicazione e la connessione con il view
            cbr.close();
            sps.close();
            sbr.close();
            s.close();

        } catch (Exception e) {
            cGUI.appendOutputClientText("\n" + e.getMessage());
        }
    }

    public void runThreadServer () {
        cGUI.appendOutputClientText("\n" + "Client start");
        Runnable c = this;
        Thread t = new Thread(c); // Create task (Application)
        t.start();
    }
}
