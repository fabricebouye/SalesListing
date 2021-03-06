package test.data.sale;

import java.time.ZonedDateTime;

/**
 * Définit une vente au comptoir en jeu.
 * @author Fabrice Bouyé
 */
public final class Sale {

    /**
     * L'id de la vente.
     */
    int id;
    /**
     * L'id de l'objet vendu.
     */
    int itemId;
    /**
     * La quantité d'objets vendus.
     */
    int quantity;
    /**
     * Le prix de la vente.
     */
    int price;
    /**
     * La date de création de la vente.
     */
    ZonedDateTime created;
    /**
     * La date de la vente.
     */
    ZonedDateTime purchased;

    /**
     * Crée une nouvelle instance.
     */
    Sale() {
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

    public int getQuantity() {
        return quantity;
    }
    
    public int getPrice() {
        return price;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getPurchased() {
        return purchased;
    }
}
