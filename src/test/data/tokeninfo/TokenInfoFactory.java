package test.data.tokeninfo;

import javax.json.JsonArray;
import javax.json.JsonObject;
import test.query.QueryUtils;

/**
 * Implémente la fabrique à infos d'un jeton de sécurité.
 * @author Fabrice
 */
public enum TokenInfoFactory {

    INSTANCE;

    public static TokenInfo createTokenInfo(final JsonObject jsonObject) {
        final TokenInfo result = new TokenInfo();
        result.id = jsonObject.getString("id"); // NOI18N.
        result.name = jsonObject.getString("name"); // NOI18N.
        final JsonArray jsonPermissions = jsonObject.getJsonArray("permissions"); // NOI18N.
        result.permissions = QueryUtils.jsonStringArrayToList(jsonPermissions, TokenInfo.Permission::find);
        return result;
    }
}
