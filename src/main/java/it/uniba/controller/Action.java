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

    public static String showInventory() {
      //  GameLoader loader = new GameLoader();
      //  Player.addItemInventory(loader.getPlotRooms().get(1).getItemsList().get(0));
      //  Player.addItemInventory(loader.getPlotRooms().get(3).getItemsList().get(0));
        String out = "\033[2J\033[H";
        if (Player.getItemsList().size() == 0) {
            out += "\n" + "L'inventario è vuoto" + "\n";
        } else {
            out += "Il tuo inventario\n" ;
            for (Item i : Player.getItemsList()) {
                out += "\n" + i.getItemName() + "\n" + "-".repeat(i.getItemName().length());
            }
        }
        return out;
    }

    public static String observeRoom(int roomId) {
        GameLoader loader = new GameLoader();
        String out = "\033[2J\033[H";
        out += loader.getPlotRooms().get(roomId).getDescription();
        return out;
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
