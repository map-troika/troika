package it.uniba.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public final class Server extends Thread {

    private int port;
    static final int MAX_SESSION = 10;
    static final int PORT_NUMBER = 4000;

    public static void main(final String[] args) throws IOException {
        int count = 0; // 2 ?
        int initialCount = Thread.activeCount(); // 2 ?
        long[] ids = new long[MAX_SESSION];

        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("*** Server IP Address: " + inetAddress.getHostAddress());
        System.out.println("*** Server Host Name: " + inetAddress.getHostName());

        new Server();

        try (ServerSocket ss = new ServerSocket(PORT_NUMBER)) {
            System.out.println("*** Listening...");
            System.out.println("*** initialCount = " + initialCount);

            while (true) {
                count++;
                System.out.println("*** Wail connect1 = " + count);
                Socket s = ss.accept();
                Runnable r = new Game(s);
                Thread t = new Thread(r); // Create task (Application)
                t.setName("*** client thread " + count);
                System.out.println("Thread = " + t);
                t.start(); // Launch new Thread
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
