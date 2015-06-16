package test.demo;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import test.data.account.Account;
import test.data.item.Item;
import test.data.item.UpgradeComponentDetail;
import test.data.sale.Sale;
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

    /**
     * Test de la méthode {@code sales}.
     */
    @Test
    public void testSales() throws Exception {
        System.out.println("sales");
        final List<Sale> result = DemoSupport.sales();
        assertEquals(5, result.size());
        //
        assertEquals(1, result.get(0).getId());
        assertEquals(1, result.get(0).getItemId());
        assertEquals(1500, result.get(0).getPrice());
        assertEquals(5, result.get(0).getQuantity());
        assertEquals(ZonedDateTime.parse("2015-06-11T10:15:30+01:00"), result.get(0).getCreated());
        assertEquals(ZonedDateTime.parse("2015-06-11T17:24:20+00:00"), result.get(0).getPurchased());
        //
        assertEquals(2, result.get(1).getId());
        assertEquals(102, result.get(1).getItemId());
        assertEquals(50, result.get(1).getPrice());
        assertEquals(1, result.get(1).getQuantity());
        assertEquals(ZonedDateTime.parse("2015-06-11T10:15:30+01:00"), result.get(1).getCreated());
        assertEquals(ZonedDateTime.parse("2015-06-11T17:24:20+00:00"), result.get(1).getPurchased());
        //
        assertEquals(3, result.get(2).getId());
        assertEquals(98, result.get(2).getItemId());
        assertEquals(675, result.get(2).getPrice());
        assertEquals(2, result.get(2).getQuantity());
        assertEquals(ZonedDateTime.parse("2015-06-11T10:15:30+01:00"), result.get(2).getCreated());
        assertEquals(ZonedDateTime.parse("2015-06-11T17:24:20+00:00"), result.get(2).getPurchased());
        //
        assertEquals(4, result.get(3).getId());
        assertEquals(23, result.get(3).getItemId());
        assertEquals(14000, result.get(3).getPrice());
        assertEquals(1, result.get(3).getQuantity());
        assertEquals(ZonedDateTime.parse("2015-06-11T10:15:30+01:00"), result.get(3).getCreated());
        assertEquals(ZonedDateTime.parse("2015-06-11T17:24:20+00:00"), result.get(3).getPurchased());
        //
        assertEquals(5, result.get(4).getId());
        assertEquals(732, result.get(4).getItemId());
        assertEquals(54000, result.get(4).getPrice());
        assertEquals(1, result.get(4).getQuantity());
        assertEquals(ZonedDateTime.parse("2015-06-11T10:15:30+01:00"), result.get(4).getCreated());
        assertEquals(ZonedDateTime.parse("2015-06-11T17:24:20+00:00"), result.get(4).getPurchased());
    }

    /**
     * Test de la méthode {@code item}.
     */
    @Test
    public void testItem() throws Exception {
        System.out.println("item");
        final Item item = DemoSupport.item(1);
        assertEquals(1, item.getId());
        assertEquals("Cachet d'agonie supérieur", item.getName());
        assertEquals("Double-cliquez pour l'appliquer à une arme.", item.getDescription());
        assertEquals(Item.Type.UPGRADE_COMPONENT, item.getType());
        assertEquals(60, item.getLevel());
        assertEquals(Item.Rarity.EXOTIC, item.getRarity());
        assertEquals(216, item.getVendorValue());
        assertEquals(4, item.getGameTypes().size());
        assertEquals(true, item.getGameTypes().contains(Item.GameType.ACTIVITY));
        assertEquals(true, item.getGameTypes().contains(Item.GameType.DUNGEON));
        assertEquals(true, item.getGameTypes().contains(Item.GameType.PVE));
        assertEquals(true, item.getGameTypes().contains(Item.GameType.WVW));
        assertEquals(true, item.getFlags().isEmpty());
        assertEquals(true, item.getRestrictions().isEmpty());
        assertEquals("https://render.guildwars2.com/file/BAF34EB051D118F8A7C1645E0D940ED0660E6269/220658.png", item.getIcon());
        assertEquals(true, item.getDetails() instanceof UpgradeComponentDetail);
        final UpgradeComponentDetail detail = (UpgradeComponentDetail) item.getDetails();
        assertEquals(UpgradeComponentDetail.Type.SIGIL, detail.getType());
        assertEquals("d'agonie", detail.getSuffix());
    }
}
