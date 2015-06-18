package test.scene.renderer;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.util.Pair;
import test.data.item.Item;
import test.data.sale.Sale;

/**
 * Cellule pour afficher une vente (une paire (vente, objet)).
 * @author Fabrice Bouyé
 */
public final class SaleListCell extends ListCell<Pair<Sale, Item>> {    
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
    protected void updateItem(final Pair<Sale, Item> value, final boolean empty) {
        super.updateItem(value, empty);
        setText(null);
        Node graphic = null;
        if (!empty && value != null) {
            graphic = renderer;
            controller.setSale(value.getKey());
            controller.setItem(value.getValue());
        }
        setGraphic(graphic);
    }
}
