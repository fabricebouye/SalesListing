package test.data.item;

import java.util.List;

/**
 * Implémente les détails d'une pièce armure.
 * @author Fabrice Bouyé
 */
public final class ArmorDetails extends Details {

    /**
     * Liste des pièces d'armure.
     * @author Fabrice Bouyé
     */
    public enum Type {

        BOOTS("Boots"), // NOI18N.
        COAT("Coat"), // NOI18N.
        GLOVES("Gloves"), // NOI18N.
        HELM("Helm"), // NOI18N.
        HELM_AQUATIC("HelmAquatic"), // NOI18N.
        LEGGINGS("Leggings"), // NOI18N.
        SHOULDERS("Shoulders"), // NOI18N.
        UNKNOWN(null);

        private final String value;

        Type(final String value) {
            this.value = value;
        }

        public static Type find(final String value) {
            Type result = Type.UNKNOWN;
            if (value != null) {
                for (final Type toTest : values()) {
                    if (value.equals(toTest.value)) {
                        result = toTest;
                        break;
                    }
                }
            }
            return result;
        }
    }

    /**
     * Poids de l'armure.
     * @author Fabrice Bouyé
     */
    public enum WeightClass {

        HEAVY("Heavy"), // NOI18N.
        MEDIUM("Medium"), // NOI18N.
        LIGHT("Light"), // NOI18N.
        CLOTHING("Clothing"), // NOI18N.
        UNKNOWN(null);

        private final String value;

        WeightClass(final String value) {
            this.value = value;
        }

        public static WeightClass find(final String value) {
            WeightClass result = WeightClass.UNKNOWN;
            if (value != null) {
                for (final WeightClass toTest : values()) {
                    if (value.equals(toTest.value)) {
                        result = toTest;
                        break;
                    }
                }
            }
            return result;
        }
    }

    /**
     * Type de pièce d'armure.
     */
    Type type;
    /**
     * Poids de l'armure.
     */
    WeightClass weightClass;
    /**
     * Valeur de la defense.
     */
    int defense;
    /**
     * List des emplacement d'infusion.
     */
    List<InfusionSlot> infusions;
    /**
     * Le modificateur infixe.
     */
    InfixUpgrade infixUpgrade;
    /**
     * L'id du suffixe de l'objet.
     */
    int suffixItemId;
    /**
     * ???
     */
    String secondarySuffixItemId;

    /**
     * Crée une nouvelle instance vide.
     */
    ArmorDetails() {
        super(Item.Type.ARMOR);
    }

    public Type getType() {
        return type;
    }

    public WeightClass getWeightClass() {
        return weightClass;
    }

    public int getDefense() {
        return defense;
    }

    public List<InfusionSlot> getInfusions() {
        return infusions;
    }

    public InfixUpgrade getInfixUpgrade() {
        return infixUpgrade;
    }

    public int getSuffixItemId() {
        return suffixItemId;
    }

    public String getSecondarySuffixItemId() {
        return secondarySuffixItemId;
    }

}
