package test.data.item;

import java.util.Optional;
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
     * @param id L'id de l'objet.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code Item}, jamais {@code null}.
     */
    public static Item createItem(final int id, final JsonObject jsonObject) {
        final Item result = new Item();
        result.id = id;
        result.name = jsonObject.getString("name"); // NOI18N.
        result.description = jsonObject.getString("description"); // NOI18N.
        final String typeStr = jsonObject.getString("type"); // NOI18N.
        result.type = Item.Type.find(typeStr);
        result.level = jsonObject.getInt("level"); // NOI18N.
        result.vendorValue = jsonObject.getInt("vendor_value"); // NOI18N.
        result.defaultSkin = jsonObject.getInt("default_skin"); // NOI18N.
        // Flags
        final JsonArray jsonFlags = jsonObject.getJsonArray("flags"); // NOI18N.
        result.flags = QueryUtils.jsonStringArrayToList(jsonFlags, Item.Flag::find);
        // Type de jeu.
        final JsonArray jsonGameTypes = jsonObject.getJsonArray("game_types"); // NOI18N.
        result.gameTypes = QueryUtils.jsonStringArrayToList(jsonGameTypes, Item.GameType::find);
        // Restrictions.
        final JsonArray jsonRestrictions = jsonObject.getJsonArray("restrictions"); // NOI18N.
        result.restrictions = QueryUtils.jsonStringArrayToList(jsonRestrictions, Item.Restriction::find);
        // Détails.
        final Optional<DetailFactory> detailFactoryOptional = Optional.ofNullable(DetailFactory.getInstance(result.type));
        detailFactoryOptional.ifPresent(detailFactory -> {
            final JsonObject jsonDetail = jsonObject.getJsonObject("details"); // NOI18N.
            result.details = detailFactory.createDetail(jsonDetail);
        });
        return result;
    }
}
