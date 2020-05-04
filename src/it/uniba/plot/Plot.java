package it.uniba.plot;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;


public class Plot {

    private Map<String, Object> config;
    private ArrayList<String> directionList;
    private ArrayList<String> objectsList;
    private ArrayList<String> actionList;
    private ArrayList<Room> roomList;

    public static void main(String[] args) {

        System.out.println("*** Plot main");

        Plot p = new Plot();
        /*
        p.dumpdirectionList();
        p.dumpObjectsList();
        p.dumpActionList();
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

        this.directionList = (ArrayList<String>) config.get("direction");
        this.objectsList = (ArrayList<String>) config.get("objects");
        this.actionList = (ArrayList<String>) config.get("action");
        this.roomList = (ArrayList<Room>) config.get("room");
    }

    public ArrayList<String> getdirectionList() {
        return directionList;
    }

    public void dumpdirectionList() {
        System.out.println("*** direction list = " + directionList);
    }

    public ArrayList<String> getObjectsList() {
        return objectsList;
    }

    public void dumpObjectsList() {
        System.out.println("*** objects list = " + objectsList);
    }

    public ArrayList<String> getActionList() {
        return actionList;
    }

    public void dumpActionList() {
        System.out.println("*** action list = " + actionList);
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public Room getRoom(int id) {
        return new Room(roomList.get(id));
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
            System.out.println("\tobjects:\t" + r.objects);
            System.out.println("\tdirection:\t" + r.direction);
        }
    }
}

