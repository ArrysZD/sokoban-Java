import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TestFile {
    File f;

    @Before
    public void init() {
        f = new File();
    }

    @Test
    public void testVide() {
        assertTrue(f.estVide());
    }
}
