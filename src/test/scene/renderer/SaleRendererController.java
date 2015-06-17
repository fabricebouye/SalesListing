package test.scene.renderer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    private TextFlow priceFlow;

    public SaleRendererController() {
        saleProperty().addListener(saleInvalidationListener);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private final InvalidationListener saleInvalidationListener = observable -> {
        updateContent(getSale());
    };

    private void updateContent(final Sale sale) {
        nameLabel.setText(sale == null ? null : String.valueOf(sale.getId()));
        priceFlow.getChildren().clear();
        if (sale != null) {
            final int price = sale.getPrice();
            priceFlow.getChildren().setAll(LabelUtils.labelsForCoins(price));
            priceFlow.getChildren().add(new Text("(" + price + ")"));
        }
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
}
