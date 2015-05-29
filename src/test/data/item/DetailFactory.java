package test.data.item;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * La fabrique abstraite pour les {@code Item}.
 * @author Fabrice Bouyé
 */
public abstract class DetailFactory {

    /**
     * Crée une instance de la classe {@code Detail}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code Detail}, jamais {@code null}.
     */
    public abstract Detail createDetail(final JsonObject jsonObject);

    /**
     * Cree une fabrique à {@code Detail}.
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
                        public Detail createDetail(JsonObject jsonObject) {
                            return createArmorDetail(jsonObject);
                        }
                    };
                    break;
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
//        result.infusions = Collections.unmodifiableList();
        final JsonObject jsonInfixUpgrade = jsonObject.getJsonObject("infix_upgrade"); // NOI18N.
//        result.infixUpgrade =
        result.suffixItemId = jsonObject.getInt("suffix_item_id"); // NOI18N.
        result.secondarySuffixItemId = jsonObject.getInt("secondary_suffix_item_id"); // NOI18N.
        return result;
    }
}
