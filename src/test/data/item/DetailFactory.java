package test.data.item;

import javax.json.JsonObject;

/**
 * Interface fonctionnelle definissant la fabrique abstraite pour les {@code Detail}.
 * @author Fabrice Bouyé
 */
@FunctionalInterface
interface DetailFactory {

    /**
     * Crée une instance de la classe {@code Detail}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code Detail}, jamais {@code null}.
     */
    Detail createDetail(final JsonObject jsonObject);
}
