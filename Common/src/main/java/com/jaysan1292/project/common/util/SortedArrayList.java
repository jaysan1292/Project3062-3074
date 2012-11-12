package com.jaysan1292.project.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/** @author Jason Recillo */
public class SortedArrayList<T extends Comparable<T>> extends ArrayList<T> {
    public SortedArrayList() {}

    public SortedArrayList(Collection<? extends T> collection) {
        super(collection);
    }

    public void insertSorted(T value) {
        add(value);
        for (int i = size() - 1; (i > 0) && (value.compareTo(get(i - 1)) < 0); i--) {
            Collections.swap(this, i, i - 1);
        }
    }

    /**
     * Returns a view of the portion of this list between the specified
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.  (If
     * {@code fromIndex} and {@code toIndex} are equal, the returned list is
     * empty.)  The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations.
     * <p/>
     * <p>This method eliminates the need for explicit range operations (of
     * the sort that commonly exist for arrays).  Any operation that expects
     * a list can be used as a range operation by passing a subList view
     * instead of a whole list.  For example, the following idiom
     * removes a range of elements from a list:
     * <pre>
     *      list.subList(from, to).clear();
     * </pre>
     * Similar idioms may be constructed for {@link #indexOf(Object)} and
     * {@link #lastIndexOf(Object)}, and all of the algorithms in the
     * {@link java.util.Collections} class can be applied to a subList.
     * <p/>
     * <p>The semantics of the list returned by this method become undefined if
     * the backing list (i.e., this list) is <i>structurally modified</i> in
     * any way other than via the returned list.  (Structural modifications are
     * those that change the size of this list, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws IllegalArgumentException  {@inheritDoc}
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return new SortedArrayList<T>(super.subList(fromIndex, toIndex));
    }
}
