package test.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Classe utilitaire qui permet de faire transferer des infos via l'interface de chat en jeu.
 * @author Fabrice Bouyé
 */
public enum ChatCodeUtils {

    INSTANCE;

    private static final String CHAT_CODE_PATTERN = "\\[&([A-Za-z0-9+/]+=*)\\]";
    public static final byte NO_VALUE = 0;
    public static final byte HEADER_ITEM = 2;
    public static final short ITEM_DEFAULT = 0x00;
    public static final short ITEM_1_UPGRADE = 0x40;
    public static final short ITEM_2_UPGRADES = 0x60;
    public static final short ITEM_SKINNED = 0x80;
    public static final short ITEM_SKINNED_1_UPGRADE = 0xC0;
    public static final short ITEM_SKINNED_2_UPGRADES = 0xE0;

    private static void encodeId(ByteArrayOutputStream bytes, final int id) {
        bytes.write((byte) (id & 0xFF));
        bytes.write((byte) ((id & 0xFFFF) >> 8));
        bytes.write((byte) ((id & 0xFFFFFF) >> 16));
    }

    private static int decodeId(final byte byte1, final byte byte2, final byte byte3) {
        int result = (byte3 & 0xFF) << 16 | (byte2 & 0xFF) << 8 | (byte1 & 0xFF);
        return result;
    }

    public static String encodeItem(final ChatItem item) {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bytes.write(HEADER_ITEM);
        bytes.write((byte) item.getQuantity());
        encodeId(bytes, item.getItemId());
        final boolean isSkinned = item.getSkinId() != NO_VALUE;
        final boolean hasUpgrade1 = item.getUpgrade1() != NO_VALUE;
        final boolean hasUpgrade2 = item.getUpgrade2() != NO_VALUE;
        if (isSkinned) {
            if (hasUpgrade2) {
                bytes.write(ITEM_SKINNED_2_UPGRADES);
                encodeId(bytes, item.getSkinId());
                encodeId(bytes, item.getUpgrade1());
                encodeId(bytes, item.getUpgrade2());
            } else if (hasUpgrade1) {
                bytes.write(ITEM_SKINNED_1_UPGRADE);
                encodeId(bytes, item.getSkinId());
                encodeId(bytes, item.getUpgrade1());
            } else {
                bytes.write(ITEM_SKINNED);
                encodeId(bytes, item.getSkinId());
            }
        } else if (hasUpgrade1) {
            if (hasUpgrade2) {
                bytes.write(ITEM_2_UPGRADES);
                encodeId(bytes, item.getUpgrade1());
                encodeId(bytes, item.getUpgrade2());
            } else {
                bytes.write(ITEM_1_UPGRADE);
                encodeId(bytes, item.getUpgrade1());
            }
        }
        bytes.write(ITEM_DEFAULT);
        return Base64.getEncoder().encodeToString(bytes.toByteArray());
    }

    public static ChatItem decodeItem(final String code) throws UnsupportedEncodingException {
        final byte[] bytes = Base64.getDecoder().decode(code);
        final ChatItem result = new ChatItem();
        result.quantity = (short) (bytes[1] & 0xFF);
        result.itemId = decodeId(bytes[2], bytes[3], bytes[4]);
        final short termination = (short) (bytes[5] & 0xFF);
        switch (termination) {
            case ITEM_2_UPGRADES:
                result.upgrade2 = decodeId(bytes[9], bytes[10], bytes[11]);
            case ITEM_1_UPGRADE:
                result.upgrade1 = decodeId(bytes[6], bytes[7], bytes[8]);
                break;
            case ITEM_SKINNED_2_UPGRADES:
                result.upgrade2 = decodeId(bytes[12], bytes[13], bytes[14]);
            case ITEM_SKINNED_1_UPGRADE:
                result.upgrade1 = decodeId(bytes[9], bytes[10], bytes[11]);
            case ITEM_SKINNED:
                result.skinId = decodeId(bytes[6], bytes[7], bytes[8]);
                break;
            case ITEM_DEFAULT:
            default:
        }
        return result;
    }
}
