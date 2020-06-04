package it.uniba.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.Base64;

class Game implements Runnable {
    private Socket s;
    private long id;
    private String name;
    private InputStream is;
    private OutputStream os;
    private PrintWriter pw;
    private int roomId;
    private GameLoader gLoader;
    private boolean authUser = false;

    Game(final Socket s1) {
        this.s = s1;
        this.roomId = 0;
        this.gLoader = new GameLoader();
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
            Parser p = new Parser(gLoader);

            while (!authUser) {
                response = "username: ";
                response = Base64.getEncoder().encodeToString(response.getBytes());
                pw.println(response);
                request = sc.nextLine();
                request = new String(Base64.getDecoder().decode(request));
                String username = request.trim();

                response = "password: ";
                response = Base64.getEncoder().encodeToString(response.getBytes());
                pw.println(response);
                request = sc.nextLine();
                request = new String(Base64.getDecoder().decode(request));
                String password = request.trim();

                Database db = new Database("users.db");
                this.authUser = db.getLogin(username, password);
                System.out.println("*** User: " +  username + " logged now");
            }


            //System.out.println("room id = " + gLoader.getPlotRooms().get(roomId).getDescription());
            //response = gLoader.getPlotRooms().get(roomId).getDescription();
            response = printRoom(roomId);

            loop: while (true) {
                // Send message to clieent
                response = Base64.getEncoder().encodeToString(response.getBytes());
                pw.println(response);

                // recieve request from client
                request = sc.nextLine();

                // Decode recieved string
                request = new String(Base64.getDecoder().decode(request));

                // parser qui
                String cmd = p.parse(request);
                switch (cmd) {

                    case "go":

                        if (roomId < (gLoader.getPlotRooms().size() - 1)) {
                            roomId++;
                        }
                        response = printRoom(roomId);
                        break;

                    case "back":

                        if (roomId > 0) {
                            roomId--;
                        }
                        response = gLoader.getPlotRooms().get(roomId).getDescription();
                        break;

                    case "home":

                        roomId = 0;
                        response = gLoader.getPlotRooms().get(roomId).getDescription();
                        break;

                    case "sud":
                    case "nord":
                    case "est":
                    case "ovest":

                        if (gLoader.getPlotRooms().get(roomId).getExitRoom(cmd) != null) {
                            roomId = gLoader.getPlotRooms().get(roomId).getExitRoom(cmd);
                            response = gLoader.getPlotRooms().get(roomId).getDescription();
                        } else {
                            response = "me, non sai d cz andare (" + request + ").  Riprova!";
                        }
                        break;

                    case "prendo":
                        break;
                    case "uso":
                        break;
                    case "lascio":
                        break;
                    case "aiuto":
                        break;
                    case "posizione":
                        break;
                    case "inventario":
                        break;
                    case "osservo":
                        break;
                    case "quit":
                        response = "quit";
                        response = Base64.getEncoder().encodeToString(response.getBytes());
                        pw.println(response);
                        break loop;
                    default:
                        System.out.println("*** Invalid command: " + cmd);
                        response = "me, non so che cz vuoi (" + request + ").  Riprova!";
                }



            }
        } finally {
            System.out.println("Client " + this.getName() + " left the session.");
            try {
                s.close(); // close socket
            } catch (Exception e) {
            }
        }
    }

    public String printRoom(final int id1) {
        String out = "\033[2J\033[H"; // pulisce schermo e va in alto a sinistra

        out += "\n" + gLoader.getPlotRooms().get(id1).getTitle() + "\n";
        out +=  "-".repeat(gLoader.getPlotRooms().get(id1).getTitle().length()) + "\n"; // separatori lunghezza del titolo
        out += gLoader.getPlotRooms().get(id1).getDescription() + "\n";

        return out;
    }

    public String exeCommand(final String c) {
        switch (c) {
            case "nord":
                break;
            case "prendo":
                break;
            case "uso":
                break;
            case "lascio":
                break;
            case "aiuto":
                break;
            case "posizione":
                break;
            case "inventario":
                break;
            case "osservo":
                break;
            default:
                System.out.println("Invalid command: " + c);
        }
        return c;
    }
}
