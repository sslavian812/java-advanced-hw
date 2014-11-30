package ru.ifmo.ctddev.shalamov.task7;

import java.math.BigInteger;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 22:11
 * To change this template use File | Settings | File Templates.
 */
public class FactorialTaskFactory extends TaskFactory<Integer, BigInteger> {
    private int bound;

    /**
     * creates a FactorialTaskFactory with specified bound.
     *
     * @param bound
     */
    public FactorialTaskFactory(int bound) {
        this.bound = bound;
    }

    @Override
    public Task<Integer, BigInteger> getTask() {
        return new FactorialTask((int) (Math.random() * 123));
    }
}
