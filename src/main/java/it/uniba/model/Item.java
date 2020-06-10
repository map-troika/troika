package it.uniba.model;

public class Item {
    private String name;
    private String pattern;
    private String description;
    private boolean use;
    private boolean isEnemy;
    private boolean isWeapon;

    /**
     * Crea un costruttore della classe <code>Item</code> parametrizzato
     *
     * @param itemName nome oggetto
     * @param itemPattern pattern oggetto
     */
    public Item(final String itemName, final String itemPattern,
                final String itemDescription, final boolean enemy, final boolean weapon) {
        this.name = itemName;
        this.pattern = itemPattern;
        this.description = itemDescription;
        this.use = false;
        this.isEnemy = enemy;
        this.isWeapon = weapon;
    }

    /**
     * Restituisce il nome univoco dell'item
     *
     * @return name
     */
    public String getItemName() {

        return name;
    }

    /**
     * Restituisce il pattern dell'item
     *
     * @return pattern
     */
    public String getItemPattern() {

        return pattern;
    }

    public String getDescription() {
        return  description;
    }

    public void setUse(final boolean useOn) {
        this.use = useOn;
    }

    public boolean getUse() {
        return use;
    }

    public boolean getIsEnemy() {
        return isEnemy;
    }

    public boolean getIsWeapon() {
        return isWeapon;
    }
}
