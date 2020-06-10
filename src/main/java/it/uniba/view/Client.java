package it.uniba.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Base64;

/**
 * La classe <code>Client</code> implementa i socket del server. Un socket del server attende che le richieste arrivino
 * attraverso il <code>Client</code>. Esegue alcune operazioni in base a tale richiesta e quindi restituisce
 * eventualmente un risultato al richiedente.
 *
 * @author Nicole Stolbovoi
 */

public final class Client {

    static final int PORT_NUMBER = 4000;

    /**
     * Crea un costruttore della classe <code>Client</code> parametrizzato.
     *
     * @param clientName nome del client
     */

    public Client(final String clientName) {

        System.out.println("Client: " + clientName);

        try {
            System.out.println("Apre una comunicazione socket");
            Socket s = new Socket("localhost", PORT_NUMBER);

            // Crea i canali di comunicazione con il Server
            BufferedReader sbr = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream sps = new PrintStream(s.getOutputStream(), true);

            // Console BufferedReader
            BufferedReader cbr = new BufferedReader(new InputStreamReader(System.in));

            // Console PrintStream
            PrintStream cps = new PrintStream(System.out, true);

            loop:
            while (true) {
                // Stampa il messaggio ricevuto
                String response = new String(Base64.getDecoder().decode(sbr.readLine()));
                if (response.contains("username:") || response.contains("password:")) {
                    cps.print(response);
                } else {
                    cps.println("Response:\n" + response.replaceAll("<br>", "\n").replaceAll("\\<[^>]*>",""));
                    cps.print("command (help): ");
                }

                // Lettura del messaggio ricevuto dalla console
                String request = cbr.readLine();

                // Manda il messaggio letto dalla console al Client
                request = Base64.getEncoder().encodeToString(request.getBytes());
                sps.println(request);

                if (request.trim().equals("quit")) {
                    break loop;
                }
            }

            // Disalloca le risorse impiegate: chiude i canali di comunicazione e la connessione con il Server
            cbr.close();
            sps.close();
            sbr.close();
            s.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Istanzia un oggetto di <code>Client</code> che si collegherà al <code>Server</code>.
     * @param args xxx
     */

    public static void main(final String[] args) {
        System.out.println("Client start");
        Client c = new Client("client1");
        System.out.println("Client end");
    }
}
