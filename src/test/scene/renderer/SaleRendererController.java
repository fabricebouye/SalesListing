package test.scene.renderer;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import test.data.item.Item;
import test.data.sale.Sale;
import test.scene.LabelUtils;

/**
 * Controleur du FXML.
 * @author Fabrice Bouyé
 */
public final class SaleRendererController implements Initializable {

    @FXML
    private GridPane rootPane;
    @FXML
    private Text nameLabel;
    @FXML
    private Text purchasedLabel;
    @FXML
    private TextFlow priceFlow;
    @FXML
    private ImageView icon;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);

    public SaleRendererController() {
        saleProperty().addListener(dataInvalidationListener);
        itemProperty().addListener(dataInvalidationListener);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private final InvalidationListener dataInvalidationListener = observable -> {
        updateContent(getSale(), getItem());
    };

    private Tooltip tooltip;

    private void updateContent(final Sale sale, final Item item) {
        Tooltip.uninstall(rootPane, tooltip);
        nameLabel.setText(null);
        for (final Item.Rarity rarity : Item.Rarity.values()) {
            final PseudoClass rarityPseudoClass = findPseudoClassForRarity(rarity);
            nameLabel.pseudoClassStateChanged(rarityPseudoClass, false);
        }
        icon.setImage(null);
        purchasedLabel.setText(null);
        priceFlow.getChildren().clear();
        if (sale != null && item != null) {
            final String name = item.getName();
            nameLabel.setText(name);
            final PseudoClass rarityPseudoClass = findPseudoClassForRarity(item.getRarity());            
            nameLabel.pseudoClassStateChanged(rarityPseudoClass, true);
            icon.setImage(new Image(item.getIcon(), true));
            final String purchased = sale.getPurchased().format(DATE_FORMATTER);
            purchasedLabel.setText(purchased);
            final int price = sale.getPrice();
            priceFlow.getChildren().setAll(LabelUtils.labelsForCoins(price, true));
            tooltip = tooltipForItem(item);
            Tooltip.install(rootPane, tooltip);
        }
    }

    private Tooltip tooltipForItem(final Item item) {
        final Tooltip result = new Tooltip();
        try {
            result.setText(item.getName());
            final URL fxmlURL = getClass().getResource("ItemRenderer.fxml"); // NOI18N.
            final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            final Node node = fxmlLoader.load();
            final ItemRendererController controller = fxmlLoader.getController();
            controller.setItem(item);
            result.setGraphic(new Group(node));
            result.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } catch (IOException ex) {
            Logger.getLogger(SaleRendererController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return result;
    }

    private PseudoClass findPseudoClassForRarity(final Item.Rarity rarity) {
        return PseudoClass.getPseudoClass(rarity.name().toLowerCase());
    }

    private final ObjectProperty<Sale> sale = new SimpleObjectProperty<>(this, "sale"); // NOI18N.

    public final Sale getSale() {
        return sale.get();
    }

    public final void setSale(Sale value) {
        sale.set(value);
    }

    public final ObjectProperty<Sale> saleProperty() {
        return sale;
    }

    private final ObjectProperty<Item> item = new SimpleObjectProperty<>(this, "item"); // NOI18N.

    public final Item getItem() {
        return item.get();
    }

    public final void setItem(Item value) {
        item.set(value);
    }

    public final ObjectProperty<Item> itemProperty() {
        return item;
    }
}
