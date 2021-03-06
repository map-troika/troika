package it.uniba.model;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * <p><code>Entity</code></p> La classe <code>Room</code> rappresenta una stanza della mappa.
 * Questa classe contiene per verificare gli attributi di una stanza.
 */
public class Room {

    private int id;
    private String title;
    private String description;
    private HashMap<String, Integer> exits;
    private ArrayList<Item> items;

    /**
     * Crea un costruttore della classe <code>Room</code> parametrizzato
     *
     * @param roomId id univoco
     * @param roomTitle nome stanza
     * @param roomDescription descrizione stanza
     */
    public Room(
            final int roomId,
            final String roomTitle,
            final String roomDescription
    ) {



        this.id =  roomId;
        this.title = roomTitle;
        this.description = roomDescription;
        this.exits = new HashMap<String, Integer>();
        this.items = new ArrayList<Item>();
    }

    /**
     * La map exits viene riempita con le uscite presenti
     * nella stanza
     *
     * @param exitsRooms Map
     */
    public void setExitRoom(final HashMap<String, Integer> exitsRooms) {
        this.exits = exitsRooms;
    }

    /**
     * Restituisce una Map contenente i riferimenti
     * alle uscite presenti nella stanza
     *
     * @return Map exits
     */
    public HashMap<String, Integer> getExitRoom() {
        return this.exits;
    }

    /**
     * Restituisce l'id della stanza corrispondente
     * al punto cardinale preso in input
     *
     * @param cmd input
     * @return id stanza di destinazione
     */
    public Integer getExitRoom(final String cmd) {
        if (this.exits.containsKey(cmd)) {
            return this.exits.get(cmd);
        } else {
            return null;
        }
    }

    /**
     * Aggiunge l'<code>Item</code> in input alla
     * lista degli oggetti della stanza
     *
     * @param item oggetto da aggiungere
     */
    public void addItemRoom(final Item item) {
        this.items.add(item);
    }

    /**
     * Rimuove l'<code>Item</code> in input dalla
     * lista degli oggetti della stanza
     *
     * @param item oggetto da rimuovere
     */
    public void removeItemRoom(final Item item) {
        this.items.remove(item);
    }

    /**
     * Restituisce una lista degli <code>Item</code>
     * presenti nella stanza
     *
     * @return lista oggetti
     */
    public ArrayList<Item> getItemsList() {
        return this.items;
    }

    /**
     * Restituisce una Hashmap avente come key i punti cardinali
     * e come value l'id univoco di una stanza
     *
     * @return map uscite
     */
    public HashMap<String, Integer> getExits() {
        return this.exits;
    }

    /**
     * Restituisce la descrizione della stanza
     *
     * @return descrizione
     */
    public String getDescription() {
        return description;
    }

    /**
     * Restituisce il nome della stanza
     *
     * @return nome stanza
     */
    public String getTitle() {
        return title;
    }

    /**
     * Restituisce l'id univoco della stanza
     *
     * @return id della stanza
     */
    public int getId() {
        return this.id;
    }
}
