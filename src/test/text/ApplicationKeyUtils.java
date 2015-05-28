package test.text;

/**
 * Permet de valider une clé d'application.
 * @author Fabrice Bouyé
 */
public enum ApplicationKeyUtils {

    INSTANCE;
    /**
     * Pattern simplifié des clés générées par le site officiel.
     */
    private static final String APPLICATION_KEY_PATTERN = "[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{20}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}"; // NOI18N.

    /**
     * Test si une clé match le pattern.
     * @param value La clé à tester.
     * @return {@code True} si le test réussit, {@code false} sinon.
     */
    public static boolean validateApplicationKey(final String value) {
        return (value == null) ? false : value.matches(APPLICATION_KEY_PATTERN);
    }
}
