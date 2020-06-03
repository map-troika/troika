package it.uniba.model;

import java.util.ArrayList;

public class Player {

    private static ArrayList<Item> inventory = new ArrayList<Item>();

    public static void addItemInventory(Item item) {
        inventory.add(item);
    }

    public static void removeItemInventory(Item item) {
        inventory.remove(item);
    }

    public static ArrayList<Item> getItemsList () {
        return inventory;
    }
}
