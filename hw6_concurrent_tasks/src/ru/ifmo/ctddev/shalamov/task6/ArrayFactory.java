package ru.ifmo.ctddev.shalamov.task6;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 21:41
 * To change this template use File | Settings | File Templates.
 */
public class ArrayFactory {
    /**
     * provides an array of integers
     *
     * @param size size of array
     * @return an array of random integers
     */
    public static List<Integer> getRandomList(int size) {
        List<Integer> l = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            l.add((int) (Math.random() * (double) size + 1));
        }
        return l;
    }
}
