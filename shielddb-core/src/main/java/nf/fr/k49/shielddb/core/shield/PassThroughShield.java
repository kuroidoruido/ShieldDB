package nf.fr.k49.shielddb.core.shield;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public interface PassThroughShield<T> extends ShieldDBShield<T>{

	@Override
	default int size() {
		return getNextShield().size();
	}

	@Override
	default boolean isEmpty() {
		return getNextShield().isEmpty();
	}

	@Override
	default boolean contains(Object o) {
		return getNextShield().contains(o);
	}

	@Override
	default Iterator<T> iterator() {
		return getNextShield().iterator();
	}

	@Override
	default Object[] toArray() {
		return getNextShield().toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	default <T> T[] toArray(T[] a) {
		return getNextShield().toArray(a);
	}

	@Override
	default boolean add(T e) {
		return getNextShield().add(e);
	}

	@Override
	default boolean remove(Object o) {
		return getNextShield().remove(o);
	}

	@Override
	default boolean containsAll(Collection<?> c) {
		return getNextShield().containsAll(c);
	}

	@Override
	default boolean addAll(Collection<? extends T> c) {
		return getNextShield().addAll(c);
	}

	@Override
	default boolean addAll(int index, Collection<? extends T> c) {
		return getNextShield().addAll(index, c);
	}

	@Override
	default boolean removeAll(Collection<?> c) {
		return getNextShield().removeAll(c);
	}

	@Override
	default boolean retainAll(Collection<?> c) {
		return getNextShield().removeAll(c);
	}

	@Override
	default void clear() {
		getNextShield().clear();
	}

	@Override
	default T get(int index) {
		return getNextShield().get(index);
	}

	@Override
	default T set(int index, T element) {
		return getNextShield().set(index, element);
	}

	@Override
	default void add(int index, T element) {
		getNextShield().add(index, element);
	}

	@Override
	default T remove(int index) {
		return getNextShield().remove(index);
	}

	@Override
	default int indexOf(Object o) {
		return getNextShield().indexOf(o);
	}

	@Override
	default int lastIndexOf(Object o) {
		return getNextShield().lastIndexOf(o);
	}

	@Override
	default ListIterator<T> listIterator() {
		return getNextShield().listIterator();
	}

	@Override
	default ListIterator<T> listIterator(int index) {
		return getNextShield().listIterator(index);
	}

	@Override
	default List<T> subList(int fromIndex, int toIndex) {
		return getNextShield().subList(fromIndex, toIndex);
	}
	
}
