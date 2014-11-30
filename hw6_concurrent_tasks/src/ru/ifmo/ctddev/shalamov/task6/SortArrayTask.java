package ru.ifmo.ctddev.shalamov.task6;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 21:34
 * To change this template use File | Settings | File Templates.
 */
public class SortArrayTask implements Task<List<Integer>, List<Integer>> {

    /**
     * Empty constructor.
     * Does Nothing.
     */
    public SortArrayTask() {
    }

    @Override
    public List<Integer> run(List<Integer> arg) {
        Collections.sort(arg);
        return arg;
    }

}

