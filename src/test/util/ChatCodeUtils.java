package test.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author Fabrice Bouyé
 */
public enum ChatCodeUtils {

    INSTANCE;

    private static final String CHAT_CODE_PATTERN = "\\[&([A-Za-z0-9+/]+=*)\\]";
    public static final byte NO_VALUE = 0;
    public static final short ITEM_DEFAULT_ITEM = 0x00;
    public static final short ITEM_1_UPGRADE = 0x40;
    public static final short ITEM_2_UPGRADES = 0x60;
    public static final short ITEM_SKINNED = 0x80;
    public static final short ITEM_SKINNED_1_UPGRADE = 0xC0;
    public static final short ITEM_SKINNED_2_UPGRADES = 0xE0;

    public static ChatItem decodeItem(final String code) throws UnsupportedEncodingException {
        final byte[] bytes = Base64.getDecoder().decode(code);
        final ChatItem result = new ChatItem();
        result.quantity = (short) (bytes[1] & 0xFF);
        result.itemId = (bytes[3] & 0xFF) << 8 | (bytes[2] & 0xFF);
        return result;
    }
}
