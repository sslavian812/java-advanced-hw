package ru.ifmo.ctddev.shalamov.task7;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */
public class ShuffleArrayTaskFactory extends TaskFactory<List<Integer>, List<Integer>> {
    private final int size;

    /**
     * Constructs a ShuffleArrayTaskFactory and specifies a size of Arrays to be shuffled in later created Tasks
     *
     * @param size size of Arrays to be shuffled in later created Tasks
     */
    public ShuffleArrayTaskFactory(int size) {
        this.size = size;
    }

    @Override
    public Task<List<Integer>, List<Integer>> getTask() {
        return new ShuffleArrayTask(getArg());
    }

    private List<Integer> getArg() {
        return ArrayFactory.getRandomList(size);
    }


}
