package it.uniba.model;

import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class Plot {

    private Map<String, Object> config;
    private ArrayList<String> commandsList;
    private ArrayList<String> itemsList;
    private ArrayList<String> actionsList;
    private ArrayList<Room>   roomsList;

    public static void main(String[] args) {
        System.out.println("*** Plot main");

        Plot p = new Plot();
        /*
        p.dumpCommandsList();
        p.dumpItemsList();
        p.dumpActionsList();
        p.dumpRoomsList();
        */
    }

    public Plot() {
        System.out.println("*** Plot constructor");

        File fin = new File("plot-adventure.yaml");

        Map<String, Object> temp = null;

        try (InputStream input = new FileInputStream(fin)) {
            Yaml yaml = new Yaml();
            this.config = yaml.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.commandsList = (ArrayList<String>) config.get("commands");
        this.itemsList = (ArrayList<String>) config.get("items");
        this.actionsList = (ArrayList<String>) config.get("actions");
        this.roomsList = (ArrayList<Room>) config.get("rooms");
    }

    public ArrayList<String> getCommandsList() {
        return commandsList;
    }

    public void dumpCommandsList() {
        System.out.println("*** commands list = " + commandsList);
    }

    public ArrayList<String> getItemsList() {
        return itemsList;
    }

    public void dumpItemsList() {
        System.out.println("*** items list = " + itemsList);
    }

    public ArrayList<String> getActionsList() {
        return actionsList;
    }

    public void dumpActionsList() {
        System.out.println("*** actions list = " + actionsList);
    }

    public ArrayList<Room> getRoomsList() {
        return roomsList;
    }

    public Room getRoom(int id) {
        return new Room(roomsList.get(id));
    }

    public String printRoom(int id) {
        String out = "\033[2J\033[H"; // pulisce schermo e va in alto a sinistra

        out += "\n" + new Room(roomsList.get(id)).title + "\n";
        out +=  "-".repeat((new Room(roomsList.get(id)).title).length()) + "\n"; // separatori lunghezza del titolo
        out += new Room(roomsList.get(id)).descr + "\n";

        return out;
    }

    public void dumpRoomsList() {
        int ellipses = 20;
        System.out.println("*** room list = ");

        for (int i = 0; i < roomsList.size(); i++) {
            Room r = new Room(roomsList.get(i));
            System.out.println("===");
            System.out.println("\tid:\t\t\t" + r.id);
            System.out.println("\ttitle:\t\t" + r.title);
            System.out.println("\tdescr:\t\t" + (
                            r.descr.length() > ellipses
                            ? (r.descr.substring(0, ellipses - 3) + "...")
                            : r.descr
                    )
            );
            System.out.println("\titems:\t" + r.items);
            System.out.println("\texits:\t" + r.exits);
        }
    }
}

