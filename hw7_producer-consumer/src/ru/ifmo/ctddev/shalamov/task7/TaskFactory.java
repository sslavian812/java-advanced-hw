package ru.ifmo.ctddev.shalamov.task7;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class TaskFactory<Res, Arg> {
    /**
     * Provides a Task, Created by the Factory.
     *
     * @return created Task.
     */
    public abstract Task getTask();
}
