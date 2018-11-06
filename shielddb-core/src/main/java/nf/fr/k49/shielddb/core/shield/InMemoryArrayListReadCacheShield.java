package nf.fr.k49.shielddb.core.shield;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class InMemoryArrayListReadCacheShield<T> implements ShieldDBShield<T> {

	private final List<T> cache;
	private ShieldDBShield<T> next;

	public InMemoryArrayListReadCacheShield() {
		this(new ArrayList<>());
	}

	public InMemoryArrayListReadCacheShield(final List<T> list) {
		this.cache = list;
	}

	public void invalidCache() {
		this.cache.clear();
		this.cache.addAll(next);
	}
	
	@Override
	public ShieldDBShield<T> getNextShield() {
		return this.next;
	}

	@Override
	public void setNextShield(ShieldDBShield<T> next) {
		this.next = next;
		invalidCache();
	}

	@Override
	public int size() {
		return this.cache.size();
	}

	@Override
	public boolean isEmpty() {
		return this.cache.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return this.cache.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return this.cache.iterator();
	}

	@Override
	public Object[] toArray() {
		return this.cache.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return this.cache.toArray(a);
	}

	@Override
	public boolean add(T e) {
		return this.cache.add(e) && this.next.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return this.cache.remove(o) && this.next.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.cache.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return this.cache.addAll(c) && this.next.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return this.cache.addAll(index, c) && this.next.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.cache.removeAll(c) && this.next.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.cache.retainAll(c) && this.next.retainAll(c);
	}

	@Override
	public void clear() {
		this.cache.clear();
		this.next.clear();
	}

	@Override
	public T get(int index) {
		return this.cache.get(index);
	}

	@Override
	public T set(int index, T element) {
		this.cache.set(index, element);
		return this.next.set(index, element);
	}

	@Override
	public void add(int index, T element) {
		this.cache.add(index, element);
		this.next.add(index, element);
	}

	@Override
	public T remove(int index) {
		this.cache.remove(index);
		return this.next.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.cache.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.cache.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return this.cache.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return this.cache.listIterator(index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return this.cache.subList(fromIndex, toIndex);
	}

	@Override
	public String toString() {
		return this.cache.toString();
	}

}
