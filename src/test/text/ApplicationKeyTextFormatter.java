package test.text;

import javafx.scene.control.TextFormatter;

/**
 * Le formateur de texte pour la clé d'application.
 * @author Fabrice Bouyé
 */
public final class ApplicationKeyTextFormatter extends TextFormatter<String> {

    /**
     * Crée une nouvelle instance.
     */
    public ApplicationKeyTextFormatter() {
        super(change -> {
            final String newText = change.getText();
            System.out.printf("Change %s", newText).println();
            if (newText != null && !change.getText().matches("[A-F0-9\\-]+")) { // NOI18N.
                change.setText("");
            }
            return change;
        });
    }
}
