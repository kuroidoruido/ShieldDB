package nf.fr.k49.shielddb.gson;

import nf.fr.k49.shielddb.core.shield.BottomShield;
import nf.fr.k49.shielddb.core.storage.FileStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

public class BottomShieldTest {

    private static final String BASE_DIR = "target/manual";

    private FileStorage storage;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() throws IOException {
        String storagePath = BASE_DIR + "/User.json";

        storage = spy(new FileStorage(storagePath));

        String json = "[ \"String1\", \"String2\", \"String3\" ]";

        storage.write(json);
    }

    @After
    public void tearDown() throws IOException {
        storage.clear();
    }

    @Test
    public void jsonWillBeCorrectlyRead() throws IOException {
        BottomShield<String> bottomShield = new BottomShield<>(new ShieldDBGson<>(), storage);

        int size = bottomShield.size();

        assertThat(size, is(3));
    }

    @Test
    public void jsonWillBeCorrectlySaved() throws IOException {
        ShieldDBGson<String> mapper = new ShieldDBGson<>();

        BottomShield<String> bottomShield = new BottomShield<>(new ShieldDBGson<>(), storage);

        boolean saved = bottomShield.add("String4");

        assertTrue(saved);

        List<String> savedList = mapper.toList(storage.read());

        assertThat(savedList.size(), is(4));
    }

}
