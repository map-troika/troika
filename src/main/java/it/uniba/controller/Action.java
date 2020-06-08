package it.uniba.controller;

import it.uniba.model.Item;
import it.uniba.model.Player;

import java.util.ArrayList;

public final class Action {
    static final int ID5 = 5;
    private Action() {

    }
    public static String help() {
        String out = "\033[2J\033[H";
        out += "Lista comandi:\n";
        out += "\tprendo:             raccoglie un oggetto\n";
        out += "\tlascio:             lascia un oggetto\n";
        out += "\tposizione:          stanza corrente\n";
        out += "\tinventario:         lista oggetti nell'inventario\n";
        out += "\tosserva:            descrive la stanza\n";
        out += "\tnord/sud/est/ovest: movimenti tra le stanze\n";
        return out;

    }

    public static boolean home(final GameLoader loader) {
        boolean minAlive = false;
        for (Item item : Player.getItemsList()) {
            if (item.getItemName().equals("gomitolo")) {
                if (item.getUse()) {
                    for (Item minotauro : loader.getPlotRooms().get(ID5).getItemsList()) {
                        if (minotauro.getItemName().equals("minotauro")) {
                            minAlive = true;
                            break;
                        }
                    }
                    if (!minAlive) {
                        Game.setRoomId(0);
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }
    public static boolean pickUpItem(final GameLoader loader, final int roomId, final String[] tk) {
        if (tk.length == 1 && loader.getPlotRooms().get(roomId).getItemsList().size() == 1) {
            Item selI = loader.getPlotRooms().get(roomId).getItemsList().get(0);
            Player.addItemInventory(selI);
            loader.getPlotRooms().get(roomId).removeItemRoom(selI);
            return  true;
        }
        else if (tk.length == 1 && loader.getPlotRooms().get(roomId).getItemsList().size() != 1) {
            return false;
        }
        else {
            for (Item item : loader.getPlotRooms().get(roomId).getItemsList()) {
                if (item.getItemName().equals(tk[1])) {
                    Player.addItemInventory(item);
                    loader.getPlotRooms().get(roomId).removeItemRoom(item);
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean leaveItem(final GameLoader loader, final int roomId, final String[] tk) {
        if (tk.length == 1 && Player.getItemsList().size() == 1) {
            Player.getItemsList().get(0).setUse(false);
            Player.setNItemUse(Player.getNItemUse() - 1);
            Item selI = Player.getItemsList().get(0);
            Player.removeItemInventory(selI);
            loader.getPlotRooms().get(roomId).addItemRoom(selI);
            return  true;
        }
        else if (tk.length == 1 && Player.getItemsList().size() != 1) {
            return false;
        }
        else {
            for (Item item : Player.getItemsList()) {
                if (item.getItemName().equals(tk[1])) {
                    item.setUse(false);
                    Player.setNItemUse(Player.getNItemUse() - 1);
                    Player.removeItemInventory(item);
                    loader.getPlotRooms().get(roomId).addItemRoom(item);
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean useItem(final String[] tk) {
        if (tk.length == 1 && Player.getItemsList().size() == 1) {
            if (!Player.getItemsList().get(0).getUse()) {
                Player.setNItemUse(Player.getNItemUse() + 1);
            }
            Player.getItemsList().get(0).setUse(true);
            return true;
        }
        else if (tk.length == 1 && Player.getItemsList().size() != 1) {
            return false;
        }
        else {
            for (Item item : Player.getItemsList()) {
                if (item.getItemName().equals(tk[1])) {
                    if (!item.getUse()) {
                        Player.setNItemUse(Player.getNItemUse() + 1);
                    }
                    item.setUse(true);
                    return true;
                }
            }
            return  false;
        }
    }
    public static String fight(final GameLoader loader, final int roomId) {
        String out = "\033[2J\033[H";
        ArrayList<Item> roomItems = loader.getPlotRooms().get(roomId).getItemsList();
        Item enemy = null;
        Item weapon = null;
        for (int i = 0; i < roomItems.size(); i++) {
            Item itm = roomItems.get(i);
            if (itm.getIsEnemy()) {
                enemy = itm;
            }
        }
        for (int i = 0; i < Player.getItemsList().size(); i++) {
            Item itm = Player.getItemsList().get(i);
            if (itm.getIsWeapon()) {
                weapon = itm;
            }
        }
        if (enemy != null && weapon != null) {
            if (weapon.getUse()) {
                loader.getPlotRooms().get(roomId)
                        .removeItemRoom(enemy);
                out += "Armato di " + weapon.getItemName() + " hai sconfitto il " + enemy.getItemName();
            } else {
                out += "Come pensavi di sconfiggere il " + enemy.getItemName()
                        + " senza l'uso di un' arma";
            }
        }
        else if (enemy != null && weapon == null) {
            out += "Come pensavi di sconfiggere il " + enemy.getItemName()
                    + " senza l'uso di un' arma";
        }
        else {
            out += "Non cè nessuno da combattere qui!";
        }
        return out;
    }
    /*
    public static String oldFight(final GameLoader loader, final int roomId) {
        String out = "\033[2J\033[H";
        out += "In questa stanza non è presente il minotauro";
        if (roomId == ID5) {
            out = "\033[2J\033[H";
            out += "Non possedendo una spada, il minotauro non ti ha lasciato scampo!";
            for (Item item : Player.getItemsList()) {
                if (item.getItemName().equals("spada")) {
                    if (item.getUse()) {
                        out = "\033[2J\033[H";
                        out += "Armato di spada hai sconfitto il minotauro";
                        for (int i = 0; i < loader.getPlotRooms().get(roomId).getItemsList().size(); i++) {

                            if (loader.getPlotRooms().get(roomId).getItemsList().get(i)
                                    .getItemName().equals("minotauro")) {
                                loader.getPlotRooms().get(roomId)
                                        .removeItemRoom(loader.getPlotRooms().get(roomId).getItemsList().get(i));
                            }
                        }
                    } else {
                        out = "\033[2J\033[H";
                        out += "Non usando la spada sei stato sconfitto";
                    }
                }
            }
        }
        return out;
    }
    */

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
    public static String observeRoom(final GameLoader loader, final int roomId) {
        String out = "\033[2J\033[H";
        if (loader.getPlotRooms().get(roomId).getItemsList().size() != 0) {
            out += "guardati intorno: ";
            for (Item item : loader.getPlotRooms().get(roomId).getItemsList()) {
                out += "\n" + item.getDescription();
            }

        } else {
            out = "\033[2J\033[H";
            out += "In questa stanza non cè niente da vedere, continua ad esplorare";
        }
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
