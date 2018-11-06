package nf.fr.k49.shielddb.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import nf.fr.k49.shielddb.core.json.ShieldDBJsonMapper;

public class ShieldDBGson<T> implements ShieldDBJsonMapper<T> {

	private final Gson gson;
	private final Type collectionType;
	
	public ShieldDBGson() {
		this(new Gson());
	}
	
	public ShieldDBGson(final Gson gson) {
		this(gson, GsonTypeUtils.getType());
	}
	
	public ShieldDBGson(final Gson gson, final Type collectionType) {
		this.gson = gson;
		this.collectionType = collectionType;
	}

	@Override
	public List<T> toList(String json) {
		if (json == null || json.length() == 0) {
			return new ArrayList<>();
		}
		return new ArrayList<>(gson.fromJson(json, collectionType));
	}

	@Override
	public String toJson(List<T> list) {
		return gson.toJson(list);
	}

}
