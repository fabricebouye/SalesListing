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

        CONDITION_DAMAGE("ConditionDamage"), // NOI18N.
        CRIT_DAMAGE("CritDamage"), // NOI18N.
        HEALING("Healing"), // NOI18N.
        POWER("Power"), // NOI18N.
        PRECISION("Precision"), // NOI18N.
        TOUGHNESS("Toughness"), // NOI18N.
        VITALITY("Vitality"), // NOI18N.
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
