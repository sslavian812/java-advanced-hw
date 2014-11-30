package ru.ifmo.ctddev.shalamov.task6;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public interface TaskRunner {

    /**
     * runs the given task in one of specified threads, waits for completion and returns the result.
     *
     * @param task  a {@link ru.ifmo.ctddev.shalamov.task6.Task} to be executed
     * @param value argument for task
     * @param <X>   result's tupe
     * @param <Y>   Task's argument's type
     * @return the result of task execution if succeed, null otherwise
     */
    public <X, Y> X run(Task<X, Y> task, Y value);

    /**
     * provides id of TaskRunner
     *
     * @return id of TaskRunner
     */
    public int getId();
}
