package test.data;

/**
 * Type de base pour le conteneur de détails.
 * @author Fabrice Bouyé
 */
public abstract class Detail {

    private final Item.Type itemType;

    public Detail(final Item.Type itemType) {
        this.itemType = itemType;
    }

    public final Item.Type getItemType() {
        return itemType;
    }
}
