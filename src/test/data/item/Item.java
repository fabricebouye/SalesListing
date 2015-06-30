package test.data.item;

import java.util.List;

/**
 * Définit un objet en jeu.
 * @author Fabrice Bouyé
 */
public final class Item {

    public static final int NO_ID = -1;
    public static final int NO_SKIN = -1;

    /**
     * Listes des types d'objets.
     * @author Fabrice Bouyé
     */
    public enum Type {

        ARMOR("Armor"), // NOI18N.
        BACK("Back"), // NOI18N.
        BAG("Bag"), // NOI18N.
        CONSUMABLE("Consumable"), // NOI18N.
        CONTAINER("Container"), // NOI18N.
        CRAFTING_MATERIAL("CraftingMaterial"), // NOI18N.
        GATHERING("Gathering"), // NOI18N.
        GIZMO("Gizmo"), // NOI18N.
        MINI_PET("MiniPet"), // NOI18N.
        TOOL("Tool"), // NOI18N.
        TRAIT("Trait"), // NOI18N.
        TRINKET("Trinket"), // NOI18N.
        TROPHY("Trophy"), // NOI18N.
        UPGRADE_COMPONENT("UpgradeComponent"), // NOI18N.
        WEAPON("Weapon"), // NOI18N.
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

        JUNK("Junk"), // NOI18N.
        BASIC("Basic"), // NOI18N.
        FINE("Fine"), // NOI18N.
        MASTERWORK("Masterwork"), // NOI18N.
        RARE("Rare"), // NOI18N.
        EXOTIC("Exotic"), // NOI18N.
        ASCENDED("Ascended"), // NOI18N.
        LEGENDARY("Legendary"), // NOI18N.
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

        ACTIVITY("Activity"), // NOI18N.
        DUNGEON("Dungeon"), // NOI18N.
        PVE("Pve"), // NOI18N.
        PVP("Pvp"), // NOI18N.
        PVP_LOBBY("PvpLobby"), // NOI18N.
        WVW("Wvw"), // NOI18N.
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

        ACCOUNT_BIND_ON_USE("AccountBindOnUse"), // NOI18N.
        ACCOUNT_BOUND("AccountBound"), // NOI18N.
        HIDE_SUFFIX("HideSuffix"), // NOI18N.
        MONSTER_ONLY("MonsterOnly"), // NOI18N.
        NO_MYSTIC_FORGE("NoMysticForge"), // NOI18N.
        NO_SALVAGE("NoSalvage"), // NOI18N.
        NO_SELL("NoSell"), // NOI18N.
        NOT_UPGRADEABLE("NotUpgradeable"), // NOI18N.
        NO_UNDERWATER("NoUnderwater"), // NOI18N.
        SOUL_BIND_ON_ACQUIRE("SoulBindOnAcquire"), // NOI18N.
        SOUL_BIND_ON_USE("SoulBindOnUse"), // NOI18N.
        UNIQUE("Unique"), // NOI18N.
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

        ASURA("Asura"), // NOI18N.
        CHARR("Charr"), // NOI18N.
        HUMAN("Human"), // NOI18N.
        NORN("Norn"), // NOI18N.
        SYLVARI("Sylvari"), // NOI18N.
        GUARDIAN("Guardian"), // NOI18N.
        MESMER("Mesmer"), // NOI18N.
        RANGER("Ranger"), // NOI18N.
        WARRIOR("Warrior"), // NOI18N.
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
     * Rareté de l'objet.
     */
    Rarity rarity;
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
     * L'URL de l'icon de l'objet.
     */
    String icon;
    /**
     * Détails de l'objet.
     */
    Details details;

    /**
     * Crée une nouvelle instance vide.
     */
    Item() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getVendorValue() {
        return vendorValue;
    }

    public int getDefaultSkin() {
        return defaultSkin;
    }

    public List<Flag> getFlags() {
        return flags;
    }

    public List<GameType> getGameTypes() {
        return gameTypes;
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public String getIcon() {
        return icon;
    }

    public Details getDetails() {
        return details;
    }
}
