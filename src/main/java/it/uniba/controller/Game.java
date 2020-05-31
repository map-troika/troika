package it.uniba.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.Base64;

import it.uniba.model.Plot;

class Game implements Runnable {
    private Socket s;
    private long id;
    private String name;
    private InputStream is;
    private OutputStream os;
    private PrintWriter pw;
    private int roomId;
    private Plot plot;

    Game(Socket s) {
        this.s = s;
        this.roomId = 0;
        this.plot = new Plot();
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
            String response = "Initial message!";
            String request;

            // System.out.println("room id = " + plot.getRoom(roomId).descr);
            // response = plot.getRoom(roomId).descr;
            //response = plot.printRoom(roomId);

            while (true) {
                // Send message to clieent
                response = Base64.getEncoder().encodeToString(response.getBytes());
                pw.println(response);

                // recieve request from client
                request = sc.nextLine();
                // Decode recieved string
                request = new String(Base64.getDecoder().decode(request));

                if (request.trim().equals("quit")) break;

                if (request.trim().equals("go")) {
                    if (roomId < 6) {
                        roomId++;
                    }
                    // response = plot.getRoom(roomId).descr;
                    //response = plot.printRoom(roomId);
                }
                else if (request.trim().equals("back")) {
                    if (roomId > 0) {
                        roomId--;
                    }
                    //response = plot.getRoom(roomId).descr;
                }
                else
                    // process request and prepare respost
                    response = request;
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
