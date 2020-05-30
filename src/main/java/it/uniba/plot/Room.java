package it.uniba.plot;

import java.util.ArrayList;
import java.util.Map;


public class Room {

    public int id;
    public String title;
    public String descr;
    public Map<String, Integer> exits;
    public ArrayList<String> items;

    public Room(Object r) {

        Map m = (Map) r;

        this.id = (int) m.get("id");
        this.title = (String) m.get("title");
        this.descr = (String) m.get("descr");
        this.items = (ArrayList<String>) m.get("items");
        this.exits = (Map<String, Integer>) m.get("exits");

    }
}
