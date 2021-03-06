package test.query;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.json.JsonArray;
import javax.json.JsonObject;
import test.data.item.Item;
import test.data.item.ItemFactory;

/**
 * Permet de faire des requêtes sur l'endpoint Items.
 * @author Fabrice Bouyé
 */
public enum ItemsQuery {

    INSTANCE;

    /**
     * L'URL de base de cet endpoint.
     */
    private static final String BASECODE = "https://api.guildwars2.com/v2/items"; // NOI18N.

    /**
     * Récupère le descriptif d'un objet.
     * @param languageCode Le code du langage.
     * @param id L'identifiant de l'objet.
     * @return Une instance de {@code Item}, jamais {@code null}.
     * @throws IOException En cas d'erreur.
     */
    public static Item item(final String languageCode, final int id) throws IOException {
        final String url = String.format("%s?id=%d&lang=%s", BASECODE, id, languageCode); // NOI18N.
        final JsonObject jsonObject = QueryUtils.queryObject(url);
        final Item result = ItemFactory.createItem(jsonObject);
        return result;
    }

    /**
     * Récupère le descriptif de plusieurs objets.
     * @param languageCode Le code du langage.
     * @param ids Les identifiants des l'objet.
     * @return Une instance de {@code List<Item>} non-modifiable, jamais {@code null}.
     * @throws IOException En cas d'erreur.
     */
    public static List<Item> items(final String languageCode, final int... ids) throws IOException {
        final String url = String.format("%s?ids=%s&lang=%s", BASECODE, QueryUtils.idsToString(ids), languageCode); // NOI18N.
        final JsonArray jsonArray = QueryUtils.queryArray(url);
        return QueryUtils.jsonObjectArrayToList(jsonArray, ItemFactory::createItem);
    }
}
