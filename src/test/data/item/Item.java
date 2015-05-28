package test.data.item;

/**
 * Cette classe definit un objet en jeu.
 * @author Fabrice Bouyé
 */
public final class Item {

    /**
     * Listes des types d'objets.
     * @author Fabrice Bouyé
     */
    public enum Type {

        ARMOR,
        BACK,
        BAG,
        CONSUMABLE,
        CONTAINER,
        CRAFTING_MATERIAL,
        GATHERING,
        GIZMO,
        MINI_PET,
        TOOL,
        TRAIT,
        TRINKET,
        TROPHY,
        UPGRADE_COMPONENT,
        WEAPON,
        UNKNOWN;
    }

    /**
     * Rareté des objets.
     * @author Fabrice Bouyé
     */
    public enum Rarity {

        JUNK,
        BASIC,
        FINE,
        MASTERWORK,
        RARE,
        EXOTIC,
        ASCENDED,
        LEGENDARY,
        UNKNOWN;
    }

    /**
     * Modes de jeu dans lequel cet objet est autorisé.
     * @author Fabrice Bouyé
     */
    public enum GameType {

        ACTIVITY,
        DUNGEON,
        PVE,
        PVP,
        WVW,
        UNKNOWN;
    }

    /**
     * Divers flags relatifs à l'état de l'objet.
     * @author Fabrice Bouyé
     */
    public enum Flag {

        ACCOUNT_BIND_ON_USE,
        ACCOUNT_BOUND,
        HIDE_SUFFIX,
        MONSTER_ONLY,
        NO_MYSTIC_FORGE,
        NO_SALVAGE,
        NO_SELL,
        NOT_UPGRADEABLE,
        NO_UNDERWATER,
        SOULBIND_ON_ACQUIRE,
        SOULBIND_ON_USE,
        UNIQUE,
        UNKNOWN;
    }

    /**
     * Autres restrictions en places sur l'objet.
     * @author Fabrice Bouyé
     */
    public enum Restriction {

        ASURA,
        CHARR,
        HUMAN,
        NORN,
        SYLVARI,
        GUARDIAN,
        MESMER,
        RANGER,
        WARRIOR,
        UNKNOWN;
    }
    /**
     * L'id de l'objet.
     */
    int id;
    /**
     * Le nom de l'objet.
     */
    String name;
    /**
     * La description de l'objet.
     */
    String description;
    /**
     * Le type de l'objet.
     */
    Type type;
    /**
     * Le niveau de l'objet.
     */
    int level;
    /**
     * La valeur de revente de l'objet auprès du marchand
     */
    int vendorValue;
    /**
     * L'apparence par défaut de l'objet.
     */
    int defaultSkin;
    /**
     * Flags sur l'objet.
     */
    Flag[] flags;
    /**
     * Modes de jeu dans lequel cet objet peut être utilisé.
     */
    GameType[] gameTypes;
    /**
     * Restrictions en place sur l'objet.
     */
    Restriction[] restrictions;
    /**
     * Détails de l'objet.
     */
    Detail details;

    /**
     * Crée une nouvelle instance.
     */
    Item() {
    }
}
