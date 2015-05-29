package test.data.item;

/**
 * Type de base pour le conteneur de détails des objets.
 * @author Fabrice Bouyé
 */
public abstract class Detail {

    /**
     * Le type de détails.
     */
    private final Item.Type itemType;

    /**
     * Crée une nouvelle instance.
     * @param itemType Le type de détails.
     */
    public Detail(final Item.Type itemType) {
        this.itemType = itemType;
    }

    /**
    * Retourne le type des details.
    * @return Une instance de {@code Item.Type}, jamais {@code null}.
    */
    public final Item.Type getItemType() {
        return itemType;
    }
}
