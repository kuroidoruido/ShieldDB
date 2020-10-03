package nf.fr.k49.shielddb.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nf.fr.k49.shielddb.core.json.ShieldDBJsonMapper;
import nf.fr.k49.shielddb.core.shield.BottomShield;
import nf.fr.k49.shielddb.core.shield.ShieldDBShield;
import nf.fr.k49.shielddb.core.storage.ShieldDBStorage;

public class ShieldDBBuilder<T> {

	private ShieldDBJsonMapper<T> mapper;
	private List<ShieldDBShield<T>> shields;
	private ShieldDBStorage storage;

	public ShieldDBBuilder() {
		this.shields = new ArrayList<>();
	}
	
	public List<T> build() {
		final BottomShield<T> bottom = new BottomShield<>(mapper, storage);
		shields.add(bottom);

		Iterator<ShieldDBShield<T>> shieldIterator = shields.iterator();
		ShieldDBShield<T> outerShield = shieldIterator.next();
		while (shieldIterator.hasNext()) {
			ShieldDBShield<T> innerShield = shieldIterator.next();
			outerShield.setNextShield(innerShield);
			outerShield = innerShield;
		}

		return shields.get(0);
	}


	/**
	 * Sets the storage engine.
	 * 
	 * @param storage set the storage implementation to use.
	 * @return the current ShieldDBBuilder instance.
	 */
	public ShieldDBBuilder<T> storage(ShieldDBStorage storage) {
		this.storage = storage;
		return this;
	}

	/**
	 * Sets the json mapper.
	 * 
	 * @param mapper set the json mapper implementation to use.
	 * @return the current ShieldDBBuilder instance.
	 */
	public ShieldDBBuilder<T> mapper(ShieldDBJsonMapper<T> mapper) {
		this.mapper = mapper;
		return this;
	}

	/**
	 * Adds a new Shield to protect the file.
	 * 
	 * @param shield add shield to add.
	 * @return the current ShieldDBBuilder instance.
	 */
	public ShieldDBBuilder<T> shield(ShieldDBShield<T> shield) {
		this.shields.add(shield);
		return this;
	}
}
