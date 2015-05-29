package test.data.sale;

import java.time.ZonedDateTime;
import javax.json.JsonObject;

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
        final String createdStr = jsonObject.getString("created"); // NOI18N.
        result.created = ZonedDateTime.parse(createdStr);
        return result;
    }
}
