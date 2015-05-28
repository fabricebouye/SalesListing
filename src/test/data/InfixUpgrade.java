package test.data;

import javafx.util.Pair;

/**
 * Definition des infix upgrade.
 * @author Fabrice Bouyé
 */
public final class InfixUpgrade {

    public enum Attribute {

        CONDITION_DAMAGE,
        CRIT_DAMAGE,
        HEALING,
        POWER,
        PRECISION,
        TOUGHNESS,
        VITALITY,
        UNKNOWN;
    }

    private final Pair<Attribute, Integer>[] attributes;
    private final Buff buff;

    public InfixUpgrade(final Pair<Attribute, Integer>[] attributes, final Buff buff) {
        this.attributes = attributes;
        this.buff = buff;
    }
}
