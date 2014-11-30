package ru.ifmo.ctddev.shalamov.task7;

import java.util.concurrent.Callable;

/**
 * Created by viacheslav on 09.04.14.
 */
public interface Task<Arg, Res> {

    /**
     * Executes the Task.
     *
     * @return returns the result of execution.
     */
    public Res doTask();

    /**
     * Makes a String representing a report of {@link ru.ifmo.ctddev.shalamov.task7.Task} execution
     *
     * @param r - result of Task execution
     * @return String representations of r
     */
    public String makeReport(Res r);
}
