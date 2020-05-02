package clientServer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.Base64;

class Connect implements Runnable {
    private Socket s;
    private int count;
    private long id;
    private String name;
    InputStream is;
    OutputStream os;
    private PrintWriter pw;


    Connect(Socket s, int count) {
        this.s = s;
        this.count = count;
        System.out.println("Connect count = " + count);
    }

    public long getId() {
        id = Thread.currentThread().getId();
        return id;
    }

    public String getName() {
        name = Thread.currentThread().getName();
        return name;
    }

    @Override
    public void run() {
        try {
            is = s.getInputStream();
            os = s.getOutputStream();
        } catch (IOException e) {
            System.err.println(e);
        }
        try (Scanner sc = new Scanner(is)) {
            pw = new PrintWriter(os, true);
            pw.println(Base64.getEncoder().encodeToString(("Connected: \n" + this.getName()).getBytes()));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // Decode recieved string
                line = new String(Base64.getDecoder().decode(line));

                // < il codice applicativo va messo qui, passando line


                if (line.trim().equals("quit")) break;
                // Encode sended string for multiline string
                line = Base64.getEncoder().encodeToString(line.getBytes());
                pw.println(line);
            }
        } finally {
            System.out.println("Client " + this.getName() + " left the session.");
            try {
                s.close();
            } // close socket
            catch (Exception e) {
                ;
            }
        }
    }
}
