package test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import test.text.ApplicationKeyTextFormatter;
import test.text.ApplicationKeyUtils;

/**
 * Contrôleur du FXML.
 * @author Fabrice Bouyé
 */
public final class SalesListingController implements Initializable {

    @FXML
    private TextField applicationKeyField;

    private final Properties settings = new Properties();

    public SalesListingController() {
        // Chargement du fichier de config si présent.
        final File file = new File("settings.properties"); // NOI18N.
        if (file.exists() && file.canRead()) {
            try (final InputStream input = new FileInputStream(file)) {
                settings.load(input);
            } catch (IOException ex) {
                Logger.getLogger(SalesListingController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final TextFormatter<String> applicationKeyTextFormatter = new ApplicationKeyTextFormatter();
        applicationKeyField.setTextFormatter(applicationKeyTextFormatter);
        final Optional<String> applicationKeyOptional = Optional.ofNullable(settings.getProperty("app.key")); // NOI18N.
        applicationKeyOptional.ifPresent(applicationKey -> applicationKeyField.setText(applicationKey));
        applicationKeyField.textProperty().addListener(applicationKeyChangeListener);
    }

    /**
     * Invoqué si la valeur de la clé change.
     */
    private final ChangeListener<String> applicationKeyChangeListener = (observable, oldValue, newValue) -> {
        if (ApplicationKeyUtils.validateApplicationKey(newValue)) {

        }
    };
}
