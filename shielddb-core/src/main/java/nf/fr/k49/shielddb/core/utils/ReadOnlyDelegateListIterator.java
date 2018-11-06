package nf.fr.k49.shielddb.core.utils;

import java.util.ListIterator;

public class ReadOnlyDelegateListIterator<T> implements ListIterator<T> {

	protected final ListIterator<T> delegate;
	protected final String errMsg;

	public ReadOnlyDelegateListIterator(final ListIterator<T> delegate, final String errMsg) {
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
	public boolean hasPrevious() {
		return delegate.hasPrevious();
	}

	@Override
	public T previous() {
		return delegate.previous();
	}

	@Override
	public int nextIndex() {
		return delegate.nextIndex();
	}

	@Override
	public int previousIndex() {
		return delegate.previousIndex();
	}

	@Override
	public void set(T e) {
        throw new UnsupportedOperationException(errMsg);
	}

	@Override
	public void add(T e) {
        throw new UnsupportedOperationException(errMsg);
	}
	
	@Override
	public void remove() {
        throw new UnsupportedOperationException(errMsg);
    }
}
