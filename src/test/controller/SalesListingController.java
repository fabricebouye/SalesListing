package test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
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
import test.data.sale.Sale;
import test.query.CommerceQuery;
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
    private ListView<Sale> salesList;

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
        applicationKeyField.textProperty().addListener(applicationKeyChangeListener);
        final Optional<String> applicationKeyOptional = Optional.ofNullable(settings.getProperty("app.key")); // NOI18N.
        applicationKeyOptional.ifPresent(applicationKey -> {
            applicationKeyField.setText(applicationKey);
            applicationKeyField.positionCaret(0);
            applicationKeyField.selectRange(0, 0);
        });
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
    private ScheduledService<List<Sale>> updateService;
    /**
     * Le temps d'attente entre chaque mise à jour automatique.
     */
    private Duration updateWaitTime = CommerceQuery.SERVER_RETENTION_DURATION;

    /**
     * Démarre le service de mise à jour automatique.
     */
    public void start() {
        if (updateService == null) {
            updateService = new ScheduledService<List<Sale>>() {

                @Override
                protected Task<List<Sale>> createTask() {
                    return new Task<List<Sale>>() {

                        @Override
                        protected List<Sale> call() throws Exception {
                            final String applicationKey = settings.getProperty("app.key"); // NOI18N.
                            return CommerceQuery.listSalesHistory(applicationKey);
                        }
                    };
                }
            };
            updateService.setRestartOnFailure(true);
            updateService.setPeriod(updateWaitTime);
            updateService.setOnSucceeded(workerStateEvent -> {
                final List<Sale> result = updateService.getValue();
                final Optional<Sale> oldSelectionOptional = Optional.ofNullable(salesList.getSelectionModel().getSelectedItem());
                salesList.getItems().setAll(result);
                // On restaure la sélection si possible.
                oldSelectionOptional.ifPresent(oldSelection -> {
                    final Optional<Sale> newSelectionOptional = result.stream()
                            .filter(sale -> sale.getId() == oldSelection.getId())
                            .findFirst();
                    newSelectionOptional.ifPresent(newSelection -> salesList.getSelectionModel().select(newSelection));
                });
            });
            updateService.setOnFailed(workerStateEvent -> {
                System.err.println(updateService.getException());
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
