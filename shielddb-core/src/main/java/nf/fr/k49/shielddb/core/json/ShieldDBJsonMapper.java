package nf.fr.k49.shielddb.core.json;

import java.util.List;

public interface ShieldDBJsonMapper<T> {

	List<T> toList(final String json);
	String toJson(final List<T> list);
}
