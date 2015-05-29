package test.data.item;

import java.util.List;

/**
 * Définit un objet en jeu.
 * @author Fabrice Bouyé
 */
public final class Item {

    /**
     * Listes des types d'objets.
     * @author Fabrice Bouyé
     */
    public enum Type {

        ARMOR("armor"), // NOI18N.
        BACK("back"), // NOI18N.
        BAG("bag"), // NOI18N.
        CONSUMABLE("consumable"), // NOI18N.
        CONTAINER("container"), // NOI18N.
        CRAFTING_MATERIAL("crafting_material"), // NOI18N.
        GATHERING("gathering"), // NOI18N.
        GIZMO("gizmo"), // NOI18N.
        MINI_PET("mini_pet"), // NOI18N.
        TOOL("tool"), // NOI18N.
        TRAIT("trait"), // NOI18N.
        TRINKET("trinket"), // NOI18N.
        TROPHY("trophy"), // NOI18N.
        UPGRADE_COMPONENT("upgrade_component"), // NOI18N.
        WEAPON("weapon"), // NOI18N.
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
     * Rareté des objets.
     * @author Fabrice Bouyé
     */
    public enum Rarity {

        JUNK("junk"), // NOI18N.
        BASIC("basic"), // NOI18N.
        FINE("fine"), // NOI18N.
        MASTERWORK("masterwork"), // NOI18N.
        RARE("rare"), // NOI18N.
        EXOTIC("exotic"), // NOI18N.
        ASCENDED("ascendede"), // NOI18N.
        LEGENDARY("legendary"), // NOI18N.
        UNKNOWN(null);

        private final String value;

        Rarity(final String value) {
            this.value = value;
        }

        public static Rarity find(final String value) {
            Rarity result = Rarity.UNKNOWN;
            if (value != null) {
                for (final Rarity toTest : values()) {
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
     * Modes de jeu dans lequel cet objet est autorisé.
     * @author Fabrice Bouyé
     */
    public enum GameType {

        ACTIVITY("activity"), // NOI18N.
        DUNGEON("dungeon"), // NOI18N.
        PVE("pve"), // NOI18N.
        PVP("pvp"), // NOI18N.
        WVW("wvw"), // NOI18N.
        UNKNOWN(null);

        private final String value;

        GameType(final String value) {
            this.value = value;
        }

        public static GameType find(final String value) {
            GameType result = GameType.UNKNOWN;
            if (value != null) {
                for (final GameType toTest : values()) {
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
     * Divers flags relatifs à l'état de l'objet.
     * @author Fabrice Bouyé
     */
    public enum Flag {

        ACCOUNT_BIND_ON_USE("account_bind_on_use"), // NOI18N.
        ACCOUNT_BOUND("account_bound"), // NOI18N.
        HIDE_SUFFIX("hide_suffix"), // NOI18N.
        MONSTER_ONLY("monster_only"), // NOI18N.
        NO_MYSTIC_FORGE("no_mystic_forge"), // NOI18N.
        NO_SALVAGE("no_salvage"), // NOI18N.
        NO_SELL("no_sell"), // NOI18N.
        NOT_UPGRADEABLE("not_upgradeable"), // NOI18N.
        NO_UNDERWATER("no_underwater"), // NOI18N.
        SOULBIND_ON_ACQUIRE("souldbind_on_acquire"), // NOI18N.
        SOULBIND_ON_USE("soulbind_on_use"), // NOI18N.
        UNIQUE("unique"), // NOI18N.
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
     * Autres restrictions en places sur l'objet.
     * @author Fabrice Bouyé
     */
    public enum Restriction {

        ASURA("asura"), // NOI18N.
        CHARR("charr"), // NOI18N.
        HUMAN("human"), // NOI18N.
        NORN("norn"), // NOI18N.
        SYLVARI("sylvari"), // NOI18N.
        GUARDIAN("guardian"), // NOI18N.
        MESMER("mesmer"), // NOI18N.
        RANGER("ranger"), // NOI18N.
        WARRIOR("warrior"), // NOI18N.
        UNKNOWN(null);

        private final String value;

        Restriction(final String value) {
            this.value = value;
        }

        public static Restriction find(final String value) {
            Restriction result = Restriction.UNKNOWN;
            if (value != null) {
                for (final Restriction toTest : values()) {
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
    List<Flag> flags;
    /**
     * Modes de jeu dans lequel cet objet peut être utilisé.
     */
    List<GameType> gameTypes;
    /**
     * Restrictions en place sur l'objet.
     */
    List<Restriction> restrictions;
    /**
     * Détails de l'objet.
     */
    Detail details;

    /**
     * Crée une nouvelle instance vide.
     */
    Item() {
    }
}
