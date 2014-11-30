package ru.ifmo.ctddev.shalamov.task7;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

/**
 * Created by viacheslav on 09.04.14.
 */
public class Worker implements Runnable {

    private BlockingQueue<Task> queueToRun;
    private BlockingQueue<String> queueToPublish;
    private int id;

    /**
     * Constructs a Worker with specified tasks queue, reports queue and id.
     *
     * @param queueToRun     reports queue
     * @param queueToPublish reports queue
     * @param id             Publisher's id
     */
    public Worker(BlockingQueue queueToRun, BlockingQueue queueToPublish, int id) {
        this.queueToRun = queueToRun;
        this.queueToPublish = queueToPublish;
        this.id = id;
    }


    /**
     * Takes and Executes Tasks, then puts them to the Queue until interrupted.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Task t = queueToRun.take();
                String s = t.makeReport(t.doTask());
                queueToPublish.put(s);
            } catch (InterruptedException e) {
                System.out.println("Worker " + id + " interrupted");
                return;
            }
        }
    }
}