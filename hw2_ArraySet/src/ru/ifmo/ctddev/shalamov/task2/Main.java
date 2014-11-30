package ru.ifmo.ctddev.shalamov.task2;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 05.03.14
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int i = 0; i < 10; ++i)
            al.add(i);

        ArraySet<Integer> arraySet = new ArraySet<Integer>(al, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });


        System.out.println(arraySet.ceiling(9));
        System.out.println(arraySet.floor(0));
        System.out.println(arraySet.higher(9));
        System.out.println(arraySet.lower(0));
        System.out.println(arraySet.contains(11));

        Iterator<Integer> dIter = arraySet.descendingIterator();

        while (dIter.hasNext()) {
            System.out.print(dIter.next() + " ");
        }

        NavigableSet<Integer> left = arraySet.subSet(5, false, 8, false);
        Iterator<Integer> it = left.iterator();

        System.out.println("");

        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }

        System.out.println("");
        NavigableSet<Integer> d = arraySet.descendingSet();

        Iterator<Integer> id = d.descendingIterator();
        while (id.hasNext()) {
            System.out.print(id.next() + " ");
        }


    }


}
