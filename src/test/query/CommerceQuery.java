package test.query;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javafx.util.Duration;
import javax.json.JsonArray;
import javax.json.JsonObject;
import test.data.Sale;

/**
 * Permet de faire des requêtes sur l'endpoint Commerce.
 * @author Fabrice Bouyé
 */
public enum CommerceQuery {
    
    INSTANCE;

    /**
     * La durée de mise en cache sur le serveur est de 5 minutes.
     * <br/>voir <a href="https://forum-en.guildwars2.com/forum/community/api/Launching-v2-commerce-transactions/first#post5059704">Launching /v2/commerce/transactions</a> par Lawton Campbell.
     */
    public static Duration SERVER_RETENTION_DURATION = Duration.minutes(5);

    /**
     * L'URL de base de cet endpoint.
     */
    private static final String basecode = "https://api.guildwars2.com/v2/commerce"; // NOI18N.

    public static final List<Sale> salesHistory(final String applicationKey) throws IOException {
        final String url = String.format("%s/transactions/current/sells?access_token=%s", basecode, applicationKey); // NOI18N.
        final JsonArray array = QueryUtils.queryArray(url);
        return array.getValuesAs(JsonObject.class)
                .stream()
                .map(value -> asSale(value))
                .collect(Collectors.toList());
    }
    
    private static Sale asSale(final JsonObject jsonObject) {
        final int id = jsonObject.getInt("id"); // NOI18N.
        final int itemId = jsonObject.getInt("item_id"); // NOI18N.
        final int price = jsonObject.getInt("price"); // NOI18N.
        final int quantity = jsonObject.getInt("quantity"); // NOI18N.
        final String createdStr = jsonObject.getString("created"); // NOI18N.
        final ZonedDateTime created = ZonedDateTime.parse(createdStr);
        return new Sale(id, itemId, quantity, price, created);
    }
}
