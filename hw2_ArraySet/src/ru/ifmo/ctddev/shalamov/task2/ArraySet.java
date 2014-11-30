package ru.ifmo.ctddev.shalamov.task2;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 05.03.14
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public class ArraySet<E> extends AbstractSet<E> implements NavigableSet<E> {

    private final List<E> elements;
    private final List<E> elementsR;
    private final Comparator<? super E> comparator;
    private boolean naturalOrder = false;


    public ArraySet(Collection<E> collection, Comparator<? super E> comparator) {
        ArrayList<E> array = new ArrayList<E>(collection);
        Collections.sort(array, comparator);
        ArrayList<E> list = new ArrayList<E>(array.size());


        if (array.size() != 0) {
            list.add(array.get(0));
        }

        for (int i = 1; i < array.size(); ++i) {
            if (comparator.compare(array.get(i), array.get(i - 1)) != 0) {
                list.add(array.get(i));
            }
        }
        list.trimToSize();
        this.elements = list;
        this.comparator = comparator;

        elementsR = new ArrayList<E>(list.size());
        for (int i = 0; i < list.size(); ++i) {
                elementsR.add(list.get(i));
        }
    }

    public ArraySet(Collection<E> collection) {
        this(collection, new Comparator<E>() {
            @Override
            @SuppressWarnings("unchecked")
            public int compare(E o1, E o2) {
                return ((Comparable<? super E>) o1).compareTo(o2);
            }
        });
        naturalOrder = true;
    }

    public ArraySet() {
        elements = new ArrayList<E>();
        elementsR = new ArrayList<E>();
        comparator = new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return 0;
            }
        };
        naturalOrder = true;
    }

    // service:
    private int search(E e) {
        return Collections.binarySearch(elements, e, comparator);
    }

    private ArraySet(List<E> collection, List<E> collectionR, Comparator<? super E> comparator, boolean order) {
        this.elements = collection;
        this.elementsR = collectionR;
        this.comparator = comparator;
        this.naturalOrder = order;
    }


    // retrieve element:

    @Override
    public E ceiling(E e) //  >=
    {
        int pos = search(e);
        if (pos >= 0)
            return elements.get(pos);
        pos = -(pos + 1);
        if (pos == elements.size())
            return null;
        else
            return elements.get(pos);
    }

    @Override
    public E floor(E e)  // <=
    {
        int pos;
        if ((pos = search(e)) >= 0)
            return elements.get(pos);

        pos = -(pos + 1);
        if (pos == 0)
            return null;
        else
            return elements.get(pos - 1);
    }

    @Override
    public E higher(E e) // >
    {
        int pos;
        if ((pos = search(e)) >= 0)
            return (pos < elements.size() - 1) ? elements.get(pos + 1) : null;
        pos = -(pos + 1);
        if (pos == elements.size())
            return null;
        else
            return elements.get(pos);
    }

    @Override
    public E lower(E e)  // <
    {
        int pos;
        if ((pos = search(e)) >= 0)
            return (pos > 0) ? elements.get(pos - 1) : null;
        pos = -(pos + 1);
        if (pos == 0)
            return null;
        else
            return elements.get(pos - 1);
    }

    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException();
    }

    // retrieve iterator:

    /**
     * Returns an  iterator over the elements in "ascending order".
     * "ascending order" means here the order, provided by given comparator
     * and "descending order" means - reversed "ascending order"
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Iterator<E> it = elements.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public E next() {
                return it.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Returns an  iterator over the elements in "descending order".
     * "ascending order" means here the order, provided by given comparator
     * and "descending order" means - reversed "ascending order"
     */
    @Override
    public Iterator<E> descendingIterator() {
        return descendingSet().iterator();
    }

    // retrieve views

    @Override
    public NavigableSet<E> descendingSet() {
        return new ArraySet<E>(this.elementsR, this.elements, Collections.reverseOrder(comparator), naturalOrder);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return headSet(toElement, false);
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        int pos = search(toElement);

        if (pos < 0)  // включай-не_включай, все равно такого нет, а нам дали больший
            pos = (-(pos + 1)) - 1;
        else if (!inclusive)
            --pos;

        return new ArraySet<E>(elements.subList(0, pos + 1), elementsR.subList(size() - pos - 1, size()), this.comparator, naturalOrder);
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return headSet(toElement, toInclusive).tailSet(fromElement, fromInclusive);
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        int pos = search(fromElement);

        if (pos < 0)
            pos = -(pos + 1);
        else if (!inclusive)
            ++pos;

        return new ArraySet<E>(elements.subList(pos, elements.size()), elementsR.subList(0, size() - pos), this.comparator, naturalOrder);
    }

    // inherited from AbstractSet:

    @Override
    public Comparator<? super E> comparator() {
        if (!naturalOrder) {
            return this.comparator;
        } else {
            return null;
        }
    }

    public E first() {
        if (elements.size() != 0)
            return elements.get(0);
        else
            throw new NoSuchElementException();
    }

    public E last() {
        if (elements.size() != 0)
            return elements.get(elements.size() - 1);
        else
            throw new NoSuchElementException();
    }


    @Override
    public boolean contains(Object o) {
        return Collections.binarySearch(elements, (E) o, comparator) >= 0;
    }

    @Override
    public int size() {
        return elements.size();
    }

}
