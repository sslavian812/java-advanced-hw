package ru.ifmo.ctddev.shalamov.task6;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public interface Task<X, Y> {
    /**
     * Executes the Task.
     *
     * @param value Task's argument
     * @return Task's execution's result.
     */
    public X run(Y value);
}

