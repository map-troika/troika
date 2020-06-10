package it.uniba.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * La classe <code>Server</code> estendendo la classe <code>Thread</code> realizza un multithreading, in questo modo il
 * server è in grado di accettare richieste da più client contemporaneamente. Essa implementa i socket del server i quali
 * attendono che le richieste arrivino attraverso il <code>Client</code>. Esegue alcune operazioni in base a tale
 * richiesta e quindi restituisce eventualmente un risultato al richiedente. Per ogni connessione viene istanziata una
 * classe <code>Game</code>.
 *
 * @author Nicole Stolbovoi
 */

public final class Server implements Runnable {

    static final int MAX_SESSION = 10;
    static final int PORT_NUMBER = 4000;

    /**
     * Fa partire un'istanza del <code>Server</code> che rimane in attesa che un Client si connetta.
     *
     * @param args non viene utilizzato, si potrebbe utilizzare ad esempio per impostare la porta
     * @throws IOException xxx
     */

    public static void main(final String[] args) throws IOException {
        //avvia thread server
        Server server = new Server();
        server.runThreadServer();
    }

    @Override
    public void run() {


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
             * Client crea una nuova istanza di un Game per quel Client.
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
