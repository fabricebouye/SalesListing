package test.data.sale;

import java.time.ZonedDateTime;
import javax.json.JsonObject;
import test.query.QueryUtils;

/**
 * La fabrique pour les {@code Sale}.
 * @author Fabrice Bouyé
 */
public enum SaleFactory {

    INSTANCE;

    /**
     * Crée une instance de la classe {@code Sale}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code Sale}, jamais {@code null}.
     */
    public static Sale createSale(final JsonObject jsonObject) {
        final Sale result = new Sale();
        result.id = jsonObject.getInt("id"); // NOI18N.
        result.itemId = jsonObject.getInt("item_id"); // NOI18N.
        result.price = jsonObject.getInt("price"); // NOI18N.
        result.quantity = jsonObject.getInt("quantity"); // NOI18N.
        final String jsonCreated = jsonObject.getString("created"); // NOI18N.
        result.created = ZonedDateTime.parse(jsonCreated);
        final String jsonPurchased = QueryUtils.fromNullOrMissingString(jsonObject, "purchased"); // NOI18N.
        result.purchased = (jsonPurchased == null) ? null : ZonedDateTime.parse(jsonPurchased); // NOI18N.
        return result;
    }
}
