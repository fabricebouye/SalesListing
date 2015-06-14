package test.data.account;

import java.util.List;

/**
 * Implémentation d'un compte.
 * @author Fabrice Bouyé
 */
public final class Account {

    String id;
    String name;
    int world;
    List<String> guilds;

    /**
     * Crée une instance vide.
     */
    Account() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWorld() {
        return world;
    }

    public List<String> getGuilds() {
        return guilds;
    }
}
