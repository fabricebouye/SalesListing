package test.demo;

import java.util.stream.IntStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import test.data.account.Account;
import test.data.tokeninfo.TokenInfo;

/**
 * Test de la classe {@code DemoSupport}.
 * @author Fabrice Bouyé
 */
public class DemoSupportTest {
    
    public DemoSupportTest() {
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
     * Test de la méthode {@code isDemoApplicationKey}.
     */
    @Test
    public void testIsDemoApplicationKey() {
        System.out.println("isDemoApplicationKey");
        final String[] values = {"XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXXXXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX",
            "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFFFFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF",
            "",
            null};
        final boolean[] expResults = {true, false, false, false};
        IntStream.range(0, values.length)
                .forEach(index -> {
                    final String value = values[index];
                    final boolean expResult = expResults[index];
                    final boolean result = DemoSupport.isDemoApplicationKey(value);
                    assertEquals(expResult, result);
                });
    }

    /**
     * Test de la méthode {@code tokenInfo}.
     */
    @Test
    public void testTokenInfo() throws Exception {
        System.out.println("tokenInfo");
        final TokenInfo result = DemoSupport.tokenInfo();
        assertEquals("XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX", result.getId());
        assertEquals("Demo Key", result.getName());
        assertEquals(2, result.getPermissions().size());
        assertEquals(true, result.getPermissions().contains(TokenInfo.Permission.ACCOUNT));
        assertEquals(true, result.getPermissions().contains(TokenInfo.Permission.TRADINGPOST));
    }

    /**
     * Test de la méthode {@code accountInfo}.
     */
    @Test
    public void testAccountInfo() throws Exception {
        System.out.println("tokenInfo");
        final Account result = DemoSupport.accountInfo();
        assertEquals("XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX", result.getId());
        assertEquals("ExampleAccount.1234", result.getName());
        assertEquals(1001, result.getWorld());
        assertEquals(3, result.getGuilds().size());
        assertEquals(true, result.getGuilds().contains("01"));
        assertEquals(true, result.getGuilds().contains("02"));
        assertEquals(true, result.getGuilds().contains("03"));
    }
}
