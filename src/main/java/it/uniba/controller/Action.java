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

    public static boolean moveNorth(int roomId) {
        GameLoader loader = new GameLoader();
        int destId = -1;
        if (loader.getPlotRooms().get(roomId).getExits().get("nord") != null) {
            destId = loader.getPlotRooms().get(roomId).getExits().get("nord");
            Game.setRoomId(destId);
            return true;
        } else {
            return false;
        }
    }

    public static boolean moveSouth(int roomId) {
        GameLoader loader = new GameLoader();
        int destId = -1;
        if (loader.getPlotRooms().get(roomId).getExits().get("sud") != null) {
            destId = loader.getPlotRooms().get(roomId).getExits().get("sud");
            Game.setRoomId(destId);
            return true;
        } else {
            return false;
        }
    }

    public static boolean moveEast(int roomId) {
        GameLoader loader = new GameLoader();
        int destId = -1;
        if (loader.getPlotRooms().get(roomId).getExits().get("est") != null) {
            destId = loader.getPlotRooms().get(roomId).getExits().get("est");
            Game.setRoomId(destId);
            return true;
        } else {
            return false;
        }
    }

    public static boolean moveWest(int roomId) {
        GameLoader loader = new GameLoader();
        int destId = -1;
        if (loader.getPlotRooms().get(roomId).getExits().get("ovest") != null) {
            destId = loader.getPlotRooms().get(roomId).getExits().get("ovest");
            Game.setRoomId(destId);
            return true;
        } else {
            return false;
        }
    }
}
