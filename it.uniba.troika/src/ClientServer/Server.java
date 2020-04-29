package ClientServer;

import java.net.*;

public class Server extends Thread {
    private ServerSocket Server;

    public static void main(String argv[]) throws Exception {
        new Server();
    }

    public Server() throws Exception {
        // Il client effettua una connessione con il server creando un nuovo oggetto Socket
        Server = new ServerSocket(4000);
        System.out.println("Il Server è in attesa sulla porta 4000.");
        this.start();
    }

    public void run() {
        while (true) {
            try {
                System.out.println("In attesa di Connessione.");
                Socket client = Server.accept();
                System.out.println("Connessione accettata da: " +
                        client.getInetAddress());
                Connect c = new Connect(client);
            } catch (Exception e) {
            }
        }
    }
}
