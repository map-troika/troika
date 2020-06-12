package it.uniba.controller;

import it.uniba.model.Action;
import it.uniba.model.Database;
import it.uniba.model.Player;
import it.uniba.model.Plot;

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

public class Game implements Runnable {
    private Socket s;
    private long id;
    private String name;
    private InputStream is;
    private OutputStream os;
    private PrintWriter pw;
    private static int roomId;
    private Plot gLoader;
    private boolean authUser = false;
    private static boolean end = false;
    private boolean isQuit = false;

    Game(final Socket s1) {
        this.s = s1;
        this.roomId = 0;
        this.gLoader = new Plot();
        isQuit = false;
        end = false;
        Player.setIsWinner(false);
        Player.setNItemUse(0);
        Player.resetInventory();
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

    public static void setEnd(final boolean isEnd) {
        end = isEnd;
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
            while (!end) {
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
                        if (Action.home(gLoader)) {
                            response = printRoom(roomId);
                        } else {
                            response = "Comando non disponibile";
                        }
                        break;
                    case "sud":
                    case "nord":
                    case "est":
                    case "ovest":
                        if (Action.goTo(roomId, cp[0])) {
                            response = printRoom(roomId);
                        } else {
                            response = "<font face=\"Impact\" size=\"5\"><b>C'è un muro da questa parte!<br>";
                        }
                        break;
                    case "prendo":
                            if (Action.pickUpItem(gLoader, roomId, cp)) {
                                if (cp.length == 1) {
                                    response =
                                            "<font face=\"Impact\" size=\"5\"><b>Hai raccolto l'oggetto "
                                            + Player.getItemsList().get(Player.getItemsList().size() - 1).getItemName()
                                    + "</b></font>";
                                } else {
                                    response =
                                            "<font face=\"Impact\" size=\"5\"><b>Hai raccolto l'oggetto "
                                                    + cp[1] + "</b></font>";
                                }

                            } else {
                                if (cp.length == 1) {
                                    response =
                                            "<font face=\"Impact\" size=\"5\">" +
                                                    "<b>Specifica un oggetto valido da raccogliere</b></font>";
                                } else {
                                    response = "<font face=\"Impact\" size=\"5\"><b>" +
                                            "In questa stanza non è presente l'oggetto " + cp[1] + "</b></font>";
                                }

                            }
                        break;
                    case "uso":
                        if (Player.getNItemUse() == 2) {
                            response = "<font face=\"Impact\" size=\"5\"><b>Hai già due oggetti in uso</b></font>";
                        } else {
                            if (Action.useItem(cp)) {
                                if (cp.length == 1) {
                                    response = "<font face=\"Impact\" size=\"5\"><b>L'oggetto "
                                            + Player.getItemsList().get(Player.getItemsList().size() - 1).getItemName()
                                            + " è ora in uso</b></font>";
                                } else {
                                    response = "<font face=\"Impact\" size=\"5\"><b>L'oggetto "
                                            + cp[1] + " è ora in uso</b></font>";
                                }
                            } else {
                                if (cp.length == 1) {
                                    response = "<font face=\"Impact\" size=\"5\"><b>"
                                            + "Specifica un oggetto valido da usare</b></font>";
                                } else {
                                    response = "<font face=\"Impact\" size=\"5\"><b>" +
                                            "Nel tuo inventario non è presente l'oggetto " + cp[1] + "</b></font>";
                                }
                            }
                        }
                        break;
                    case "combatto":
                        response = "<font face=\"Impact\" size=\"5\"><b>" + Action.fight(gLoader, roomId)
                                + "</b></font>";
                        break;
                    case "lascio":
                        if (Action.leaveItem(gLoader, roomId, cp)) {
                            if (cp.length == 1) {
                                response = "<font face=\"Impact\" size=\"5\"><b>Hai lasciato l'oggetto "
                                        + gLoader.getPlotRooms().get(roomId).getItemsList()
                                        .get(gLoader.getPlotRooms().get(roomId).getItemsList().size() - 1)
                                        .getItemName() + "</b></font>";
                            } else {
                                response = "<font face=\"Impact\" size=\"5\"><b>Hai lasciato l'oggetto " +
                                        cp[1] + "</b></font>";
                            }
                        } else {
                            if (cp.length == 1) {
                                response = "<font face=\"Impact\" size=\"5\"><b>" +
                                        "Specifica un oggetto valido da lasciare</b></font>";
                            } else {
                                response = "<font face=\"Impact\" size=\"5\"><b>" +
                                        "Nel tuo inventario non è presente l'oggetto " + cp[1] + "</b></font>";
                            }
                        }
                        break;
                    case "aiuto":
                        response = "<font face=\"Impact\" size=\"5\"><b>" + Action.help() + "</b></font>";
                        break;
                    case "posizione":
                        response = "<font face=\"Impact\" size=\"5\"><b>" + Action.position(roomId) + "</b></font>";
                        break;
                    case "inventario":
                        response = "<font face=\"Impact\" size=\"5\"><b>" + Action.showInventory() + "</b></font>";
                        break;
                    case "osservo":
                        response = "<font face=\"Impact\" size=\"5\"><b>" +
                                Action.observeRoom(gLoader, roomId) + "</b></font>";
                        break;
                    case "quit":
                        //response = "quit";
                        //response = Base64.getEncoder().encodeToString(response.getBytes());
                        //pw.println(response);
                        // break loop;
                        end = true;
                        isQuit = true;
                    default:
                        System.out.println("*** Invalid command: " + cmd);
                        response = "<font face=\"Impact\" size=\"5\"><b>" +
                                "me, non so che cz vuoi (" +
                                "<font color='red' face=\"Impact\"><b>" + request +  "</b></font>" +
                                ").  Riprova!" + "</b></font>";
                }
            }
            String exitMessage;
            if (isQuit) {
                exitMessage = "<font face=\"Impact\" size=\"5\"><b>Uscita in corso...</b></font>";
                exitMessage = Base64.getEncoder().encodeToString(exitMessage.getBytes());
                pw.println(exitMessage);
            } else {
                if (Player.getIsWinner()) {
                    exitMessage = "<font face=\"Impact\" size=\"5\"><b>Hai vinto!</b></font>";
                    exitMessage = Base64.getEncoder().encodeToString(exitMessage.getBytes());
                    pw.println(exitMessage);
                } else {
                    exitMessage = "<font face=\"Impact\" size=\"5\"><b>Sei stato ucciso, hai perso!</b></font>";
                    exitMessage = Base64.getEncoder().encodeToString(exitMessage.getBytes());
                    pw.println(exitMessage);
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
        String titleWithNoTag;

        out += "\n" + gLoader.getPlotRooms().get(id1).getTitle() + "\n";

        //rimuovi tag html prima della creazione dei separatori
        titleWithNoTag = gLoader.getPlotRooms().get(id1).getTitle()
                .replaceAll("\\<[^>]*>","");


        out += "-".repeat(titleWithNoTag.length()) + "\n"; // separatori lunghezza titolo
        out += gLoader.getPlotRooms().get(id1).getDescription() + "\n";

        return out;
    }
}
