package test.text;

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
        System.out.println("validateApplicationKey");
        String value = "";
        boolean expResult = true;
        boolean result = ApplicationKeyUtils.validateApplicationKey(value);
        assertEquals(expResult, result);
    }
}
