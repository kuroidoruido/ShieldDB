package nf.fr.k49.shielddb.core.shield;

import java.util.List;

public interface ShieldDBShield<T> extends List<T> {

	ShieldDBShield<T> getNextShield();
	void setNextShield(ShieldDBShield<T> next);
}
