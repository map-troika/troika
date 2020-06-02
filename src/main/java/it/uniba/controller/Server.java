package it.uniba.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread {

    private int port;

    public static void main(String[] args) throws IOException
    {
        int count = 0; // 2 ?
        int initial_count = Thread.activeCount(); // 2 ?
        long[] ids = new long[10];

        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("*** Server IP Address: " + inetAddress.getHostAddress());
        System.out.println("*** Server Host Name: " + inetAddress.getHostName());

        new Server();

        try (ServerSocket ss = new ServerSocket(4000))
        {
            System.out.println("*** Listening...");
            System.out.println("*** initial_count = " + initial_count);

            while(true)
            {
                count++;
                System.out.println("*** Wail connect1 = " + count);
                Socket s = ss.accept();
                Runnable r = new Game(s);
                Thread t = new Thread(r); // Create task (Application)
                t.setName("*** client thread " + count);
                System.out.println("Thread = " + t);
                t.start(); // Launch new Thread
                ids[count] = t.getId();
                System.out.println("*** Now connected: "+ count);
                System.out.println("*** Ids: "+ ids[count]);

                System.out.println("*** Connected clients: " + (Thread.activeCount() - initial_count));
                System.out.println("*** ids count: " + ids[count]);

            }
        }
    }

    public void Server() throws IOException {
        port = 4000;

        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("IP Address:- " + inetAddress.getHostAddress());
        System.out.println("Host Name:- " + inetAddress.getHostName());
    }

}
