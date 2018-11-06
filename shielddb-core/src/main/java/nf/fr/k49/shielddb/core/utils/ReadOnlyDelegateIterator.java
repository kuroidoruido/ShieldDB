package nf.fr.k49.shielddb.core.utils;

import java.util.Iterator;

public class ReadOnlyDelegateIterator<T> implements Iterator<T> {

	protected final Iterator<T> delegate;
	protected final String errMsg;
	
	public ReadOnlyDelegateIterator(final Iterator<T> delegate, final String errMsg) {
		this.delegate = delegate;
		this.errMsg = errMsg;
	}
	
	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public T next() {
		return delegate.next();
	}
	
	@Override
	public void remove() {
        throw new UnsupportedOperationException(errMsg);
    }
}
