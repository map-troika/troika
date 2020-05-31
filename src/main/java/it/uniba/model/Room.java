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

    public void addExitRoom (String exit, Integer roomId) {
        exits.put(exit, roomId);
    }
    public void addItemRoom (Item item) {
        items.add(item);
    }
    public ArrayList<Item> getItemsList () {
        return items;
    }
}
