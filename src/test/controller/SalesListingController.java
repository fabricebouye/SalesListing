package test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.util.Pair;
import test.SalesListing;
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
    private ImageView bltcLogo;
    @FXML
    private TextField applicationKeyField;
    @FXML
    private ToggleGroup languageSelectionGroup;
    @FXML
    private ToggleButton enToggle;
    @FXML
    private ToggleButton frToggle;
    @FXML
    private ToggleButton deToggle;
    @FXML
    private ToggleButton esToggle;
    @FXML
    private TextField searchField;
    @FXML
    private CheckMenuItem nameCheckItem;
    @FXML
    private CheckMenuItem descriptionCheckItem;
    @FXML
    private CheckMenuItem rarityCheckItem;
    @FXML
    private ToggleGroup salesCategoryGroup;
    @FXML
    private ToggleButton sellToggle;
    @FXML
    private ToggleButton buyToggle;
    @FXML
    private ToggleGroup salesTimeGroup;
    @FXML
    private ToggleButton currentToggle;
    @FXML
    private ToggleButton historyToggle;
    @FXML
    private ListView<Pair<Sale, Item>> salesListView;

    /**
     * Liste des ventes.
     */
    private ObservableList<Pair<Sale, Item>> salesList = FXCollections.observableList(new LinkedList());
    /**
     * Liste filtrée des personnages.
     */
    private FilteredList<Pair<Sale, Item>> filteredSalesList = new FilteredList<>(salesList);
    /**
     * Affiche toutes les ventes.
     */
    private final Predicate<Pair<Sale, Item>> allSalesFilter = sale -> true;

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
        //
        filteredSalesList.setPredicate(allSalesFilter);
    }

    private ResourceBundle resources;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        this.resources = resources;
        //
        settings.setProperty("language.code", (String) languageSelectionGroup.getSelectedToggle().getUserData()); // NOI18N.
        languageSelectionGroup.selectedToggleProperty().addListener(languageInvalidationListener);
        //
        settings.setProperty("sales.category", (String) salesCategoryGroup.getSelectedToggle().getUserData()); // NOI18N.
        salesCategoryGroup.selectedToggleProperty().addListener(salesInvalidationListener);
        //
        settings.setProperty("sales.history", (String) salesTimeGroup.getSelectedToggle().getUserData()); // NOI18N.
        salesTimeGroup.selectedToggleProperty().addListener(salesInvalidationListener);
        //
        searchField.textProperty().addListener(searchInvalidationListener);
        //
        nameCheckItem.setSelected(Boolean.parseBoolean(settings.getProperty("search.filter.name", "true"))); // NOI18N.
        nameCheckItem.selectedProperty().addListener(searchInvalidationListener);
        descriptionCheckItem.setSelected(Boolean.parseBoolean(settings.getProperty("search.filter.description", "true"))); // NOI18N.
        descriptionCheckItem.selectedProperty().addListener(searchInvalidationListener);
        rarityCheckItem.setSelected(Boolean.parseBoolean(settings.getProperty("search.filter.rarity", "true"))); // NOI18N.
        rarityCheckItem.selectedProperty().addListener(searchInvalidationListener);
        //
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
        salesListView.setItems(filteredSalesList);
        salesListView.setCellFactory(listView -> new SaleListCell());
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
     * Invoqué si la valeur de la valeur de recherche change.
     */
    private final InvalidationListener searchInvalidationListener = observable -> {
        final String searchText = searchField.getText();
        final String[] criteria = (searchText == null || searchText.trim().isEmpty()) ? null : Arrays.stream(searchText.trim().split("[\\s,;]+")) // NOI18N.
                .map(this::normalizeForSearch)
                .collect(Collectors.toList())
                .toArray(new String[0]);
        final Predicate<Pair<Sale, Item>> filter = (criteria == null) ? allSalesFilter : character -> filterSale(character, criteria);
        filteredSalesList.setPredicate(filter);
    };

    /**
     * Filtre la liste des ventes.
     * @param sale La vente à tester.
     * @param criteria Les critères de recherche.
     * @return {@code True} si le test réussit, {@code false} sinon.
     */
    private boolean filterSale(final Pair<Sale, Item> sale, final String... criteria) {
        final boolean filterName = nameCheckItem.isSelected();
        final boolean filterDescription = descriptionCheckItem.isSelected();
        final boolean filterRarity = rarityCheckItem.isSelected();
        final String name = filterName ? normalizeForSearch(sale.getValue().getName()) : null;
        final String baseName = (name == null) ? null : normalizeForSearch(name);
        final String description = filterDescription && sale.getValue().getDescription() != null ? normalizeForSearch(sale.getValue().getDescription()) : null;
        final String baseDescription = (description == null) ? null : normalizeForSearch(description);
        final Item.Rarity rarity = filterRarity ? sale.getValue().getRarity() : null;
        final String baseRarity = (rarity == null) ? null : normalizeForSearch(rarity.name());
        //
        boolean result = true;
        for (final String criterion : criteria) {
            boolean criterionTest = false;
            // Teste le nom de l'objet.  
            if (baseName != null) {
                final boolean characterFound = baseName.contains(criterion);
                criterionTest |= characterFound;
            }
            // Teste la description de l'objet.  
            if (baseDescription != null) {
                final boolean descriptionFound = baseDescription.contains(criterion);
                criterionTest |= descriptionFound;
            }
            // Teste la rareté de l'objet. 
            if (baseRarity != null) {
                final boolean rarityFound = baseRarity.contains(criterion);
                criterionTest |= rarityFound;
            }
            //
            result &= criterionTest;
        }
        return result;
    }

    /**
     * Normalize la chaine de charactère en retirant les accents et diacritiques.
     * @param source La chaîne source.
     * @return Une {@code String}, jamais {@code null}.
     */
    private String normalizeForSearch(final String source) {
        final String nfdNormalizedString = Normalizer.normalize(source.toLowerCase(), Normalizer.Form.NFD);
        final Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+"); // NOI18N.
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    /**
     * Invoqué quand la clé d'application change.
     * @param applicationKey La nouvelle clé d'application.
     */
    private void applicationKeyChanged(final String applicationKey) {
        final boolean isDemoMode = DemoSupport.isDemoApplicationKey(applicationKey);
        final boolean applicationKeyValid = isDemoMode ? true : ApplicationKeyUtils.validateApplicationKey(applicationKey);
        applicationKeyField.pseudoClassStateChanged(errorPseudoClass, !applicationKeyValid);
        stopUpdateService();
        salesListView.setDisable(!applicationKeyValid);
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
                            final String languageCode = settings.getProperty("language.code"); // NOI18N.
                            final String salesCategory = settings.getProperty("sales.category"); // NOI18N.
                            final String salesHistory = settings.getProperty("sales.history"); // NOI18N.
                            final boolean isDemoMode = DemoSupport.isDemoApplicationKey(applicationKey);
                            final QueryResult result = new QueryResult();
                            System.out.printf("%s - %s - %s", salesCategory, salesHistory, applicationKey).println();
                            switch (salesCategory) {
                                case "sell": {
                                    switch (salesHistory) {
                                        case "history": {
                                            result.sales = isDemoMode ? DemoSupport.sales() : CommerceQuery.listSalesHistory(applicationKey);
                                        }
                                        break;
                                        case "current": {
                                            result.sales = isDemoMode ? DemoSupport.sales() : CommerceQuery.listSales(applicationKey);
                                        }
                                        break;
                                    }
                                }
                                break;
                                case "buy": {
                                    switch (salesHistory) {
                                        case "history": {
                                            result.sales = isDemoMode ? DemoSupport.sales() : CommerceQuery.listPurchasesHistory(applicationKey);
                                        }
                                        break;
                                        case "current": {
                                            result.sales = isDemoMode ? DemoSupport.sales() : CommerceQuery.listPurchases(applicationKey);
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                            System.out.println(result.sales);
                            final int[] itemIds = result.sales
                                    .stream()
                                    .mapToInt(sale -> sale.getItemId())
                                    .toArray();
                            final List<Item> items = (itemIds.length == 0) ? Collections.EMPTY_LIST : (isDemoMode ? DemoSupport.items(itemIds) : ItemsQuery.items(languageCode, itemIds));
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
                final Optional<Pair<Sale, Item>> oldSelectionOptional = Optional.ofNullable(salesListView.getSelectionModel().getSelectedItem());
                final List<Pair<Sale, Item>> sales = result.sales.stream().map(sale -> {
                    final int itemId = sale.getItemId();
                    final Item item = result.items.get(itemId);
                    return new Pair<>(sale, item);
                }).collect(Collectors.toList());
                salesList.setAll(sales);
                // On restaure la sélection si possible.
                oldSelectionOptional.ifPresent(oldSelection -> {
                    final Optional<Pair<Sale, Item>> newSelectionOptional = sales
                            .stream()
                            .filter(value -> value.getKey().getId() == oldSelection.getKey().getId())
                            .findFirst();
                    newSelectionOptional.ifPresent(newSelection -> salesListView.getSelectionModel().select(newSelection));
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

    /**
     * Invoqué lorsque le langue sélectionné change.
     */
    private final InvalidationListener languageInvalidationListener = observable -> {
        final Toggle selectedLanguageToggle = languageSelectionGroup.getSelectedToggle();
        final String languageCode = (selectedLanguageToggle == null) ? null : (String) selectedLanguageToggle.getUserData();
        if (languageCode != null) {
            final String logoName = String.format("BLTCLogo_%s.png", languageCode.toUpperCase()); // NOI18N.
            final URL logoURL = SalesListing.class.getResource(logoName);
            final Image logoImage = new Image(logoURL.toExternalForm());
            bltcLogo.setImage(logoImage);
            settings.setProperty("language.code", languageCode); // NOI18N.
            if (updateService != null && updateService.isRunning()) {
                updateService.restart();
            }
        }
    };

    /**
     * Invoqué lorsque le langue sélectionné change.
     */
    private final InvalidationListener salesInvalidationListener = observable -> {
        final Toggle salesCategoryToggle = salesCategoryGroup.getSelectedToggle();
        final String salesCategory = (salesCategoryToggle == null) ? null : (String) salesCategoryToggle.getUserData();
        final Toggle salesHistoryToggle = salesTimeGroup.getSelectedToggle();
        final String salesHistory = (salesHistoryToggle == null) ? null : (String) salesHistoryToggle.getUserData();
        if (salesCategory != null && salesHistory != null) {
            settings.setProperty("sales.category", salesCategory); // NOI18N.
            settings.setProperty("sales.history", salesHistory); // NOI18N.
            if (updateService != null && updateService.isRunning()) {
                updateService.restart();
            }
        }
    };
}
