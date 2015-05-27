package test.query;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Classe utilitaire pour requêtes JSON.
 * @author Fabrice Bouyé
 */
public enum QueryUtils {

    INSTANCE;

    /**
     * Récupère un tableau JSON à l'URL indiquée.
     * @param basecode L'URL source.
     * @return Un objet de type
     * <span style="font-family: monospace; padding: 2px; background: #eee">JsonArray</span>
     * * @throws IOException En cas d'erreur.
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
     * @return Un objet de type
     * <span style="font-family: monospace; padding: 2px; background: #eee">JsonArray</span>
     * * @throws IOException En cas d'erreur.
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
     * @return Une chaine de texte. <br/>Contient {@code "all"} si aucun
     * identifiant est fourni.
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
}
