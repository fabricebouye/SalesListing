package test.scene.renderer;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import test.data.item.Buff;
import test.data.item.Details;
import test.data.item.InfixUpgrade;
import test.data.item.Item;
import test.data.item.UpgradeComponentDetails;
import test.data.item.WeaponDetails;
import test.scene.LabelUtils;

/**
 * Controleur du FXML.
 * @author Fabrice Bouyé
 */
public final class ItemRendererController implements Initializable {

    @FXML
    private Text nameLabel;
    @FXML
    private TextFlow statsFlow;
    @FXML
    private TextFlow buffFlow;
    @FXML
    private TextFlow descriptionFlow;
    @FXML
    private Text levelLabel;
    @FXML
    private TextFlow priceFlow;
    @FXML
    private ImageView icon;

    public ItemRendererController() {
        itemProperty().addListener(dataInvalidationListener);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buffFlow.managedProperty().bind(buffFlow.visibleProperty());
        descriptionFlow.managedProperty().bind(descriptionFlow.visibleProperty());
        levelLabel.managedProperty().bind(levelLabel.visibleProperty());
    }

    private final InvalidationListener dataInvalidationListener = observable -> {
        updateContent(getItem());
    };

    private void updateContent(final Item item) {
        nameLabel.setText(null);
        for (final Item.Rarity rarity : Item.Rarity.values()) {
            final PseudoClass rarityPseudoClass = findPseudoClassForRarity(rarity);
            nameLabel.pseudoClassStateChanged(rarityPseudoClass, false);
        }
        icon.setImage(null);
        statsFlow.getChildren().clear();
        buffFlow.getChildren().clear();
        descriptionFlow.getChildren().clear();
        levelLabel.setText(null);
        priceFlow.getChildren().clear();
        if (item != null) {
            final String name = item.getName();
            nameLabel.setText(name);
            final PseudoClass rarityPseudoClass = findPseudoClassForRarity(item.getRarity());
            nameLabel.pseudoClassStateChanged(rarityPseudoClass, true);
            icon.setImage(new Image(item.getIcon(), true));
            final List<Text> descriptionLabels = LabelUtils.labelsForDescription(item.getDescription());
            descriptionFlow.getChildren().setAll(descriptionLabels);
            levelLabel.setVisible(item.getLevel() > 0);
            levelLabel.setText(String.format("Niveau requis : %d", item.getLevel()));
            final int price = item.getVendorValue();
            final List<Node> priceLabels = LabelUtils.labelsForCoins(price);
            priceFlow.getChildren().setAll(priceLabels);
            final Details details = item.getDetails();
            if (details instanceof UpgradeComponentDetails) {
                final UpgradeComponentDetails upgradeComponentDetails = (UpgradeComponentDetails) details;
                final InfixUpgrade infixUpgrade = upgradeComponentDetails.getInfixUpgrade();
                final Optional<Buff> buffOptional = Optional.ofNullable(infixUpgrade.getBuff());
                buffOptional.ifPresent(buff -> {
                    final List<Text> buffLabels = LabelUtils.labelsForBuff(buff.getDescription());
                    buffFlow.getChildren().setAll(buffLabels);
                });
            } else if (details instanceof WeaponDetails) {
                final WeaponDetails weaponDetails = (WeaponDetails) details;
                final String stats = String.format("Puissance d'arme : %d - %d", weaponDetails.getMinPower(), weaponDetails.getMaxPower());
                final Text statsLabel = new Text(stats);
                statsLabel.getStyleClass().add("item-stats-label");
                statsFlow.getChildren().add(statsLabel);
            }
        }
    }

    private PseudoClass findPseudoClassForRarity(final Item.Rarity rarity) {
        return PseudoClass.getPseudoClass(rarity.name().toLowerCase());
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
