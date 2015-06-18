package test.data.item;

import java.util.List;
import javafx.util.Pair;

/**
 * Definition des infix upgrade.
 * @author Fabrice Bouyé
 */
public final class InfixUpgrade {

    /**
     * Définit un attribut.
     * @author Fabrice Bouyé
     */
    public enum Attribute {

        CONDITION_DAMAGE("condition_damage"), // NOI18N.
        CRIT_DAMAGE("crit_damage"), // NOI18N.
        HEALING("healing"), // NOI18N.
        POWER("power"), // NOI18N.
        PRECISION("precision"), // NOI18N.
        TOUGHNESS("toughness"), // NOI18N.
        VITALITY("vitality"), // NOI18N.
        UNKNOWN(null);

        private final String value;

        Attribute(final String value) {
            this.value = value;
        }

        public static Attribute find(final String value) {
            Attribute result = Attribute.UNKNOWN;
            if (value != null) {
                for (final Attribute toTest : values()) {
                    if (value.equals(toTest.value)) {
                        result = toTest;
                        break;
                    }
                }
            }
            return result;
        }
    }

    List<Pair<Attribute, Integer>> attributes;
    Buff buff;

    /**
     * Crée une nouvelle instance vide.
     */
    InfixUpgrade() {
    }

    public List<Pair<Attribute, Integer>> getAttributes() {
        return attributes;
    }

    public Buff getBuff() {
        return buff;
    }
}
