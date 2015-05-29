package test.data.item;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.util.Pair;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import test.data.item.InfixUpgrade.Attribute;

/**
 * La fabrique abstraite pour les {@code Item}.
 * @author Fabrice Bouyé
 */
public abstract class DetailFactory {

    /**
     * Crée une nouvelle instance.
     */
    protected DetailFactory() {
    }

    /**
     * Crée une instance de la classe {@code Detail}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code Detail}, jamais {@code null}.
     */
    public abstract Detail createDetail(final JsonObject jsonObject);

    /**
     * Crée une fabrique à {@code Detail}.
     * @param itemType Le type d'objet.
     * @return Une instance de {@code DetailFactory}, peut être {@code null} s'il n'y a pas de fabrique pour ce type d'objet.
     */
    public static DetailFactory getInstance(final Item.Type itemType) {
        DetailFactory result = null;
        if (itemType != null) {
            switch (itemType) {
                case ARMOR:
                    result = new DetailFactory() {

                        @Override
                        public Detail createDetail(final JsonObject jsonObject) {
                            return createArmorDetail(jsonObject);
                        }
                    };
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
                    result = new DetailFactory() {

                        @Override
                        public Detail createDetail(final JsonObject jsonObject) {
                            return createUpgradeComponentDetail(jsonObject);
                        }
                    };
                    break;
                case WEAPON:
                case UNKNOWN:
                default:
            }
        }
        return result;
    }

    /**
     * Crée une instance de la classe {@code Detail} pour les armures.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code ArmorDetail}, jamais {@code null}.
     */
    private static ArmorDetail createArmorDetail(final JsonObject jsonObject) {
        final ArmorDetail result = new ArmorDetail();
        result.type = ArmorDetail.Type.find(jsonObject.getString("type")); // NOI18N.
        result.weightClass = ArmorDetail.WeightClass.find(jsonObject.getString("weight-class")); // NOI18N.
        result.defense = jsonObject.getInt("defense"); // NOI18N.
        // Infusions.
        final JsonArray jsonInfusions = jsonObject.getJsonArray("infusions"); // NOI18N.
        final List<InfusionSlot> infusions = jsonInfusions.getValuesAs(JsonObject.class)
                .stream()
                .map(jsonInfusion -> createInfusionSlot(jsonInfusion))
                .collect(Collectors.toList());
        result.infusions = Collections.unmodifiableList(infusions);
        // Amélioration infixe.
        final JsonObject jsonInfixUpgrade = jsonObject.getJsonObject("infix_upgrade"); // NOI18N.
        result.infixUpgrade = createInfixUpgrade(jsonInfixUpgrade);
        //
        result.suffixItemId = jsonObject.getInt("suffix_item_id"); // NOI18N.
        result.secondarySuffixItemId = jsonObject.getInt("secondary_suffix_item_id"); // NOI18N.
        return result;
    }

    /**
     * Crée un emplacement d'infusion.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code InfusionSlot}, jamais {@code null}.
     */
    private static InfusionSlot createInfusionSlot(final JsonObject jsonObject) {
        final InfusionSlot result = new InfusionSlot(); // NOI18N.
        result.itemId = jsonObject.getInt("item_id");
        final JsonArray jsonFlags = jsonObject.getJsonArray("flags"); // NOI18N.
        final List<InfusionSlot.Flag> flags = jsonFlags.getValuesAs(JsonString.class)
                .stream()
                .map(jsonFlag -> InfusionSlot.Flag.find(jsonFlag.getString()))
                .collect(Collectors.toList());
        result.flags = Collections.unmodifiableList(flags);
        return result;
    }

    /**
     * Crée une amélioration infixe.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code InfusionSlot}, jamais {@code null}.
     */
    private static InfixUpgrade createInfixUpgrade(final JsonObject jsonObject) {
        final InfixUpgrade result = new InfixUpgrade(); // NOI18N.
        // Attributs.
        final JsonArray jsonFlags = jsonObject.getJsonArray("attribute"); // NOI18N.
        final List<Pair<Attribute, Integer>> flags = jsonFlags.getValuesAs(JsonArray.class)
                .stream()
                .map(array -> {
                    final String attributeStr = array.getString(0);
                    final Attribute attribute = InfixUpgrade.Attribute.find(attributeStr);
                    final int attributeValue = array.getInt(1);
                    return new Pair<>(attribute, attributeValue);
                })
                .collect(Collectors.toList());
        result.attributes = Collections.unmodifiableList(flags);
        // Buff.
        final JsonObject jsonBuff = jsonObject.getJsonObject("buff"); // NOI18N.
        result.buff = createBuff(jsonBuff);
        return result;
    }

    /**
     * Crée un buff.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code Buff}, jamais {@code null}.
     */
    private static Buff createBuff(final JsonObject jsonObject) {
        final Buff result = new Buff();
        result.skillId = jsonObject.getString("skill_id"); // NOI18N.
        result.description = jsonObject.getString("description"); // NOI18N.
        return result;
    }

    /**
     * Crée une instance de la classe {@code Detail} pour les composants d'amélioration.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code UpgradeComponentDetail}, jamais {@code null}.
     */
    private static UpgradeComponentDetail createUpgradeComponentDetail(final JsonObject jsonObject) {
        final UpgradeComponentDetail result = new UpgradeComponentDetail();
        return result;
    }
}
