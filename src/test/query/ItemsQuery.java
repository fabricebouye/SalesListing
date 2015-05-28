package test.query;

import java.io.IOException;
import javax.json.JsonObject;
import test.data.Item;

/**
 * Permet de faire des requêtes sur l'endpoint Items.
 * @author Fabrice Bouyé
 */
public enum ItemsQuery {

    INSTANCE;

    /**
     * L'URL de base de cet endpoint.
     */
    private static final String basecode = "https://api.guildwars2.com/v2/items"; // NOI18N.

    public Item item(final String languageCode, final int id) throws IOException {
        final String url = String.format("%s?id=%d&lang=%s", basecode, id, languageCode); // NOI18N.
        final JsonObject jsonObject = QueryUtils.queryObject(url);
        return asItem(jsonObject);
    }

    public Item asItem(final JsonObject jsonObject) {
        return null;
    }
}
