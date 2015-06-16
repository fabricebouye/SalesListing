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

    /**
     * Test de la méthode {@code decodeItem}.
     */
    @Test
    public void testDecodeItem() {
        System.out.println("decode");
        final String[] codes = {"AgH1WQAA", "AgH2WQAA", "AgH3WQAA", "AgEAWgAA", "AgG8BQEA"};
        final short[] expQuantities = {1, 1, 1, 1, 1};
        final int[] expItemIds = {23029, 23030, 23031, 23040, 1};
        final int[] expSkinIds = {ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE};
        final int[] expUpgrade1s = {ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE};
        final int[] expUpgrade2s = {ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE, ChatCodeUtils.NO_VALUE};
        IntStream.range(0, codes.length)
                .forEach(index -> {
                    try {
                        final String code = codes[index];
                        final int expQuantity = expQuantities[index];
                        final int expItemId = expItemIds[index];
                        final int expSkinId = expSkinIds[index];
                        final int expUpgrade1 = expUpgrade1s[index];
                        final int expUpgrade2 = expUpgrade2s[index];
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
