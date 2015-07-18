package test.query;

import java.io.IOException;
import javax.json.JsonObject;
import test.data.account.Account;
import test.data.account.AccountFactory;
import test.text.ApplicationKeyUtils;

/**
 * Permet de faire des requêtes sur l'endpoint Account.
 * @author Fabrice Bouyé
 */
public enum AccountQuery {

    INSTANCE;

    /**
     * L'URL de base de cet endpoint.
     */
    private static final String BASECODE = "https://api.guildwars2.com/v2/account"; // NOI18N.

    /**
     * Récupère les infos d'un compte.
     * @param applicationKey La clé d'application.
     * @return Une instance de {@code Account}, jamais {@code null}.
     * @throws IOException En cas d'erreur.
     * @throws IllegalArgumentException Si la clé d'application est {@code null} ou n'est pas valide.
     */
    public static Account accountInfo(final String applicationKey) throws IOException, IllegalArgumentException {
        final boolean applicationKeyValid = ApplicationKeyUtils.validateApplicationKey(applicationKey);
        if (!applicationKeyValid) {
            throw new IllegalArgumentException();
        }
        final String url = String.format("%s?access_token=%s", BASECODE, applicationKey); // NOI18N.
        final JsonObject jsonObject = QueryUtils.queryObject(url);
        return AccountFactory.createAccount(jsonObject);
    }
}
