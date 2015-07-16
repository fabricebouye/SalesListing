package test.data.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import javafx.util.Pair;
import javax.json.JsonArray;
import javax.json.JsonObject;
import test.query.QueryUtils;

/**
 * La fabrique pour les {@code Item}.
 * @author Fabrice Bouyé
 */
public enum ItemFactory {

    INSTANCE;

    /**
     * Crée une instance de la classe {@code Item}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code Item}, jamais {@code null}.
     */
    public static Item createItem(final JsonObject jsonObject) {
        final Item result = new Item();
        result.id = jsonObject.getInt("id"); // NOI18N.
        result.name = jsonObject.getString("name"); // NOI18N.
        result.description = QueryUtils.fromNullOrMissingString(jsonObject, "description"); // NOI18N.
        final String typeStr = jsonObject.getString("type"); // NOI18N.
        result.type = Item.Type.find(typeStr);
        result.level = jsonObject.getInt("level"); // NOI18N.
        result.rarity = Item.Rarity.find(jsonObject.getString("rarity")); // NOI18N.
        result.vendorValue = jsonObject.getInt("vendor_value"); // NOI18N.
        result.defaultSkin = QueryUtils.fromNullOrMissingInt(jsonObject, "default_skin", Item.NO_SKIN); // NOI18N.
        result.icon = jsonObject.getString("icon"); // NOI18N.
        // Flags
        final JsonArray jsonFlags = jsonObject.getJsonArray("flags"); // NOI18N.
        result.flags = QueryUtils.jsonStringArrayToList(jsonFlags, Item.Flag::find);
        // Type de jeu.
        final JsonArray jsonGameTypes = jsonObject.getJsonArray("game_types"); // NOI18N.
        result.gameTypes = QueryUtils.jsonStringArrayToList(jsonGameTypes, Item.GameType::find);
        // Restrictions.
        final JsonArray jsonRestrictions = jsonObject.getJsonArray("restrictions"); // NOI18N.
        result.restrictions = QueryUtils.jsonStringArrayToList(jsonRestrictions, Item.Restriction::find);
        // Détails (optionnel).
        final Optional<DetailsFactory> detailFactoryOptional = Optional.ofNullable(getDetailFactory(result.type));
        detailFactoryOptional.ifPresent(detailFactory -> {
            final JsonObject jsonDetail = jsonObject.getJsonObject("details"); // NOI18N.
            result.details = detailFactory.createDetails(jsonDetail);
        });
        return result;
    }

    /**
     * Crée une fabrique à {@code Detail}.
     * @param itemType Le type d'objet.
     * @return Une instance de {@code DetailFactory}, peut être {@code null} s'il n'y a pas de fabrique pour ce type d'objet.
     */
    private static DetailsFactory getDetailFactory(final Item.Type itemType) {
        DetailsFactory result = null;
        if (itemType != null) {
            switch (itemType) {
                case ARMOR:
                    result = ItemFactory::createArmorDetails;
                    break;
                case BACK:
                case BAG:
                case CONSUMABLE:
                case CONTAINER:
                case CRAFTING_MATERIAL:
                case GATHERING:
                case GIZMO:
                case MINI_PET:
                case TOOL:
                case TRAIT:
                case TRINKET:
                case TROPHY:
                    break;
                case UPGRADE_COMPONENT:
                    result = ItemFactory::createUpgradeComponentDetails;
                    break;
                case WEAPON:
                    result = ItemFactory::createWeaponDetails;
                    break;
                case UNKNOWN:
                default:
            }
        }
        return result;
    }

    /**
     * Crée une instance de la classe {@code ArmorDetail}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code ArmorDetail}, jamais {@code null}.
     */
    private static ArmorDetails createArmorDetails(final JsonObject jsonObject) {
        final ArmorDetails result = new ArmorDetails();
        result.type = ArmorDetails.Type.find(jsonObject.getString("type")); // NOI18N.
        result.weightClass = ArmorDetails.WeightClass.find(jsonObject.getString("weight_class")); // NOI18N.
        result.defense = jsonObject.getInt("defense"); // NOI18N.
        // Infusions.
        final JsonArray jsonInfusions = QueryUtils.fromNullOrMissingArray(jsonObject, "infusions"); // NOI18N.
        result.infusions = QueryUtils.jsonObjectArrayToList(jsonInfusions, ItemFactory::createInfusionSlot);
        // Amélioration infixe.
        final JsonObject jsonInfixUpgrade = QueryUtils.fromNullOrMissingObject(jsonObject, "infix_upgrade"); // NOI18N.
        result.infixUpgrade = (jsonInfixUpgrade == null) ? null : createInfixUpgrade(jsonInfixUpgrade);
        //
        result.suffixItemId = QueryUtils.fromNullOrMissingInt(jsonObject, "suffix_item_id", Item.NO_ID); // NOI18N.
        result.secondarySuffixItemId = jsonObject.getString("secondary_suffix_item_id"); // NOI18N.
        return result;
    }

    /**
     * Crée une instance de la classe {@code InfusionSlot}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code InfusionSlot}, jamais {@code null}.
     */
    private static InfusionSlot createInfusionSlot(final JsonObject jsonObject) {
        final InfusionSlot result = new InfusionSlot(); // NOI18N.
        result.itemId = QueryUtils.fromNullOrMissingInt(jsonObject, "item_id", Item.NO_ID); // NOI18N.
        final JsonArray jsonFlags = jsonObject.getJsonArray("flags"); // NOI18N.
        result.flags = QueryUtils.jsonStringArrayToList(jsonFlags, InfusionSlot.Flag::find);
        return result;
    }

    /**
     * Crée une instance de la classe {@code InfixUpgrade}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code InfusionSlot}, jamais {@code null}.
     */
    private static InfixUpgrade createInfixUpgrade(final JsonObject jsonObject) {
        final InfixUpgrade result = new InfixUpgrade(); // NOI18N.
        // Attributs.
        final JsonArray jsonFlags = jsonObject.getJsonArray("attributes"); // NOI18N.
        result.attributes = QueryUtils.jsonObjectArrayToList(jsonFlags, object -> {
            final String attributeStr = object.getString("attribute"); // NOI18N.
            final InfixUpgrade.Attribute attribute = InfixUpgrade.Attribute.find(attributeStr);
            final int attributeValue = object.getInt("modifier"); // NOI18N.
            return new Pair<>(attribute, attributeValue);
        });
        // Buff.
        final JsonObject jsonBuff = QueryUtils.fromNullOrMissingObject(jsonObject, "buff"); // NOI18N.
        result.buff = (jsonBuff == null) ? null : createBuff(jsonBuff);
        return result;
    }

    /**
     * Crée une instance de la classe {@code Buff}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code Buff}, jamais {@code null}.
     */
    private static Buff createBuff(final JsonObject jsonObject) {
        final Buff result = new Buff();
        result.skillId = jsonObject.getInt("skill_id"); // NOI18N.
        result.description = jsonObject.getString("description"); // NOI18N.
        return result;
    }

    /**
     * Crée une instance de la classe {@code UpgradeComponentDetail}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code UpgradeComponentDetail}, jamais {@code null}.
     */
    private static UpgradeComponentDetails createUpgradeComponentDetails(final JsonObject jsonObject) {
        final UpgradeComponentDetails result = new UpgradeComponentDetails();
        result.type = UpgradeComponentDetails.Type.find(jsonObject.getString("type")); // NOI18N.
        final JsonArray flags = jsonObject.getJsonArray("flags"); // NOI18N.
        result.flags = QueryUtils.jsonStringArrayToList(flags, UpgradeComponentDetails.Flag::find);
        //
        result.infusionUpgradeFlags = Collections.unmodifiableList(new ArrayList<>());
        // Amélioration infixe.
        final JsonObject jsonInfixUpgrade = QueryUtils.fromNullOrMissingObject(jsonObject, "infix_upgrade"); // NOI18N.
        result.infixUpgrade = (jsonInfixUpgrade == null) ? null : createInfixUpgrade(jsonInfixUpgrade);
        //
        result.suffix = jsonObject.getString("suffix"); // NOI18N.
        return result;
    }

    /**
     * Crée une instance de la classe {@code UpgradeComponentDetail}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code UpgradeComponentDetail}, jamais {@code null}.
     */
    private static WeaponDetails createWeaponDetails(final JsonObject jsonObject) {
        final WeaponDetails result = new WeaponDetails();
        result.type = WeaponDetails.Type.find(jsonObject.getString("type")); // NOI18N.
        result.damageType = WeaponDetails.DamageType.find(jsonObject.getString("damage_type")); // NOI18N.
        result.minPower = jsonObject.getInt("min_power"); // NOI18N.
        result.maxPower = jsonObject.getInt("max_power"); // NOI18N.
        result.defense = jsonObject.getInt("defense"); // NOI18N.
        // Infusions.
        final JsonArray jsonInfusionsSlots = QueryUtils.fromNullOrMissingArray(jsonObject, "infusion_slots"); // NOI18N.
        result.infusionSlots = QueryUtils.jsonObjectArrayToList(jsonInfusionsSlots, ItemFactory::createInfusionSlot);
        // Amélioration infixe.
        final JsonObject jsonInfixUpgrade = QueryUtils.fromNullOrMissingObject(jsonObject, "infix_upgrade"); // NOI18N.
        result.infixUpgrade = (jsonInfixUpgrade == null) ? null : createInfixUpgrade(jsonInfixUpgrade); // NOI18N.
        //
        result.suffixItemId = jsonObject.getInt("suffix_item_id"); // NOI18N.
        result.secondarySuffixItemId = jsonObject.getString("secondary_suffix_item_id"); // NOI18N.
        return result;
    }
}
