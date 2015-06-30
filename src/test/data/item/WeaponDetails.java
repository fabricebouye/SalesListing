package test.data.item;

import java.util.List;

/**
 * Implémente les détails d'une arme.
 * @author Fabrice Bouyé
 */
public final class WeaponDetails extends Details {

    /**
     * Listes des types d'armes.
     * @author Fabrice Bouyé
     */
    public enum Type {

        AXE("Axe"), // NOI18N.
        LONG_BOW("LongBow"), // NOI18N.
        DAGGER("Dagger"), // NOI18N.
        FOCUS("Focus"), // NOI18N.
        GREATSWORD("Greatsword"), // NOI18N.
        HAMMER("Hammer"), // NOI18N.
        HARPOON("Harpoon"), // NOI18N.
        MACE("Mace"), // NOI18N.
        PISTOL("Pistol"), // NOI18N.
        RIFLE("Rifle"), // NOI18N.
        SCEPTER("Scepter"), // NOI18N.
        SHIELD("Shield"), // NOI18N.
        SHORT_BOW("ShortBow"), // NOI18N.
        SPEARGUN("Speargun"), // NOI18N.
        STAFF("Staff"), // NOI18N.
        SWORD("Sword"), // NOI18N.
        TORCH("Torch"), // NOI18N.
        TRIDENT("Trident"), // NOI18N.
        WARHORN("Warhorn"), // NOI18N.
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
     * Listes des types de degats.
     * @author Fabrice Bouyé
     */
    public enum DamageType {

        PHYSICAL("Physical"), // NOI18N.
        UNKNOWN(null);

        private final String value;

        DamageType(final String value) {
            this.value = value;
        }

        public static DamageType find(final String value) {
            DamageType result = DamageType.UNKNOWN;
            if (value != null) {
                for (final DamageType toTest : values()) {
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
     * Le type d'arme.
     */
    Type type;
    /**
     * Type de degats.
     */
    DamageType damageType;
    /**
     * Puissance minimale.
     */
    int minPower;
    /**
     * Puissance maximale.
     */
    int maxPower;
    /**
     * Defense.
     */
    int defense;
    /**
     * Liste des emplacement d'infusion.
     */
    List<InfusionSlot> infusionSlots;
    /**
     * Le modificateur infixe.
     */
    InfixUpgrade infixUpgrade;
    int suffixItemId;
    String secondarySuffixItemId;

    /**
     * Crée une nouvelle instance vide.
     */
    WeaponDetails() {
        super(Item.Type.WEAPON);
    }

    public Type getType() {
        return type;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public int getMinPower() {
        return minPower;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public int getDefense() {
        return defense;
    }

    public List<InfusionSlot> getInfusionSlots() {
        return infusionSlots;
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
