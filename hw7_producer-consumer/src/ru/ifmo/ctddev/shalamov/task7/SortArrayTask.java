package ru.ifmo.ctddev.shalamov.task7;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private List<Integer> list;

    /**
     * Constructs a SortArrayTask and specifies the ArrayList to be sorted.
     *
     * @param list ArrayList to be sorted
     */
    public SortArrayTask(List<Integer> list) {
        this.list = list;
    }

    /**
     * Sorts the ArrayList
     *
     * @return sorted ArrayList
     */
    @Override
    public List<Integer> doTask() {
        Collections.sort(list);
        return list;
    }

    @Override
    public String makeReport(List<Integer> list) {
        String res = "";
        for (int i = 0; i < list.size(); ++i) {
            res = res.concat(list.get(i).toString() + " ");
        }
        return res;
    }
}

