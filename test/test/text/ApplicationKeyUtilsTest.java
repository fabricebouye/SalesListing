package test.text;

import java.util.stream.IntStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test unitaire du méthodes utilitaire de gestion des clés.
 * @author Fabrice Bouyé
 */
public class ApplicationKeyUtilsTest {

    public ApplicationKeyUtilsTest() {
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
     * Test of validateApplicationKey method, of class ApplicationKeyUtils.
     */
    @Test
    public void testValidateApplicationKey() {
        System.out.println("validateApplicationKey"); // NOI18N.
        String[] values = {
            null,
            "", // NOI18N.
            "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXXXXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX", // NOI18N.
            "AD8EF568-A2BF-2187-9500-AD8EF568AD8EF568AD8E-9500-2187-9500-AD8EF568AD8E", // NOI18N.
            "AW8EF568-A2BF-2187-9500-AD8EF568AD8EF568AD8E-9500-2187-9500-AD8EF568AD8E" // NOI18N.
        };
        boolean[] expResults = {false, false, false, true, false};

        IntStream.range(
                0, values.length)
                .forEach(index -> {
                    final String value = values[index];
                    final boolean expResult = expResults[index];
                    boolean result = ApplicationKeyUtils.validateApplicationKey(value);
                    assertEquals(expResult, result);
                }
                );
    }
}
