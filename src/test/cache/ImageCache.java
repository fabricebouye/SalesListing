package test.cache;

import java.util.Map;
import java.util.WeakHashMap;
import javafx.scene.image.Image;

/**
 * Un cache pour les icones des objets.
 * @author Fabrice Bouyé
 */
public enum ImageCache {

    /**
     * La seule instance de cette classe.
     */
    INSTANCE;

    private final Map<String, Image> cache = new WeakHashMap();

    /**
     * Recupère une image dane le cache des icones.
     * L'accès à cette méthode se fait dans le JavaFX Application Thread.
     * @param url L'url de l'image.
     * @return Une instance de {@code Image}, peut être {@code null} si {@code url} est {@code null} ou vide.
     */
    public Image getImage(final String url) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }
        Image result = cache.get(url);
        if (result == null) {
            result = new Image(url, true);
            cache.put(url, result);
        }
        return result;
    }
}
