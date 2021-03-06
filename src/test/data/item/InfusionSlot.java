package test.data.item;

import java.util.List;

/**
 * Implementation de l'emplacement d'infusion.
 * @author Fabrice Bouyé
 */
public final class InfusionSlot {

    public enum Flag {

        DEFENSE("Defense"), // NOI18N.
        OFFENSE("Offense"), // NOI18N.
        UTILITY("Utility"), // NOI18N.
        AGONY(""), // NOI18N.
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

    int itemId;
    List<Flag> flags;

    /**
     * Crée une nouvelle instance vide.
     */
    InfusionSlot() {
    }

    public int getItemId() {
        return itemId;
    }

    public List<Flag> getFlags() {
        return flags;
    }
}
