package it.uniba.controller;

import it.uniba.model.Item;
import it.uniba.model.Player;

public final class Action {

    static final int ID5 = 5;
    private Action() {

    }
    public static void help() {

    }
    public static void pickUpItem(final int roomId, final Item item) {
        GameLoader loader = new GameLoader();
        Player.addItemInventory(item);
        loader.getPlotRooms().get(roomId).removeItemRoom(item);
    }

    public static void leaveItem(final int roomId, final Item item) {
        GameLoader loader = new GameLoader();
        Player.removeItemInventory(item);
        loader.getPlotRooms().get(roomId).addItemRoom(item);
    }

    /**
     * Restituisce una stringa contenente il nome della stanza corrente
     *
     * @param roomId stanza attuale
     * @return output string
     */
    public static String position(final int roomId) {
        GameLoader loader = new GameLoader();
        String out = "\033[2J\033[H";
        out += "Ti trovi nella stanza: ";
        out += loader.getPlotRooms().get(roomId).getTitle();
        return out;
    }

    /**
     * Restituisce una stringa contenente la lista formattata degli oggetti
     * presenti nell'inventario, o un messaggio d'avviso nel caso l'inventario
     * sia vuoto
     *
     * @return output string
     */
    public static String showInventory() {
      //  GameLoader loader = new GameLoader();
      //  Player.addItemInventory(loader.getPlotRooms().get(1).getItemsList().get(0));
      //  Player.addItemInventory(loader.getPlotRooms().get(3).getItemsList().get(0));
        String out = "\033[2J\033[H";
        if (Player.getItemsList().size() == 0) {
            out += "\n" + "L'inventario è vuoto" + "\n";
        } else {
            out += "Il tuo inventario\n";
            for (Item i : Player.getItemsList()) {
                out += "\n" + i.getItemName() + "\n" + "-".repeat(i.getItemName().length());
            }
        }
        return out;
    }

    /**
     * Restituisce la descrizione della stanza corrente
     *
     * @param roomId stanza corrente
     * @return output string
     */
    public static String observeRoom(final int roomId) {
        GameLoader loader = new GameLoader();
        String out = "\033[2J\033[H";
        out += loader.getPlotRooms().get(roomId).getDescription();
        return out;
    }

    /**
     * Controlla che la stanza corrente abbia una exit a nord
     * e nel caso sia presente compie lo spostamento nella
     * stanza di destinazione, altrimenti restituisce false
     *
     * @param roomId stanza corrente
     * @return true/false
     */
    public static boolean moveNorth(final int roomId) {
        GameLoader loader = new GameLoader();
        if (loader.getPlotRooms().get(roomId).getExits().containsKey("nord")) {
            int destId = loader.getPlotRooms().get(roomId).getExits().get("nord");
            Game.setRoomId(destId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Controlla che la stanza corrente abbia una exit a sud
     * e nel caso sia presente compie lo spostamento nella
     * stanza di destinazione, altrimenti restituisce false
     *
     * @param roomId stanza corrente
     * @return true/false
     */
    public static boolean moveSouth(final int roomId) {
        GameLoader loader = new GameLoader();
        if (loader.getPlotRooms().get(roomId).getExits().containsKey("sud")) {
            int destId = loader.getPlotRooms().get(roomId).getExits().get("sud");
            Game.setRoomId(destId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Controlla che la stanza corrente abbia una exit a est
     * e nel caso sia presente compie lo spostamento nella
     * stanza di destinazione, altrimenti restituisce false
     *
     * @param roomId stanza corrente
     * @return true/false
     */
    public static boolean moveEast(final int roomId) {
        GameLoader loader = new GameLoader();
        if (loader.getPlotRooms().get(roomId).getExits().containsKey("est")) {
            int destId = loader.getPlotRooms().get(roomId).getExits().get("est");
            Game.setRoomId(destId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Controlla che la stanza corrente abbia una exit a ovest
     * e nel caso sia presente compie lo spostamento nella
     * stanza di destinazione, altrimenti restituisce false
     *
     * @param roomId stanza corrente
     * @return true/false
     */
    public static boolean moveWest(final int roomId) {
        GameLoader loader = new GameLoader();
        if (loader.getPlotRooms().get(roomId).getExits().containsKey("ovest")) {
            int destId = loader.getPlotRooms().get(roomId).getExits().get("ovest");
            Game.setRoomId(destId);
            return true;
        } else {
            return false;
        }
    }
}
