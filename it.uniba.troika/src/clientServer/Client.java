package clientServer;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String argv[]) {
        System.out.println("Client start");
        Client c = new Client("client1");
        System.out.println("Client end");
    }

    public Client(String client_name) {

        System.out.println("Client: " + client_name);

        try {
            System.out.println("Apre una comunicazione socket");
            Socket s = new Socket("localhost", 4000);
            // Apre i canali di comunicazione e la connessione con il  server

            // Server BufferedReader
            BufferedReader sbr = new BufferedReader(new InputStreamReader(s.getInputStream()));

            // Server PrintStream
            PrintStream sps = new PrintStream(s.getOutputStream(), true);

            // Console BufferedReader
            BufferedReader cbr = new BufferedReader(new InputStreamReader(System.in));

            // Server PrintStream
            PrintStream cps = new PrintStream(System.out, true);

            System.out.println("Messaggio Ricevuto : " + sbr.readLine());
            while(true) {
                // Lettura di un messaggio ricevuto dalla console
                String console = cbr.readLine();

                // Send messaggio letto dal console a server
                sps.println(console);

                // Lettura di un messaggio ricevuto dal server
                String message = sbr.readLine();
                cps.println("Messaggio Ricevuto : " + message);
                if (console.trim().equals("quit")) break;
            }

            // Chiude i canali di comunicazione e la connessione con il server
            cbr.close();
            sps.close();
            sbr.close();
            s.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}