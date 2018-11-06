package nf.fr.k49.shielddb.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public interface JsonListDeserializer<T> extends JsonDeserializer<List<T>> {

	default List<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final List<T> res = new ArrayList<>();
		final JsonArray jsonArray = json.getAsJsonArray();

		for (JsonElement je : jsonArray) {
			res.add(deserializeOne(je, context));
		}

		return res;
	}

	T deserializeOne(JsonElement json, JsonDeserializationContext context) throws JsonParseException;

}
