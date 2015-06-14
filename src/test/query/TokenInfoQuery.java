package test.query;

import java.io.IOException;
import javax.json.JsonObject;
import test.data.tokeninfo.TokenInfo;
import test.data.tokeninfo.TokenInfoFactory;
import test.text.ApplicationKeyUtils;

/**
 * Permet de faire des requêtes sur l'endpoint TokenInfo.
 * @author Fabrice Bouyé
 */
public enum TokenInfoQuery {

    INSTANCE;

    /**
     * L'URL de base de cet endpoint.
     */
    private static final String BASECODE = "https://api.guildwars2.com/v2/tokenInfo"; // NOI18N.

    public static TokenInfo tokenInfo(final String applicationKey) throws IOException, IllegalArgumentException {
        final boolean applicationKeyValid = ApplicationKeyUtils.validateApplicationKey(applicationKey);
        if (!applicationKeyValid) {
            throw new IllegalArgumentException();
        }
        final String url = String.format("%s?access_token=%s", BASECODE, applicationKey); // NOI18N.
        final JsonObject jsonObject = QueryUtils.queryObject(url);
        return TokenInfoFactory.createTokenInfo(jsonObject);
    }
}
