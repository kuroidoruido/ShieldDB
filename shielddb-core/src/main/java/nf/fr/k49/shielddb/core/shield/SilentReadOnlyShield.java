package nf.fr.k49.shielddb.core.shield;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import nf.fr.k49.shielddb.core.utils.SilentlyReadOnlyDelegateIterator;
import nf.fr.k49.shielddb.core.utils.SilentlyReadOnlyDelegateListIterator;

public class SilentReadOnlyShield<T> implements PassThroughShield<T> {

	private ShieldDBShield<T> next;

	public SilentReadOnlyShield() {
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
		return false;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
	}

	@Override
	public T set(int index, T element) {
		return null;
	}

	@Override
	public void add(int index, T element) {
	}

	@Override
	public T remove(int index) {
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return new SilentlyReadOnlyDelegateIterator<>(next.iterator());
	}

	@Override
	public ListIterator<T> listIterator() {
		return new SilentlyReadOnlyDelegateListIterator<>(next.listIterator());
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new SilentlyReadOnlyDelegateListIterator<>(next.listIterator(index));
	}

	@Override
	public String toString() {
		return this.next.toString();
	}

}
