package test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.Duration;
import javafx.util.Pair;
import test.data.account.Account;
import test.data.item.Item;
import test.data.sale.Sale;
import test.data.tokeninfo.TokenInfo;
import test.demo.DemoSupport;
import test.query.CommerceQuery;
import test.query.ItemsQuery;
import test.query.TokenInfoQuery;
import test.scene.renderer.SaleListCell;
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
    private ListView<Pair<Sale, Item>> salesList;

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
        //
        salesList.setCellFactory(listView -> new SaleListCell());
    }

    /**
     * La pseudo-classe servant de décorateur en cas d'erreur.
     */
    private final PseudoClass errorPseudoClass = PseudoClass.getPseudoClass("error"); // NOI18N.

    /**
     * Invoqué si la valeur de la clé d'application change.
     */
    private final ChangeListener<String> applicationKeyChangeListener = (observable, oldValue, newValue) -> {
        applicationKeyChanged(newValue);
    };

    /**
     * Invoqué quand la clé d'application change.
     * @param applicationKey La nouvelle clé d'application.
     */
    private void applicationKeyChanged(final String applicationKey) {
        final boolean isDemoMode = DemoSupport.isDemoApplicationKey(applicationKey);
        final boolean applicationKeyValid = isDemoMode ? true : ApplicationKeyUtils.validateApplicationKey(applicationKey);
        applicationKeyField.pseudoClassStateChanged(errorPseudoClass, !applicationKeyValid);
        stopUpdateService();
        salesList.setDisable(!applicationKeyValid);
        if (applicationKeyValid) {
            settings.setProperty("app.key", applicationKey); // NOI18N.
            checkApplicationKeyAndStartUpdate();
        } else {
            settings.setProperty("app.key", ""); // NOI18N.
//            messageLabel.setVisible(true);
//            messageLabel.setText(resources.getString("no.account.label")); // NOI18N.
        }
    }

    /**
     * Le service qui va vérifier la validité de la clé.
     */
    private Service<TokenInfo> applicationKeyCheckService;

    /**
     * Vérifie les permission de la clé et si ok lance le service de mise à jour.
     */
    private void checkApplicationKeyAndStartUpdate() {
        if (applicationKeyCheckService == null) {
            applicationKeyCheckService = new Service<TokenInfo>() {

                @Override
                protected Task<TokenInfo> createTask() {
                    return new Task<TokenInfo>() {

                        @Override
                        protected TokenInfo call() throws Exception {
                            final String applicationKey = settings.getProperty("app.key"); // NOI18N.
                            final boolean isDemoMode = DemoSupport.isDemoApplicationKey(applicationKey);
                            final TokenInfo result = isDemoMode ? DemoSupport.tokenInfo() : TokenInfoQuery.tokenInfo(applicationKey);
                            return result;
                        }
                    };
                }
            };
            applicationKeyCheckService.setOnSucceeded(workerStateEvent -> {
                final TokenInfo result = (TokenInfo) workerStateEvent.getSource().getValue();
//                accountKeyLabel.setText(result.getName());                
                final List<TokenInfo.Permission> permissions = result.getPermissions();
//                final List<Label> permissionsLabel = permissions.stream()
//                        .map(permission -> {
//                            final String text = LabelUtils.permissionLabel(resources, permission);
//                            final Label label = new Label(text);
//                            label.getStyleClass().add("permission-label");
//                            return label;
//                        })
//                        .collect(Collectors.toList());
//                applicationKeyPermissionFlow.getChildren().setAll(permissionsLabel);
                if (permissions.contains(TokenInfo.Permission.ACCOUNT) && permissions.contains(TokenInfo.Permission.TRADINGPOST)) {
                    startUpdateService();
                } else {
//                    messageLabel.setVisible(true);
//                    messageLabel.pseudoClassStateChanged(errorPseudoClass, true);
//                    messageLabel.setText(resources.getString("bad.permission.error"));
                }
            });
            applicationKeyCheckService.setOnCancelled(workerStateEvent -> {
            });
            applicationKeyCheckService.setOnFailed(workerStateEvent -> {
//                messageLabel.setVisible(true);
//                messageLabel.pseudoClassStateChanged(errorPseudoClass, true);
//                messageLabel.setText(resources.getString("application_key.failed.error"));
                workerStateEvent.getSource().getException().printStackTrace();
            });
        }
        applicationKeyCheckService.restart();
    }

    /**
     * Le service de mise à jour automatique.
     */
    private ScheduledService<QueryResult> updateService;
    /**
     * Le temps d'attente entre chaque mise à jour automatique.
     */
    private Duration updateWaitTime = CommerceQuery.SERVER_RETENTION_DURATION;

    /**
     * Le résultat de la requête.
     * @author Fabrice Bouyé
     */
    private static class QueryResult {

        Account account;
        List<Sale> sales;
        Map<Integer, Item> items;
    }

    /**
     * Démarre le service de mise à jour automatique.
     */
    public void startUpdateService() {
        if (updateService == null) {
            updateService = new ScheduledService<QueryResult>() {

                @Override
                protected Task<QueryResult> createTask() {
                    return new Task<QueryResult>() {

                        @Override
                        protected QueryResult call() throws Exception {
                            final String applicationKey = settings.getProperty("app.key"); // NOI18N.
                            final boolean isDemoMode = DemoSupport.isDemoApplicationKey(applicationKey);
                            final QueryResult result = new QueryResult();
                            result.sales = isDemoMode ? DemoSupport.sales() : CommerceQuery.listSalesHistory(applicationKey);
                            final int[] itemIds = result.sales
                                    .stream()
                                    .mapToInt(sale -> sale.getItemId())
                                    .toArray();
                            final List<Item> items = isDemoMode ? DemoSupport.items(itemIds) : ItemsQuery.items("fr", itemIds);
                            final Map<Integer, Item> itemMap = items.stream()
                                    .collect(Collectors.toMap(item -> item.getId(), Function.identity()));
                            result.items = Collections.unmodifiableMap(itemMap);
                            return result;
                        }
                    };
                }
            };
            updateService.setRestartOnFailure(true);
            updateService.setPeriod(updateWaitTime);
            updateService.setOnSucceeded(workerStateEvent -> {
                final QueryResult result = (QueryResult) workerStateEvent.getSource().getValue();
                final Optional<Pair<Sale, Item>> oldSelectionOptional = Optional.ofNullable(salesList.getSelectionModel().getSelectedItem());
                final List<Pair<Sale, Item>> sales = result.sales.stream().map(sale -> {
                    final int itemId = sale.getItemId();
                    final Item item = result.items.get(itemId);
                    return new Pair<>(sale, item);
                }).collect(Collectors.toList());
                salesList.getItems().setAll(sales);
                // On restaure la sélection si possible.
                oldSelectionOptional.ifPresent(oldSelection -> {
                    final Optional<Pair<Sale, Item>> newSelectionOptional = sales
                            .stream()
                            .filter(value -> value.getKey().getId() == oldSelection.getKey().getId())
                            .findFirst();
                    newSelectionOptional.ifPresent(newSelection -> salesList.getSelectionModel().select(newSelection));
                });
            });
            updateService.setOnCancelled(workerStateEvent -> {
            });
            updateService.setOnFailed(workerStateEvent -> {
                workerStateEvent.getSource().getException().printStackTrace();
            });
        }
        updateService.restart();
    }

    /**
     * Stoppe le service de mise a jour automatique.
     */
    public void stopUpdateService() {
        if (updateService == null) {
            return;
        }
        updateService.cancel();
    }
}
