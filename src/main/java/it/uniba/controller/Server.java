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

    private static ServerGUI sGUI;
    public static void main(final String[] args) throws IOException {
        sGUI = new ServerGUI();
    }

    @Override
    public void run() {


        int count = 0; // 2 ?
        int initialCount = Thread.activeCount(); // 2 ?
        long[] ids = new long[MAX_SESSION];

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Server.sGUI.appendOutputServerText("\n" + "*** Server IP Address: " + inetAddress.getHostAddress());
        Server.sGUI.appendOutputServerText("\n" + "*** Server Host Name: " + inetAddress.getHostName());

        new Server();

        try {
            try (ServerSocket ss = new ServerSocket(PORT_NUMBER)) {
                Server.sGUI.appendOutputServerText("\n" + "*** Listening...");
                Server.sGUI.appendOutputServerText("\n" + "*** initialCount = " + initialCount);

                while (true) {
                    count++;
                    Server.sGUI.appendOutputServerText("\n" + "*** Wail connect1 = " + count);
                    Socket s = ss.accept();
                    Runnable r = new Game(s);
                    Thread t = new Thread(r); // Create task (Application)
                    Server.sGUI.appendOutputServerText("\n" + "*** client thread " + count);
                    Server.sGUI.appendOutputServerText("\n" + "Thread = " + t);
                    t.start(); // Launch new Thread
                    ids[count] = t.getId();
                    Server.sGUI.appendOutputServerText("\n" + "*** Now connected: " + count);
                    Server.sGUI.appendOutputServerText("\n" + "*** Ids: " + ids[count]);

                    Server.sGUI.appendOutputServerText("\n" + "*** Connected clients: " + (Thread.activeCount() - initialCount));
                    Server.sGUI.appendOutputServerText("\n" + "*** ids count: " + ids[count]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runThreadServer () {
        Runnable r = this;
        Thread t = new Thread(r); // Create task (Application)
        Server.sGUI.appendOutputServerText("\n" + "***Thread flusso Server");
        t.start();
    }

}
