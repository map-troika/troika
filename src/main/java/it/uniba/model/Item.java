package it.uniba.model;

public class Item {
    private String name;
    private String pattern;

    public Item(String itemName, String itemPattern) {
        this.name = itemName;
        this.pattern = itemPattern;
    }

    public String getItemName () {

        return name;
    }

    public String getItemPattern () {

        return pattern;
    }
}
