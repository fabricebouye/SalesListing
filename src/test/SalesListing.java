package test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application SalesListing.
 * @author Fabrice Bouyé
 */
public class SalesListing extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
         // chargement de l'interface graphique.
        final ResourceBundle bundle = ResourceBundle.getBundle("test.SalesListing"); // NOI18N.
        final URL fxmlURL = getClass().getResource("SalesListing.fxml"); // NOI18N.
        final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL, bundle);
        final Parent root = fxmlLoader.load();
        // Préparation de la scène.
        final Scene scene = new Scene(root);
        final URL cssURL = getClass().getResource("SalesListing.css"); // NOI18N.
        scene.getStylesheets().add(cssURL.toExternalForm());
        primaryStage.setTitle(bundle.getString("app.title")); // NOI18N.
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
