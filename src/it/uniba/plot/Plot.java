package it.uniba.plot;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;


public class Plot {

    private Map<String, Object> config;
    private ArrayList<String> exitsList;
    private ArrayList<String> itemsList;
    private ArrayList<String> actionsList;
    private ArrayList<Room> roomList;

    public static void main(String[] args) {

        System.out.println("*** Plot main");

        Plot p = new Plot();
        /*
        p.dumpexitsList();
        p.dumpitemsList();
        p.dumpactionsList();
        p.dumpRoomList();
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

        this.exitsList = (ArrayList<String>) config.get("exits");
        this.itemsList = (ArrayList<String>) config.get("items");
        this.actionsList = (ArrayList<String>) config.get("actions");
        this.roomList = (ArrayList<Room>) config.get("room");
    }

    public ArrayList<String> getexitsList() {
        return exitsList;
    }

    public void dumpexitsList() {
        System.out.println("*** exits list = " + exitsList);
    }

    public ArrayList<String> getitemsList() {
        return itemsList;
    }

    public void dumpitemsList() {
        System.out.println("*** items list = " + itemsList);
    }

    public ArrayList<String> getactionsList() {
        return actionsList;
    }

    public void dumpactionsList() {
        System.out.println("*** actions list = " + actionsList);
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public Room getRoom(int id) {
        return new Room(roomList.get(id));
    }

    public String printRoom(int id) {
        String out = "";

        out += "\n" + new Room(roomList.get(id)).title + "\n";
        out +=  "-".repeat((new Room(roomList.get(id)).title).length()) + "\n"; // separatori della lunghezza del titolo
        out += new Room(roomList.get(id)).descr + "\n";

        return out;
    }

    public void dumpRoomList() {

        int ellipses = 20;
        System.out.println("*** room list = ");

        for (int i = 0; i < roomList.size(); i++) {
            Room r = new Room(roomList.get(i));
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

