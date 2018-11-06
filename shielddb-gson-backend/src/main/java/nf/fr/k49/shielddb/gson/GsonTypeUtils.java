package nf.fr.k49.shielddb.gson;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class GsonTypeUtils {

	public static <T> Type getType() {
		return new TypeToken<List<T>>() {
		}.getType();
	}
}
