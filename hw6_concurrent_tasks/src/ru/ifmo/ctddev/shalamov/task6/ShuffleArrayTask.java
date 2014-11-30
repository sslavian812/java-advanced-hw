package ru.ifmo.ctddev.shalamov.task6;

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

    /**
     * Empty constructor.
     * Does Nothing.
     */
    public ShuffleArrayTask() {
    }

    @Override
    public List<Integer> run(List<Integer> arg) {
        Collections.shuffle(arg);
        return arg;
    }

}