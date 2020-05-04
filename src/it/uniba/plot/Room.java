package it.uniba.plot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Room {

    public int id;
    public String title;
    public String descr;
    public Map<String, Integer> direction;
    public ArrayList<String> objects;

    public Room(Object r) {

        Map m = (Map) r;

        this.id = (int) m.get("id");
        this.title = (String) m.get("title");
        this.descr = (String) m.get("descr");
        this.objects = (ArrayList<String>) m.get("objects");
        this.direction = (Map<String, Integer>) m.get("direction");

    }
}
