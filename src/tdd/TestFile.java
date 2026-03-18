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
        final int n = 5;
        int i;
        try {
            assertTrue(f.estVide());
            for (i = 0; i < n; i++) {
                f.enfiler(i);
                assertFalse(f.estVide());
            }
            while (i > 0) {
                assertFalse(f.estVide());
                f.defiler();
                i--;
            }
            assertTrue(f.estVide());
        } catch (Exception e) { fail(); }
    }

    @Test
    public void testValeurs() {
        try {
            f.enfiler(1);
            assertEquals(1, f.tete());
            f.defiler();
            f.enfiler(2);
            assertEquals(2, f.tete());
        } catch (Exception e) { fail(); }
    }

    @Test
    public void testEtat() {
        try {
            for (int i = 0; i < 10; i++) {
                f.enfiler(1);
                f.enfiler(2);
                assertEquals(1, f.tete());
                f.defiler();
                assertEquals(2, f.tete());
                f.defiler();
            }
        } catch (Exception e) { fail(); }
    }

    @Test(expected = FileVideException.class)
    public void testExceptionVide() throws FileVideException {
        f.defiler();
    }

    @Test(expected = FilePleineException.class)
    public void testExceptionPleine() throws FilePleineException {
        for (int i = 0; i < f.capacity; i++) {
            f.enfiler(i);
        }
        f.enfiler(f.capacity);
    }
}
