package it.uniba.controller;

import it.uniba.model.Item;
import it.uniba.model.Player;

public class Action {

    public static void pickUpItem(int roomId, Item item) {
        GameLoader loader = new GameLoader();
        Player.addItemInventory(item);
        loader.getPlotRooms().get(roomId).removeItemRoom(item);
    }

    public static void leaveItem(int roomId, Item item) {
        GameLoader loader = new GameLoader();
        Player.removeItemInventory(item);
        loader.getPlotRooms().get(roomId).addItemRoom(item);
    }

    public static void showInventory() {
        System.out.println("Il tuo inventario");
        for(Item i : Player.getItemsList()) {
            System.out.println(i.getItemName() + "-".repeat(i.getItemName().length()));
        }
    }

    public static void observeRoom(int roomId) {
        GameLoader loader = new GameLoader();
        System.out.println(loader.getPlotRooms().get(roomId).getDescription());
    }


}
