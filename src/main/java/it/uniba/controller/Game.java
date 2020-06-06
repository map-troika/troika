package it.uniba.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.Base64;

/**
 * {@inheritDoc}
 * La classe <code>Game</code> implementando l'interfaccia <code>Runnable</code> crea il canale di comunicazione in
 * input ed output, in caso di errori si chiude la connessione. Il metodo <code>run()</code> legge la richiesta da
 * <code>Client</code> e fornisce una risposta.
 *
 * @author Nicole Stolbovoi
 */

class Game implements Runnable {
    private Socket s;
    private long id;
    private String name;
    private InputStream is;
    private OutputStream os;
    private PrintWriter pw;
    private static int roomId;
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

    public static void setRoomId(final int id) {
        roomId = id;
    }

    public String getName() {
        name = Thread.currentThread().getName();
        return name;
    }

    /**
      *  Insert the {@inheritDoc} inline tag in a method main description
      */

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
                System.out.println("*** User: " + username + " logged now");
            }

            //System.out.println("room id = " + gLoader.getPlotRooms().get(roomId).getDescription());
            //response = gLoader.getPlotRooms().get(roomId).getDescription();
            response = printRoom(roomId);

            loop:
            while (true) {
                // Manda un messaggio al Client
                response = Base64.getEncoder().encodeToString(response.getBytes());
                pw.println(response);

                // Riceve una richiesta dal Client
                request = sc.nextLine();

                // Decodifica la stringa ricevuta
                request = new String(Base64.getDecoder().decode(request));

                // Parser
                String[] cp = p.parse(request);
                String cmd = cp[0];

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
                        response = printRoom(roomId);
                        break;
                    case "sud":
                        if (Action.moveSouth(roomId)) {
                            response = printRoom(roomId);
                        } else {
                            response = "C'è un muro da questa parte!";
                        }
                        break;
                    case "nord":

                        if (Action.moveNorth(roomId)) {
                            response = printRoom(roomId);
                        } else {
                            response = "C'è un muro da questa parte!";
                        }
                        break;
                    case "est":

                        if (Action.moveEast(roomId)) {
                            response = printRoom(roomId);
                        } else {
                            response = "C'è un muro da questa parte!";
                        }
                        break;
                    case "ovest":

                        if (Action.moveWest(roomId)) {
                            response = printRoom(roomId);
                        } else {
                            response = "C'è un muro da questa parte!";
                        }
                        break;
                    case "prendo":
                        if (Action.pickUpItem(gLoader, roomId, cp[1])) {
                            response = "Hai raccolto l'oggetto " + cp[1];
                        } else {
                            response = "In questa stanza non è presente l'oggetto " + cp[1];
                        }
                        break;
                    case "uso":
                        if (Action.useItem(cp[1])) {
                            response = "L'oggetto " + cp[1] + " è ora in uso";
                        } else {
                            response = "Nel tuo inventario non è presente l'oggetto " + cp[1];
                        }
                        break;
                    case "combatto":
                        response = Action.fight(roomId);
                        break ;
                    case "lascio":
                        if (Action.leaveItem(gLoader, roomId, cp[1])) {
                            response = "Hai lasciato l'oggetto " + cp[1];
                        } else {
                            response = "Nel tuo inventario non è presente l'oggetto " + cp[1];
                        }
                        break;
                    case "aiuto":
                        response = Action.help();
                        break;
                    case "posizione":
                        response = Action.position(roomId);
                        break;
                    case "inventario":
                        response = Action.showInventory();
                        break;
                    case "osservo":
                        response = Action.observeRoom(roomId);
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
                s.close(); // chiude il socket
            } catch (Exception e) {
            }
        }
    }

    public String printRoom(final int id1) {
        String out = "\033[2J\033[H"; // pulisce lo schermo e va in alto a sinistra

        out += "\n" + gLoader.getPlotRooms().get(id1).getTitle() + "\n";
        out += "-".repeat(gLoader.getPlotRooms().get(id1).getTitle().length()) + "\n"; // separatori lunghezza titolo
        out += gLoader.getPlotRooms().get(id1).getDescription() + "\n";

        return out;
    }
}
