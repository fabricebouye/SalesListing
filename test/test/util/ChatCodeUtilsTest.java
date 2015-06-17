package test.util;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fabrice Bouyé
 */
public class ChatCodeUtilsTest {

    public ChatCodeUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    final String[] codes = {"AgH1WQAA", "AgH2WQAA", "AgH3WQAA", "AgEAWgAA"};
    final short[] quantities = {1, 1, 1, 1, 1};
    final int[] itemIds = {23029, 23030, 23031, 23040};
    final int[] skinIds = {ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE};
    final int[] upgrade1s = {ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE};
    final int[] upgrade2s = {ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE};

    /**
     * Test de la méthode {@code encodeItem}.
     */
    @Test
    public void testEncodeItem() {
        System.out.println("encodeItem");
        IntStream.range(0, codes.length)
                .forEach(index -> {
                    final ChatItem item = new ChatItem();
                    item.quantity = quantities[index];
                    item.itemId = itemIds[index];
                    item.skinId = skinIds[index];
                    item.upgrade1 = upgrade1s[index];
                    item.upgrade2 = upgrade2s[index];
                    final String expResult = codes[index];
                    final String result = ChatCodeUtils.encodeItem(item);
                    assertEquals(expResult, result);
                });
    }

    /**
     * Test de la méthode {@code decodeItem}.
     */
    @Test
    public void testDecodeItem() {
        System.out.println("decodeItem");
        IntStream.range(0, codes.length)
                .forEach(index -> {
                    try {
                        final String code = codes[index];
                        final int expQuantity = quantities[index];
                        final int expItemId = itemIds[index];
                        final int expSkinId = skinIds[index];
                        final int expUpgrade1 = upgrade1s[index];
                        final int expUpgrade2 = upgrade2s[index];
                        final ChatItem result = ChatCodeUtils.decodeItem(code);
                        assertEquals(expQuantity, result.getQuantity());
                        assertEquals(expItemId, result.getItemId());
                        assertEquals(expSkinId, result.getSkinId());
                        assertEquals(expUpgrade1, result.getUpgrade1());
                        assertEquals(expUpgrade2, result.getUpgrade2());
                    } catch (UnsupportedEncodingException ex) {
                        fail(ex.getMessage());
                    }
                });
    }
}
