package nf.fr.k49.shielddb.core.shield;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AtomicShield<T> implements ShieldDBShield<T> {
	private ShieldDBShield<T> next;

	public AtomicShield() {
	}

	@Override
	public ShieldDBShield<T> getNextShield() {
		return this.next;
	}

	@Override
	public void setNextShield(ShieldDBShield<T> next) {
		this.next = next;
	}

	@Override
	public synchronized int size() {
		return getNextShield().size();
	}

	@Override
	public synchronized boolean isEmpty() {
		return getNextShield().isEmpty();
	}

	@Override
	public synchronized boolean contains(Object o) {
		return getNextShield().contains(o);
	}

	@Override
	public synchronized Iterator<T> iterator() {
		return getNextShield().iterator();
	}

	@Override
	public synchronized Object[] toArray() {
		return getNextShield().toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public synchronized <T> T[] toArray(T[] a) {
		return getNextShield().toArray(a);
	}

	@Override
	public synchronized boolean add(T e) {
		return getNextShield().add(e);
	}

	@Override
	public synchronized boolean remove(Object o) {
		return getNextShield().remove(o);
	}

	@Override
	public synchronized boolean containsAll(Collection<?> c) {
		return getNextShield().containsAll(c);
	}

	@Override
	public synchronized boolean addAll(Collection<? extends T> c) {
		return getNextShield().addAll(c);
	}

	@Override
	public synchronized boolean addAll(int index, Collection<? extends T> c) {
		return getNextShield().addAll(index, c);
	}

	@Override
	public synchronized boolean removeAll(Collection<?> c) {
		return getNextShield().removeAll(c);
	}

	@Override
	public synchronized boolean retainAll(Collection<?> c) {
		return getNextShield().removeAll(c);
	}

	@Override
	public synchronized void clear() {
		getNextShield().clear();
	}

	@Override
	public synchronized T get(int index) {
		return getNextShield().get(index);
	}

	@Override
	public synchronized T set(int index, T element) {
		return getNextShield().set(index, element);
	}

	@Override
	public synchronized void add(int index, T element) {
		getNextShield().add(index, element);
	}

	@Override
	public synchronized T remove(int index) {
		return getNextShield().remove(index);
	}

	@Override
	public synchronized int indexOf(Object o) {
		return getNextShield().indexOf(o);
	}

	@Override
	public synchronized int lastIndexOf(Object o) {
		return getNextShield().lastIndexOf(o);
	}

	@Override
	public synchronized ListIterator<T> listIterator() {
		return getNextShield().listIterator();
	}

	@Override
	public synchronized ListIterator<T> listIterator(int index) {
		return getNextShield().listIterator(index);
	}

	@Override
	public synchronized List<T> subList(int fromIndex, int toIndex) {
		return getNextShield().subList(fromIndex, toIndex);
	}

	@Override
	public synchronized String toString() {
		return next.toString();
	}

}
