package test.scene.renderer;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import test.data.sale.Sale;

/**
 * Cellule pour afficher une vente.
 * @author Fabrice Bouyé
 */
public final class SaleListCell extends ListCell<Sale> {    
    private Node renderer;
    private SaleRendererController controller;

    public SaleListCell() {
        super();
        try {
            final URL fxmlURL = getClass().getResource("SaleRenderer.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            renderer = fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (IOException ex) {
            Logger.getLogger(SaleListCell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void updateItem(final Sale item, final boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        Node graphic = null;
        if (!empty && item != null) {
            graphic = renderer;
            controller.setSale(item);
        }
        setGraphic(graphic);
    }
}
