package nf.fr.k49.shielddb.gson;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ShieldDBGsonTest {

    @Test
    public void emptyArrayListWillBeReturnedOnProvidedNullJSON() {
        ShieldDBGson<String> shieldDBGson = new ShieldDBGson<>(new Gson(), String.class);

        List<String> list = shieldDBGson.toList(null);

        assertThat(list.size(), is(0));
    }

    @Test
    public void emptyArrayListWillBeReturnedOnProvidedEmptyJSON() {
        ShieldDBGson<String> shieldDBGson = new ShieldDBGson<>(new Gson(), String.class);

        List<String> list = shieldDBGson.toList("");

        assertThat(list.size(), is(0));
    }

    @Test
    public void correctArrayListWillBeReturnedOnProvidedJSONArray() {
        String json = "[ \"String1\", \"String2\", \"String3\" ]";

        ShieldDBGson<String> shieldDBGson = new ShieldDBGson<>(new Gson(), GsonTypeUtils.getType());

        List<String> list = shieldDBGson.toList(json);

        assertThat(list.size(), is(3));
        assertThat(list, containsInAnyOrder("String1", "String2", "String3"));
    }

}
