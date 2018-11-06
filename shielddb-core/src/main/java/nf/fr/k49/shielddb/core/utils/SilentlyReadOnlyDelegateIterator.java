package nf.fr.k49.shielddb.core.utils;

import java.util.Iterator;

public class SilentlyReadOnlyDelegateIterator<T> extends ReadOnlyDelegateIterator<T> {

	public SilentlyReadOnlyDelegateIterator(final Iterator<T> delegate) {
		super(delegate, "");
	}

	@Override
	public void remove() {
	}
}
