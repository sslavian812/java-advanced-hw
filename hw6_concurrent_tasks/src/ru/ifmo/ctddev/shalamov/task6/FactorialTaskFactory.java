package ru.ifmo.ctddev.shalamov.task6;


/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 22:11
 * To change this template use File | Settings | File Templates.
 */
public class FactorialTaskFactory extends TaskFactory<Integer, Integer> {
    private int bound;

    /**
     * creates a FactorialTaskFactory with specified bound of factorial.
     *
     * @param bound
     */
    public FactorialTaskFactory(int bound) {
        this.bound = bound % 12;
    }

    @Override
    public Task<Integer, Integer> getTask() {
        return new FactorialTask();
    }

    @Override
    public Integer getArg() {
        return bound;
    }

    @Override
    public String taskFinished(Integer r) {
        String res = r.toString();
        return res;
    }
}
