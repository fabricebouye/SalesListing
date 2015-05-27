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
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.Duration;
import test.text.ApplicationKeyTextFormatter;
import test.text.ApplicationKeyUtils;

/**
 * Contrôleur du FXML.
 * @author Fabrice Bouyé
 */
public final class SalesListingController implements Initializable {

    @FXML
    private TextField applicationKeyField;
    @FXML
    private ListView salesList;

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
        applicationKeyOptional.ifPresent(applicationKey -> {
            applicationKeyField.setText(applicationKey);
            applicationKeyField.positionCaret(0);
            applicationKeyField.selectRange(0, 0);
        });
        applicationKeyField.textProperty().addListener(applicationKeyChangeListener);
    }

    /**
     * La pseudo-classe servant de décorateur en cas d'erreur.
     */
    private final PseudoClass errorPseudoClass = PseudoClass.getPseudoClass("error"); // NOI18N.

    /**
     * Invoqué si la valeur de la clé d'application change.
     */
    private final ChangeListener<String> applicationKeyChangeListener = (observable, oldValue, newValue) -> {
        final boolean applicationKeyValid = ApplicationKeyUtils.validateApplicationKey(newValue);
        applicationKeyField.pseudoClassStateChanged(errorPseudoClass, !applicationKeyValid);
        salesList.setDisable(!applicationKeyValid);
        if (applicationKeyValid) {
            settings.setProperty("app.key", newValue); // NOI18N.
            start();
        } else {
            stop();
            settings.setProperty("app.key", null); // NOI18N.
            salesList.getItems().clear();
        }
    };

    /**
     * Le service de mise à jour automatique.
     */
    private ScheduledService updateService;
    /**
    * Le temps d'attente entre chaque mise à jour automatique.
    */
    private Duration updateWaitTime = Duration.minutes(2);

    /**
     * Démarre le service de mise à jour automatique.
     */
    public void start() {
        if (updateService == null) {
            updateService = new ScheduledService() {

                @Override
                protected Task createTask() {
                    return null;
                }
            };
            updateService.setRestartOnFailure(true);
            updateService.setPeriod(updateWaitTime);
            updateService.setOnSucceeded(workerStateEvent -> {
            });
            updateService.setOnFailed(workerStateEvent -> {
            });
            updateService.setOnCancelled(workerStateEvent -> {
            });
        }
        updateService.restart();
    }

    /**
     * Stoppe le service de mise a jour automatique.
     */
    public void stop() {
        if (updateService == null) {
            return;
        }
        updateService.cancel();
    }
}
