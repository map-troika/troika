package it.uniba.model;

import java.util.ArrayList;

public final class Player {

    private Player() {

    }

    private static int nItemUse = 0;

    private static ArrayList<Item> inventory = new ArrayList<Item>();

    public static void addItemInventory(final Item item) {
        inventory.add(item);
    }

    public static void removeItemInventory(final Item item) {
        inventory.remove(item);
    }

    public static ArrayList<Item> getItemsList() {
        return inventory;
    }

    public static int getNItemUse() {
        return nItemUse;
    }

    public static void setNItemUse(int nItemUse1) {
        nItemUse = nItemUse1;
    }
}
