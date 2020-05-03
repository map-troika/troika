package it.uniba.server;

import java.io.*;
import java.net.*;
import java.util.Base64;

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

            // Console PrintStream
            PrintStream cps = new PrintStream(System.out, true);

            while(true) {
                // Stampa messagio ricevuto
                // System.out.println("Messaggio Ricevuto undecoded: >>" + sbr.readLine() + "<<");
                String response = new String(Base64.getDecoder().decode(sbr.readLine()));
                System.out.println("Messaggio Ricevuto decoded: " + response);

                // Lettura di un messaggio ricevuto dalla console
                String request = cbr.readLine();

                // Send messaggio letto dal console a server
                request = Base64.getEncoder().encodeToString(request.getBytes());
                sps.println(request);

                if (request.trim().equals("quit")) break;
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