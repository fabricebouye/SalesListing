package test.data.item;

import javax.json.JsonObject;

/**
 * Interface fonctionnelle definissant la fabrique abstraite pour les {@code Detail}.
 * @author Fabrice Bouyé
 */
@FunctionalInterface
interface DetailsFactory {

    /**
     * Crée une instance de la classe {@code Detail}.
     * @param jsonObject L'objet Json source.
     * @return Une instance de {@code Detail}, jamais {@code null}.
     */
    Details createDetails(final JsonObject jsonObject);
}
