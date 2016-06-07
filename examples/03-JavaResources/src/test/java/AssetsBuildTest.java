import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AssetsBuildTest {
    @Test
    public void testResources() throws Exception {
        assertNotNull(getClass().getResource("css/main.css"));
        assertNotNull(getClass().getResource("js/templates.js"));
    }
}