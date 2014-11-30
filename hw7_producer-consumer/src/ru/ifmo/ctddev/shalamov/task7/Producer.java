package ru.ifmo.ctddev.shalamov.task7;

import java.util.concurrent.BlockingQueue;

/**
 * Created by viacheslav on 09.04.14.
 */
public class Producer implements Runnable {
    private TaskFactory factory;
    private BlockingQueue<Task> queueToRun;
    private int id;

    /**
     * Constructs a Producer with specified tasks factory, tasks queue and id.
     *
     * @param factory    TaskFactory
     * @param queueToRun BlockingQueue
     * @param id         Porducer's id
     */
    public Producer(TaskFactory factory, BlockingQueue queueToRun, int id) {
        this.factory = factory;
        this.queueToRun = queueToRun;
        this.id = id;
    }

    /**
     * Creates Tasks and puts them to the Queue until interrupted.
     */
    @Override
    public void run() {
        while (true) {
            Task t = factory.getTask();
            try {
                queueToRun.put(t);
            } catch (InterruptedException e) {
                System.out.println("Producer " + id + " interrupted");
                return;
            }
        }
    }
}
