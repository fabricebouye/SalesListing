package test.query;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

/**
 * Classe utilitaire pour requêtes JSON.
 * @author Fabrice Bouyé
 */
public enum QueryUtils {

    INSTANCE;

    /**
     * Récupère un tableau JSON à l'URL indiquée.
     * @param basecode L'URL source.
     * @return Un objet de type {@code JsonArray}.
     * @throws IOException En cas d'erreur.
     */
    public static JsonArray queryArray(final String basecode) throws IOException {
        final URL url = new URL(basecode);
        try (final InputStream input = url.openStream(); final JsonReader reader = Json.createReader(input)) {
            return reader.readArray();
        }
    }

    /**
     * Récupère un objet JSON à l'URL indiquée.
     * @param basecode L'URL source.
     * @return Un objet de type {@code JsonArray}.
     * @throws IOException En cas d'erreur.
     */
    public static JsonObject queryObject(final String basecode) throws IOException {
        final URL url = new URL(basecode);
        try (final InputStream input = url.openStream(); final JsonReader reader = Json.createReader(input)) {
            return reader.readObject();
        }
    }

    /**
     * Convertit les identifiants en chaine de texte pour la requête.
     * @param ids Les identifiants.
     * @return Une chaine de texte. 
     * <br/>Contient {@code "all"} si aucun identifiant est fourni.
     */
    public static String idsToString(final int... ids) {
        String result = "all"; // NOI18N.
        if (ids.length > 0) {
            final StringBuilder builder = new StringBuilder();
            for (final int id : ids) {
                builder.append(id);
                builder.append(','); // NOI18N.
            }
            // On retire la virgule finale.
            builder.replace(builder.length() - 1, builder.length(), ""); // NOI18N.
            result = builder.toString();
        }
        return result;
    }

    /**
     * Convertit un tableau de chaines JSON en liste d'objets de type {@code T}.
     * @param <T> Le type à utiliser.
     * @param array Le tableau JSON source.
     * @param converter Le convertisseur {@code String} -> {@code T}.
     * @return Une instance non-modifiable de {@code List<T>}, jamais {@code null}.
     */
    public static <T> List<T> jsonStringArrayToList(final JsonArray array, final Function<String, T> converter) {
        final List<T> result = array.getValuesAs(JsonString.class)
                .stream()
                .map(jsonString -> jsonString.getString())
                .map(converter)
                .collect(Collectors.toList());
        return Collections.unmodifiableList(result);
    }

    /**
     * Convertit un tableau d'object JSON en liste d'objets de type {@code T}.
     * @param <T> Le type à utiliser.
     * @param array Le tableau JSON source.
     * @param converter Le convertisseur {@code JsonObject} -> {@code T}.
     * @return Une instance non-modifiable de {@code List<T>}, jamais {@code null}.
     */
    public static <T> List<T> jsonObjectArrayToList(final JsonArray array, final Function<JsonObject, T> converter) {
        final List<T> result = array.getValuesAs(JsonObject.class)
                .stream()
                .map(converter)
                .collect(Collectors.toList());
        return Collections.unmodifiableList(result);
    }

    /**
     * Convertit un tableau de tableau JSON en liste d'objets de type {@code T}.
     * @param <T> Le type à utiliser.
     * @param array Le tableau JSON source.
     * @param converter Le convertisseur {@code JsonArray} -> {@code T}.
     * @return Une instance non-modifiable de {@code List<T>}, jamais {@code null}.
     */
    public static <T> List<T> jsonArrayArrayToList(final JsonArray array, final Function<JsonArray, T> converter) {
        final List<T> result = array.getValuesAs(JsonArray.class)
                .stream()
                .map(converter)
                .collect(Collectors.toList());
        return Collections.unmodifiableList(result);
    }
}
