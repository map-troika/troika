package it.uniba.model;

/**
 * <p><code>Entity</code></p> La classe <code>Item</code> rappresenta un oggetto del gioco, con
 * un costruttore che ne definisce la tipologia (isWeapon, isEnemy).
 * Questa classe contiene metodi per verificare e modificare lo stato di un item.
 */
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
     * @param itemName        nome oggetto
     * @param itemPattern     pattern oggetto
     * @param itemDescription descrizione oggetto
     * @param enemy           è/non è nemico
     * @param weapon          è/non è un'arma
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

    /**
     * Restituisce la descrizione dell'item
     *
     * @return description
     */
    public String getDescription() {
        return  description;
    }

    /**
     * Setta l'attributo use a true/false
     *
     * @param useOn attivo/disattivo
     */
    public void setUse(final boolean useOn) {
        this.use = useOn;
    }

    /**
     * Restituisce vero/falso a seconda che l'item
     * sia in uso o meno
     *
     * @return use
     */
    public boolean getUse() {
        return use;
    }

    /**
     * Restituisce vero/falso a seconda che l'item
     * sia un nemico o meno
     *
     * @return isEnemy
     */
    public boolean getIsEnemy() {
        return isEnemy;
    }

    /**
     * Restituisce vero/falso a seconda che l'item
     * sia un'arma o meno
     *
     * @return isWeapon
     */
    public boolean getIsWeapon() {
        return isWeapon;
    }
}
