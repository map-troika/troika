package it.uniba.plot;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Plot {

    private Map<String, Object> config;
    private ArrayList<String> commandList;
    private List<Room> roomList;

    public static void main(String[] args) {
        // write your code here
        System.out.println("Plot Test!");
        Plot p = new Plot();
        // System.out.println(p.getCommanList());
        // System.out.println(p.getRoomList());
    }

    public Plot() {
        System.out.println("Plot constructor!");

        File fin = new File("plot-adventure.yaml");

        // Map<String, Object> xxx = null;

        try (InputStream input = new FileInputStream(fin)) {
            Yaml yaml = new Yaml();
            this.config = yaml.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.commandList = (ArrayList<String>) config.get("command");
/*
        List<Document> docs = obj.get("documents");
        Document doc = new Document(docs.get(0));
 */
        // this.roomList    = (List<Room>) config.get("room");
        // dumpRoomList();
    }

    public ArrayList<String> getCommandList() {
        return commandList;
    }

    public void dumpCommandList() {
        System.out.println("*** command list = " + commandList);
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void dumpRoomList() {
        // System.out.println("*** room list = " + roomList);
        System.out.print("*** room list = [");
        for (int i= 0; i < roomList.size(); i++) {
            Room r = roomList.get(i);
            System.out.println(r + ", ");
        }
        System.out.println("]");
    }

}

