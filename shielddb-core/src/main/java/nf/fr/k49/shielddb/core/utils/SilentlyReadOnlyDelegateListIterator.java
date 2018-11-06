package nf.fr.k49.shielddb.core.utils;

import java.util.ListIterator;

public class SilentlyReadOnlyDelegateListIterator<T> extends ReadOnlyDelegateListIterator<T> {

	public SilentlyReadOnlyDelegateListIterator(final ListIterator<T> delegate) {
		super(delegate, "");
	}

	@Override
	public void set(T e) {
	}

	@Override
	public void add(T e) {
	}

	@Override
	public void remove() {
	}
}
