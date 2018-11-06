package nf.fr.k49.shielddb.core;

public class ShieldDB {

	private ShieldDB() {
	}
	
	public static <T> ShieldDBBuilder<T> builder() {
		return new ShieldDBBuilder<T>();
	}
}
