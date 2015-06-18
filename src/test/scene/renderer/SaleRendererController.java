package test.scene.renderer;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
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

    private void updateContent(final Sale sale, final Item item) {
        nameLabel.setText(null);
        for (final Item.Rarity rarity : Item.Rarity.values()) {
            final PseudoClass rarityPseudoClass = findPseudoClassForRarity(rarity);
            nameLabel.pseudoClassStateChanged(rarityPseudoClass, false);
        }
        purchasedLabel.setText(null);
        priceFlow.getChildren().clear();
        if (sale != null && item != null) {
            final String name = item.getName();
            nameLabel.setText(name);
            final PseudoClass rarityPseudoClass = findPseudoClassForRarity(item.getRarity());
            nameLabel.pseudoClassStateChanged(rarityPseudoClass, true);
            final String purchased = sale.getPurchased().format(DATE_FORMATTER);
            purchasedLabel.setText(purchased);
            final int price = sale.getPrice();
            priceFlow.getChildren().setAll(LabelUtils.labelsForCoins(price));
        }
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
