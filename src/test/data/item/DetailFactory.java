package test.data.item;

import javax.json.JsonObject;

/**
 * La fabrique pour les {@code Item}.
 * @author Fabrice Bouyé
 */
public abstract class DetailFactory {

    public abstract Detail createDetail(final JsonObject jsonObject);

    public DetailFactory getInstance(final Item.Type itemType) {
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
    
    private static ArmorDetail createArmorDetail(final JsonObject jsonObject) {
        final ArmorDetail  result = new ArmorDetail();
//        result.type = 
//        result.weightClass = 
        result.defense = jsonObject.getInt("defense"); // NOI18N.
//        result.infusions = 
//        result.infixUpgrade =
        result.suffixItemId = jsonObject.getInt("suffix_item_id"); // NOI18N.
        result.secondarySuffixItemId = jsonObject.getInt("secondary_suffix_item_id"); // NOI18N.
        return result;
    }
}
