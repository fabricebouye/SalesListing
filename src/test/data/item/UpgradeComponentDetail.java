package test.data.item;

import java.util.List;

/**
 * Implémente les détails d'une amélioration (rune, cachet, etc.).
 * @author Fabrice Bouyé
 */
public final class UpgradeComponentDetail extends Detail {

    /**
     * Listes des types d'améliorations.
     * @author Fabrice Bouyé
     */
    public enum Type {

        SIGIL("Sigil"), // NOI18N.
        RUNE("Rune"), // NOI18N.
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
     * Listes des flags.
     * @author Fabrice Bouyé
     */
    public enum Flag {

        AXIE("Axe"), // NOI18N.
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
        SHORT_BOW("ShortBox"), // NOI18N.
        SPEARGUN("Speargun"), // NOI18N.
        STAFF("Staff"), // NOI18N.
        SWORD("Sword"), // NOI18N.
        TORCH("Torch"), // NOI18N.
        TRIDENT("Trident"), // NOI18N.
        WARHORN("Warhorn"), // NOI18N.
        UNKNOWN(null);

        private final String value;

        Flag(final String value) {
            this.value = value;
        }

        public static Flag find(final String value) {
            Flag result = Flag.UNKNOWN;
            if (value != null) {
                for (final Flag toTest : values()) {
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
     * Le type d'amélioration.
     */
    Type type;
    
    List<Flag> flags;
    /**
     * Liste des emplacement d'infusion.
     */
    List<Object> infusionUpgradeFlags;
    /**
     * Le modificateur infixe.
     */
    InfixUpgrade infixUpgrade;
    String suffix;
    
    /**
     * Crée une nouvelle instance vide.
     */
    UpgradeComponentDetail() {
        super(Item.Type.UPGRADE_COMPONENT);
    }

    public Type getType() {
        return type;
    }

    public List<Flag> getFlags() {
        return flags;
    }

    public List<Object> getInfusionUpgradeFlags() {
        return infusionUpgradeFlags;
    }

    public InfixUpgrade getInfixUpgrade() {
        return infixUpgrade;
    }

    public String getSuffix() {
        return suffix;
    }
}
