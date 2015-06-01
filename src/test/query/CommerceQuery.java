package test.query;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.util.Duration;
import javax.json.JsonArray;
import javax.json.JsonObject;
import test.data.sale.Sale;
import test.data.sale.SaleFactory;
import test.text.ApplicationKeyUtils;

/**
 * Permet de faire des requêtes sur l'endpoint Commerce.
 * @author Fabrice Bouyé
 */
public enum CommerceQuery {

    INSTANCE;

    /**
     * La durée de mise en cache sur le serveur est de 5 minutes.
     * <br/>voir <a href="https://forum-en.guildwars2.com/forum/community/api/Launching-v2-commerce-transactions/">Launching /v2/commerce/transactions</a> par Lawton Campbell.
     */
    public static Duration SERVER_RETENTION_DURATION = Duration.minutes(5);

    /**
     * L'URL de base de cet endpoint.
     */
    private static final String BASECODE = "https://api.guildwars2.com/v2/commerce"; // NOI18N.

    /**
     * Recupère l'historique des ventes.
     * @param applicationKey La clé d'application.
     * @return Une {@code List<Sale>} non-modifiable, jamais {@code null}.
     * @throws IOException En cas d'erreur.
     * @throws IllegalArgumentException Si la clé d'application est {@code null} ou n'est pas valide.
     */
    public static final List<Sale> listSalesHistory(final String applicationKey) throws IOException, IllegalArgumentException {
        final boolean applicationKeyValid = ApplicationKeyUtils.validateApplicationKey(applicationKey);
        if (!applicationKeyValid) {
            throw new IllegalArgumentException();
        }
        final String url = String.format("%s/transactions/current/sells?access_token=%s", BASECODE, applicationKey); // NOI18N.
        final JsonArray array = QueryUtils.queryArray(url);
        final List<Sale> result = array.getValuesAs(JsonObject.class)
                .stream()
                .map(value -> SaleFactory.createSale(value))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(result);
    }
}
