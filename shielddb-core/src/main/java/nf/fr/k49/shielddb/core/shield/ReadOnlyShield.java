package nf.fr.k49.shielddb.core.shield;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import nf.fr.k49.shielddb.core.utils.ReadOnlyDelegateIterator;
import nf.fr.k49.shielddb.core.utils.ReadOnlyDelegateListIterator;

public class ReadOnlyShield<T> implements PassThroughShield<T> {

	private static final String ERR_MSG = "You cannot write on this list because it is a read only ShieldDB.";
	private ShieldDBShield<T> next;

	public ReadOnlyShield() {
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
	public boolean add(T e) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	@Override
	public T set(int index, T element) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	@Override
	public void add(int index, T element) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	@Override
	public T remove(int index) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	@Override
	public Iterator<T> iterator() {
		return new ReadOnlyDelegateIterator<>(next.iterator(), ERR_MSG);
	}

	@Override
	public ListIterator<T> listIterator() {
		return new ReadOnlyDelegateListIterator<>(next.listIterator(), ERR_MSG);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new ReadOnlyDelegateListIterator<>(next.listIterator(index), ERR_MSG);
	}

	@Override
	public String toString() {
		return this.next.toString();
	}

}
