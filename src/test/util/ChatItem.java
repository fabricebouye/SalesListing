package test.util;

/**
 *
 * @author Fabrice Bouyé
 */
public final class ChatItem {

    short quantity;
    int itemId;
    int skinId = ChatCodeUtils.NO_VALUE;
    int upgrade1 = ChatCodeUtils.NO_VALUE;
    int upgrade2 = ChatCodeUtils.NO_VALUE;

    public ChatItem() {
    }

    public short getQuantity() {
        return quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public int getSkinId() {
        return skinId;
    }

    public int getUpgrade1() {
        return upgrade1;
    }

    public int getUpgrade2() {
        return upgrade2;
    }

}
