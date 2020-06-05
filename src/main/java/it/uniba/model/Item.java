package it.uniba.model;

public class Item {
    private String name;
    private String pattern;

    /**
     * Crea un costruttore della classe <code>Item</code> parametrizzato
     *
     * @param itemName nome oggetto
     * @param itemPattern pattern oggetto
     */
    public Item(String itemName, String itemPattern) {
        this.name = itemName;
        this.pattern = itemPattern;
    }

    /**
     * Restituisce il nome univoco dell'item
     *
     * @return name
     */
    public String getItemName () {

        return name;
    }

    /**
     * Restituisce il pattern dell'item
     *
     * @return pattern
     */
    public String getItemPattern () {

        return pattern;
    }
}
