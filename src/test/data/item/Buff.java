package test.data.item;

/**
 * Implementation d'un buff.
 * @author Fabrice Bouyé
 */
public final class Buff {

    int skillId;
    String description;

    /**
     * Crée une nouvelle instance vide.
     */
    Buff() {
    }

    public int getSkillId() {
        return skillId;
    }

    public String getDescription() {
        return description;
    }       
}
