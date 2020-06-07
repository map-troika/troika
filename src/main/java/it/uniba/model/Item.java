package it.uniba.model;

public class Item {
    private String name;
    private String pattern;
    private String description;
    private boolean use;

    /**
     * Crea un costruttore della classe <code>Item</code> parametrizzato
     *
     * @param itemName nome oggetto
     * @param itemPattern pattern oggetto
     */
    public Item(String itemName, String itemPattern, String itemDescription) {
        this.name = itemName;
        this.pattern = itemPattern;
        this.description = itemDescription;
        this.use = false;
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

    public String getDescription() {
        return  description;
    }

    public void setUse(boolean useOn) {
        this.use = useOn;
    }

    public boolean getUse() {
        return use;
    }
}
