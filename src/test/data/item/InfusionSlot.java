package test.data.item;

/**
 * Implementation de l'emplacement d'infusion.
 * @author Fabrice Bouyé
 */
public final class InfusionSlot {

    public enum Flag {

        DEFENSE,
        OFFENSE,
        UTILITY,
        AGONY,
        UNKNOWN;
    }

    private final int itemId;
    private final Flag[] flags;

    public InfusionSlot(final int itemId, final Flag[] flags) {
        this.itemId = itemId;
        this.flags = flags;
    }
}
