package ru.ifmo.ctddev.shalamov.task7;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
public class ShuffleArrayTask implements Task<List<Integer>, List<Integer>> {
    private List<Integer> list;

    /**
     * Constructs a ShuffleArrayTask and specifies the ArrayList to be shuffled.
     *
     * @param list ArrayList to be shuffled
     */
    public ShuffleArrayTask(List<Integer> list) {
        this.list = list;
    }

    /**
     * Shuffles the ArrayList
     *
     * @return shuffled ArrayList
     */
    @Override
    public List<Integer> doTask() {
        Collections.shuffle(list);
        return list;
    }

    @Override
    public String makeReport(List<Integer> list) {
        String res = new String();
        for (int i = 0; i < list.size(); ++i) {
            res = res.concat(list.get(i).toString() + " ");
        }
        return res;
    }

}