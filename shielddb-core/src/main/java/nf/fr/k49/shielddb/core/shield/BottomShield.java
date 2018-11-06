package nf.fr.k49.shielddb.core.shield;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import nf.fr.k49.shielddb.core.ShieldDBIOException;
import nf.fr.k49.shielddb.core.json.ShieldDBJsonMapper;
import nf.fr.k49.shielddb.core.storage.ShieldDBStorage;

public class BottomShield<T> implements ShieldDBShield<T> {

	private final ShieldDBJsonMapper<T> mapper;
	private final ShieldDBStorage storage;

	public BottomShield(final ShieldDBJsonMapper<T> mapper, final ShieldDBStorage storage) {
		this.mapper = mapper;
		this.storage = storage;
	}

	@Override
	public ShieldDBShield<T> getNextShield() {
		// nothing to do because this shield must be the last shield
		return null;
	}
	
	@Override
	public void setNextShield(ShieldDBShield<T> next) {
		// nothing to do because this shield must be the last shield
	}

	private List<T> read() {
		try {
			return this.mapper.toList(this.storage.read());
		} catch (IOException e) {
			throw new ShieldDBIOException(e);
		}
	}

	private boolean save(final List<T> list) {
		try {
			this.storage.write(this.mapper.toJson(list));
			return true;
		} catch (IOException e) {
			throw new ShieldDBIOException(e);
		}
	}

	@Override
	public int size() {
		return read().size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		return read().contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return read().iterator();
	}

	@Override
	public Object[] toArray() {
		return read().toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return read().toArray(a);
	}

	@Override
	public boolean add(T e) {
		final List<T> l = read();
		return l.add(e) && save(l);
	}

	@Override
	public boolean remove(Object o) {
		final List<T> l = read();
		return l.remove(o) && save(l);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return read().containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		final List<T> l = read();
		return l.addAll(c) && save(l);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		final List<T> l = read();
		return l.addAll(index, c) && save(l);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		final List<T> l = read();
		return l.removeAll(c) && save(l);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		final List<T> l = read();
		return l.retainAll(c) && save(l);
	}

	@Override
	public void clear() {
		try {
			this.storage.clear();
		} catch (IOException e) {
			throw new ShieldDBIOException(e);
		}
	}

	@Override
	public T get(int index) {
		return read().get(index);
	}

	@Override
	public T set(int index, T element) {
		final List<T> l = read();
		final T res = l.set(index, element);
		save(l);
		return res;
	}

	@Override
	public void add(int index, T element) {
		final List<T> l = read();
		l.add(index, element);
		save(l);
	}

	@Override
	public T remove(int index) {
		final List<T> l = read();
		T res = l.remove(index);
		save(l);
		return res;
	}

	@Override
	public int indexOf(Object o) {
		return read().indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return read().lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return read().listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return read().listIterator(index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return read().subList(fromIndex, toIndex);
	}

	@Override
	public String toString() {
		return read().toString();
	}
}
