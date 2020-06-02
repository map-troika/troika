package it.uniba.model;

import java.util.ArrayList;
import java.util.HashMap;



public class Room {

    private int id;
    private String title;
    private String description;
    private HashMap<String, Integer> exits;
    private ArrayList<Item> items;

    public Room(
            int roomId,
            String roomTitle,
            String roomDescription
    ) {



        this.id =  roomId;
        this.title = roomTitle;
        this.description = roomDescription;
        this.exits = new HashMap<String, Integer>();
        this.items = new ArrayList<Item>();
    }

    public void setExitRoom (HashMap<String, Integer> exitsRooms) {
        this.exits = exitsRooms;
    }
    public void addItemRoom (Item item) {
        this.items.add(item);
    }
    public void removeItemRoom (Item item) {
        this.items.remove(item);
    }
    public ArrayList<Item> getItemsList () {
        return this.items;
    }
    public String getDescription () {
        return description;
    }

    public String getTitle() {
        return title;
    }

    /**
     *
     * @return id della stanza
     */
    public int getId() {
        return this.id;
    }
}
