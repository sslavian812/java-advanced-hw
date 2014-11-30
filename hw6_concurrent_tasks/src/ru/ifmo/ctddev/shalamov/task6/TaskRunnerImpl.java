package ru.ifmo.ctddev.shalamov.task6;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 21:21
 * To change this template use File | Settings | File Templates.
 */
public class TaskRunnerImpl implements TaskRunner {
    private int id;
    private final Queue<SecuredTask<?, ?>> taskQueue;
    private Thread[] threads;


    private class SecuredTask<X, Y> {
        private Task<X, Y> task;
        private Y value;
        private X result;
        private volatile boolean ready;

        public SecuredTask(Task<X, Y> task, Y value) {
            this.task = task;
            this.value = value;
        }

        public synchronized boolean isReady() {
            return ready;
        }

        public X getResult() {
            return result;
        }

        public synchronized void exec() {
            result = task.run(value);
            ready = true;
            notify();
        }
    }

    private class QueueWorker<X, Y> implements Runnable {

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    SecuredTask<?, ?> currentTask;
                    synchronized (taskQueue) {
                        while (taskQueue.isEmpty()) {
                            taskQueue.wait();
                        }
                        currentTask = taskQueue.poll();
                    }
                    currentTask.exec();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Constructs class which can run tasks in given number of threads.
     *
     * @param id id of TaskRunner
     * @param n  how much threads
     */
    public TaskRunnerImpl(int id, int n) {
        this.id = id;
        this.taskQueue = new ArrayDeque<SecuredTask<?, ?>>();
        threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(new QueueWorker(), Integer.toString(i));
            threads[i].start();
        }
    }

    public <X, Y> X run(Task<X, Y> task, Y value) {
        SecuredTask<X, Y> newTask = new SecuredTask<X, Y>(task, value);
        try {
            synchronized (taskQueue) {
                taskQueue.offer(newTask);
                taskQueue.notify();
            }
            synchronized (newTask) {
                while (!newTask.isReady()) {
                    newTask.wait();
                }
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
        return newTask.getResult();
    }

    @Override
    public int getId() {
        return id;
    }
}
