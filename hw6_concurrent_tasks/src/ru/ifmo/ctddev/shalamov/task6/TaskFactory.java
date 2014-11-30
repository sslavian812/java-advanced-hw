package ru.ifmo.ctddev.shalamov.task6;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class TaskFactory<Res, Arg> {

    /**
     * Creates and provides a Task.
     *
     * @return
     */
    public abstract Task<Res, Arg> getTask();

    /**
     * Provides Task's argument
     *
     * @return
     */
    public abstract Arg getArg();

    /**
     * Provides a String-report of Task execution result.
     *
     * @param r
     * @return
     */
    public abstract String taskFinished(Res r);
}
