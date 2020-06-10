package it.uniba.controller;

import it.uniba.model.Item;
import it.uniba.model.Player;

import java.util.ArrayList;

/**
 * <p><code>Controller</code></p> La classe <code>Action</code> è una classe statica e contiene tutti i metodi
 * corrispondenti alle azioni possibili da effettuare in gioco.
 */
public final class Action {
    /**
     * Crea costruttore privato della classe <code>Action</code>
     */
    private Action() {

    }

    /**
     * Restituisce una stringa formattata contenente l'intero menu help
     * con nome dei comandi e relative descrizioni.
     *
     * @return string
     */
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

    /**
     * Imposta la stanza corrente alla stanza iniziale del gioco,
     * a condizione che si possegga e sia un uso un oggetto specifico.
     *
     * @param loader game loader
     * @return true/false
     */
    public static boolean home(final GameLoader loader) {
        for (Item item : Player.getItemsList()) {
            if (item.getItemName().equals("gomitolo")) {
                if (item.getUse()) {
                        Game.setRoomId(0);
                        if (Player.getIsWinner()) {
                            Game.setEnd(true);
                        }
                        return true;
                    }
                }
                break;
            }
        return false;
    }

    /**
     * Se nella stanza corrente c'è un solo item, viene preso e messo nell'inventario anche senza essere esplicitato;
     * Se ci sono più item, è necessario specificare l'item da prendere.
     *
     * @param loader game loader
     * @param roomId stanza corrente
     * @param tk lista token input
     * @return true/false
     */
    public static boolean pickUpItem(final GameLoader loader, final int roomId, final String[] tk) {
        if (tk.length == 1 && loader.getPlotRooms().get(roomId).getItemsList().size() == 1) {
            Item selI = loader.getPlotRooms().get(roomId).getItemsList().get(0);
            Player.addItemInventory(selI);
            loader.getPlotRooms().get(roomId).removeItemRoom(selI);
            return  true;
        } else if (tk.length == 1 && loader.getPlotRooms().get(roomId).getItemsList().size() != 1) {
            return false;
        } else {
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

    /**
     * Se nell'inventario c'è un solo item, viene lasciato nella stanza corrente anche senza essere esplicitato;
     * Se ci sono più item, è necessario specificare l'item da lasciare.
     *
     * @param loader game loader
     * @param roomId stanza corrente
     * @param tk lista token input
     * @return true/false
     */
    public static boolean leaveItem(final GameLoader loader, final int roomId, final String[] tk) {
        if (tk.length == 1 && Player.getItemsList().size() == 1) {
            Player.getItemsList().get(0).setUse(false);
            Player.setNItemUse(Player.getNItemUse() - 1);
            Item selI = Player.getItemsList().get(0);
            Player.removeItemInventory(selI);
            loader.getPlotRooms().get(roomId).addItemRoom(selI);
            return  true;
        } else if (tk.length == 1 && Player.getItemsList().size() != 1) {
            return false;
        } else {
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

    /**
     * Se nell'inventario c'è un solo item, viene settato in uso anche senza essere esplicitato;
     * Se ci sono più item, è necessario specificare l'item da usare
     *
     * @param tk lista token input
     * @return true/false
     */
    public static boolean useItem(final String[] tk) {
        if (tk.length == 1 && Player.getItemsList().size() == 1) {
            if (!Player.getItemsList().get(0).getUse()) {
                Player.setNItemUse(Player.getNItemUse() + 1);
            }
            Player.getItemsList().get(0).setUse(true);
            return true;
        } else if (tk.length == 1 && Player.getItemsList().size() != 1) {
            return false;
        } else {
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

    /**
     * Restituisce una stringa variabile dinamicamente in base all'esito dello scontro
     * e al nemico
     *
     * @param loader game loader
     * @param roomId stanza corrente
     * @return string
     */
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
                Player.setIsWinner(true);
            } else {
                out += "Come pensavi di sconfiggere il " + enemy.getItemName()
                        + " senza l'uso di un' arma";
                Game.setEnd(true);
            }
        } else if (enemy != null && weapon == null) {
            out += "Come pensavi di sconfiggere il " + enemy.getItemName()
                    + " senza l'uso di un' arma";
            Game.setEnd(true);
        } else {
            out += "Non cè nessuno da combattere qui!";
        }
        return out;
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
     * Controlla che la stanza corrente abbia una exit passata come parametro
     * e nel caso sia presente compie lo spostamento nella
     * stanza di destinazione, altrimenti restituisce false
     *
     * @param roomId stanza corrente
     * @param cmd exit di destinazione
     * @return true/false
     */
    public static boolean goTo(final int roomId, final String cmd) {
        GameLoader gLoader = new GameLoader();
        if (gLoader.getPlotRooms().get(roomId).getExitRoom(cmd) != null) {
            int destId = gLoader.getPlotRooms().get(roomId).getExitRoom(cmd);
            Game.setRoomId(destId);
            if (destId == 0 && Player.getIsWinner()) {
                Game.setEnd(true);
            }
            return true;
        } else {
            return false;
        }
    }
}
