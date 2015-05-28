package test.data;

import java.time.ZonedDateTime;

/**
 * Définit une vente au comptoir en jeu.
 * @author Fabrice Bouyé
 */
public final class Sale {

    private final int id;
    private final int itemId;
    private final int quantity;
    private final int price;
    private final ZonedDateTime created;

    /**
     * Crée une nouvelle instance.
     * @param id L'id de la vente.
     * @param itemId L'id de l'objet vendu.
     * @param quantity La quantité d'objet vendus.
     * @param price Le prix de la vente.
     * @param created La date de création de la vente.
     */
    public Sale(final int id, final int itemId, final int quantity, final int price, final ZonedDateTime created) {
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.created = created;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public int quantity() {
        return quantity;
    }

    public ZonedDateTime getCreated() {
        return created;
    }
}
