package it.uniba.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public final class Server implements Runnable {

    private int port;
    static final int MAX_SESSION = 10;
    static final int PORT_NUMBER = 4000;

    public static void main(final String[] args) throws IOException {
        Runnable r = new Server();
        Thread t = new Thread(r); // Create task (Application)
        t.setName("***Thread flusso Server");
        t.start();
    }

    @Override
    public void run() {
        System.out.println("prova");
        int count = 0; // 2 ?
        int initialCount = Thread.activeCount(); // 2 ?
        long[] ids = new long[MAX_SESSION];

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("*** Server IP Address: " + inetAddress.getHostAddress());
        System.out.println("*** Server Host Name: " + inetAddress.getHostName());

        new Server();

        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void server() throws IOException {
        port = PORT_NUMBER;

        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("IP Address:- " + inetAddress.getHostAddress());
        System.out.println("Host Name:- " + inetAddress.getHostName());
    }

}
