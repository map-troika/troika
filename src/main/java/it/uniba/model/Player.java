package it.uniba.model;

import java.util.ArrayList;

/**
 * <p><code>Entity</code></p> La classe <code>Player</code> è statica e rappresenta l'utente del gioco.
 * Questa classe contiene metodi per verificare e modificare lo stato del Player.
 */
public final class Player {
    /**
     * Crea un costruttore privato di <code>Player</code>
     */
    private Player() {

    }

    private static int nItemUse = 0;
    private static boolean isWinner = false;
    private static ArrayList<Item> inventory = new ArrayList<Item>();

    /**
     * Aggiunge l'item passato in input alla lista degli item del Player
     *
     * @param item item da aggiungere
     */
    public static void addItemInventory(final Item item) {
        inventory.add(item);
    }

    /**
     * Rimuove l'item passato in input dalla lista degli item del Player
     *
     * @param item item da rimuovere
     */
    public static void removeItemInventory(final Item item) {
        inventory.remove(item);
    }

    /**
     * Restituisce l'inventario del Player
     *
     * @return ArrayList
     */
    public static ArrayList<Item> getItemsList() {
        return inventory;
    }

    /**
     * Restituisce il numero di oggetti in uso dal Player nel contesto di gioco attuale
     *
     * @return int
     */
    public static int getNItemUse() {
        return nItemUse;
    }

    /**
     * Imposta il numero di oggetti in uso dal Player al valore intero preso come parametro
     *
     * @param nItemUse1 int
     */
    public static void setNItemUse(final int nItemUse1) {
        nItemUse = nItemUse1;
    }

    /**
     * Imposta la variabile isWinner al valore booleano preso come parametro
     *
     * @param winner true/false
     */
    public static void setIsWinner(final boolean winner) {
        isWinner = winner;
    }

    /**
     * Restituisce il valore di isWinner
     *
     * @return isWinner
     */
    public static boolean getIsWinner() {
        return isWinner;
    }

    public static void resetInventory() {
        inventory = new ArrayList<>();
    }
}
