package test.data.item;

/**
 * Implémente les détails d'une armure.
 * @author Fabrice Bouyé
 */
public final class ArmorDetail extends Detail {

    /**
     * Listes des pièces d'armure.
     * @author Fabrice Bouyé
     */
    public enum Type {

        BOOTS,
        COAT,
        GLOVES,
        HELM,
        HELM_AQUATIC,
        LEGGINS,
        SHOULDERS,
        UNKNOWN;
    }

    /**
     * Poids de l'armure.
     * @author Fabrice Bouyé
     */
    public enum WeightClass {

        HEAVY,
        MEDIUM,
        LIGHT,
        CLOTHING,
        UNKNOWN;
    }

    Type type;
    WeightClass weightClass;
    int defense;
    InfusionSlot[] infusions;
    InfixUpgrade infixUpgrade;
    int suffixItemId;
    int secondarySuffixItemId;

    ArmorDetail() {
        super(Item.Type.ARMOR);
    }
}
