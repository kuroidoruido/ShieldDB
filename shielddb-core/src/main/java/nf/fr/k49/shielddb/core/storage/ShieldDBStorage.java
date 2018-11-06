package nf.fr.k49.shielddb.core.storage;

import java.io.IOException;

public interface ShieldDBStorage {

	String read() throws IOException;

	void write(final String json) throws IOException;
	
	void clear() throws IOException;
}
