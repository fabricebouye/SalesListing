package test.data.item;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;

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
        final JsonArray jsonFlags = jsonObject.getJsonArray("flags"); // NOI18N.
        // Flags
        final List<Item.Flag> flags = jsonFlags.getValuesAs(JsonString.class)
                .stream()
                .map(jsonString -> Item.Flag.find(jsonString.getString()))
                .collect(Collectors.toList());
        result.flags = Collections.unmodifiableList(flags);
        // Type de jeu.
        final JsonArray jsonGameTypes = jsonObject.getJsonArray("game_types"); // NOI18N.
        final List<Item.GameType> gameTypes = jsonGameTypes.getValuesAs(JsonString.class)
                .stream()
                .map(jsonString -> Item.GameType.find(jsonString.getString()))
                .collect(Collectors.toList());
        result.gameTypes = Collections.unmodifiableList(gameTypes);
        // Restrictions.
        final JsonArray jsonRestrictions = jsonObject.getJsonArray("restrictions"); // NOI18N.
        final List<Item.Restriction> restrictions = jsonRestrictions.getValuesAs(JsonString.class)
                .stream()
                .map(jsonString -> Item.Restriction.find(jsonString.getString()))
                .collect(Collectors.toList());
        result.restrictions = Collections.unmodifiableList(restrictions);
        // Détails.
        final Optional<DetailFactory> detailFactoryOptional = Optional.ofNullable(DetailFactory.getInstance(result.type));
        detailFactoryOptional.ifPresent(detailFactory -> {
            final JsonObject jsonDetail = jsonObject.getJsonObject("details"); // NOI18N.
            result.details = detailFactory.createDetail(jsonDetail);
        });
        return result;
    }
}
