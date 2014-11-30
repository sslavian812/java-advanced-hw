package ru.ifmo.ctddev.shalamov.task7;

import java.util.concurrent.BlockingQueue;

/**
 * Created by viacheslav on 09.04.14.
 */
public class Publisher implements Runnable {
    private BlockingQueue<String> queueToPublish;
    private int id;

    /**
     * Constructs a Publisher with specified reports queue and id.
     *
     * @param queueToPublish reports queue
     * @param id             Publisher's id
     */
    public Publisher(BlockingQueue queueToPublish, int id) {
        this.queueToPublish = queueToPublish;
        this.id = id;
    }

    /**
     * Takes reports from queue and publishes them one by one till interrupted.
     */
    @Override
    public void run() {
        while (true) {
            try {
                String s = queueToPublish.take();
                System.out.println("done(" + id + "): " + s);
            } catch (InterruptedException e) {
                System.out.println("Publisher " + id + " interrupted");
                return;
            }
        }
    }
}