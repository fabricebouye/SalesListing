package test.data;

/**
 * Cette classe definit un objet en jeu.
 * @author Fabrice Bouyé
 */
public final class Item {

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

    public enum GameType {

        ACTIVITY,
        DUNGEON,
        PVE,
        PVP,
        WVW,
        UNKNOWN;
    }

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
    private final int id;
    private final String name;
    private final String description;
    private final Type type;
    private final int level;
    private final int vendorValue;
    private final int default_skin;
    private final Flag[] flags;
    private final GameType[] gameTypes;
    private final Restriction[] restrictions;
    private final Detail details;

    private Item(final int id, final String name, final String description, final Type type, final int level, final int vendorValue, final int default_skin, final Flag[] flags, final GameType[] gameTypes, final Restriction[] restrictions, final Detail details) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.level = level;
        this.vendorValue = vendorValue;
        this.default_skin = default_skin;
        this.flags = flags;
        this.gameTypes = gameTypes;
        this.restrictions = restrictions;
        this.details = details;
    }
}
