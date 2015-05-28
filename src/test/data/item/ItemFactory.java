package test.data.item;

import javax.json.JsonObject;

/**
 * La fabrique pour les {@code Item}.
 * @author Fabrice Bouyé
 */
public enum ItemFactory {

    INSTANCE;

    public static Item createItem(final int id, final JsonObject jsonObject) {
        final Item result = new Item();
        result.id = id;
        result.name = jsonObject.getString("name"); // NOI18N.
        result.description = jsonObject.getString("description"); // NOI18N.
//        result.type = 
        result.level = jsonObject.getInt("level"); // NOI18N.
        result.vendorValue = jsonObject.getInt("vendor_value"); // NOI18N.
        result.defaultSkin = jsonObject.getInt("default_skin"); // NOI18N.
//        result.flags = 
//        result.gameTypes = 
//        result.restrictions = 
//        result.details = 
        return result;
    }
}
