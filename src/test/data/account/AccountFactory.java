package test.data.account;

import java.util.function.Function;
import javax.json.JsonArray;
import javax.json.JsonObject;
import test.query.QueryUtils;

/**
 * Fabrique à compte.
 * @author Fabrice Bouyé
 */
public enum AccountFactory {

    INSTANCE;

    /**
     * Crée un compte.
     * @param jsonObject L'objet JSON source.
     * @return Une instance de {@code Account}, never {@code null}.
     */
    public static Account createAccount(final JsonObject jsonObject) {
        final Account result = new Account();
        result.id = jsonObject.getString("id"); // NOI18N.
        result.name = jsonObject.getString("name"); // NOI18N.
        result.world = jsonObject.getInt("world"); // NOI18N.
        final JsonArray jsonGuilds = jsonObject.getJsonArray("guilds"); // NOI18N.
        result.guilds = QueryUtils.jsonStringArrayToList(jsonGuilds, Function.identity());
        return result;
    }
}
