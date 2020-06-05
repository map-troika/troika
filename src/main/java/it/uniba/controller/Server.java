package it.uniba.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La classe <code>Server</code> estendendo la classe <code>Thread</code> realizza un multithreading, in questo modo il
 * server è in grado di accettare richieste da più client contemporaneamente. Essa implementa i socket del server i quali
 * attendono che le richieste arrivino attraverso il <code>Client</code>. Esegue alcune operazioni in base a tale
 * richiesta e quindi restituisce eventualmente un risultato al richiedente. Il server accetta richieste da più client
 * contemporaneamente.
 *
 * @author Nicole Stolbovoi
 */

public final class Server extends Thread {

    private int port;
    static final int MAX_SESSION = 10;
    static final int PORT_NUMBER = 4000;

    /**
     * Legge in input un array di stringhe contenente una serie di comandi del gioco, per testare che vengano
     * decodificati correttamente. Si crea il canale di comunicazione in
     * output ed il canale di comunicazione in input, in caso di errori si chiude la connessione, altrimenti viene lanciato
     * il metodo che si occuperà della comunicazione.
     *
     * @param args  stringhe da parsificare
     */

    public static void main(final String[] args) throws IOException {
        int count = 0; // 2 ?
        int initialCount = Thread.activeCount(); // 2 ?
        long[] ids = new long[MAX_SESSION];

        InetAddress inetAddress = InetAddress.getLocalHost(); // restituisce l'indirizzo locale
        System.out.println("*** Server IP Address: " + inetAddress.getHostAddress());
        System.out.println("*** Server Host Name: " + inetAddress.getHostName());

        new Server();

        try (ServerSocket ss = new ServerSocket(PORT_NUMBER)) {
            System.out.println("*** Listening...");
            System.out.println("*** initialCount = " + initialCount);

            /*
             * Ciclo infinito in cui il Server è in ascolto sulla porta 4000, ed ogni volta che riceve una richiesta crea
             * i canali di comunicazione per poter comunicare con il Client. Quando il Server riceve una richiesta da un
             * Client crea una nuova istanza di una Socket per quel Client.
             */

            while (true) {
                count++;
                System.out.println("*** Wail connect1 = " + count);
                Socket s = ss.accept(); // si pone in attesa di una richiesta
                                        // arrivata la richiesta la accetta e costituisce una connessione
                Runnable r = new Game(s);
                Thread t = new Thread(r); // Create task (Application)
                t.setName("*** client thread " + count);
                System.out.println("Thread = " + t);
                t.start(); // lancia un nuovo thread
                ids[count] = t.getId();
                System.out.println("*** Now connected: " + count);
                System.out.println("*** Ids: " + ids[count]);

                System.out.println("*** Connected clients: " + (Thread.activeCount() - initialCount));
                System.out.println("*** ids count: " + ids[count]);
            }
        }
    }

    public void server() throws IOException {
        port = PORT_NUMBER;

        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("IP Address:- " + inetAddress.getHostAddress());
        System.out.println("Host Name:- " + inetAddress.getHostName());
    }

}
