package test.data;

/**
 *
 * @author Fabrice Bouyé
 */
public final class ArmorDetail extends Detail {

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

    public enum WeightClass {

        HEAVY,
        MEDIUM,
        LIGHT,
        CLOTHING,
        UNKNOWN;
    }

    private final Type type;
    private final WeightClass weightClass;
    private final int defense;
    private final InfusionSlot[] infusions;
    private final InfixUpgrade infixUpgrade;
    private final int suffixItemId;
    private final int secondarySuffixItemId;

    public ArmorDetail(final Type type, final WeightClass weightClass, final int defense, InfusionSlot[] infusions, InfixUpgrade infixUpgrade, final int suffixItemId, final int secondarySuffixItemId) {
        super(Item.Type.ARMOR);
        this.type = type;
        this.weightClass = weightClass;
        this.defense = defense;
        this.infusions = infusions;
        this.infixUpgrade = infixUpgrade;
        this.suffixItemId = suffixItemId;
        this.secondarySuffixItemId = secondarySuffixItemId;
    }
}
