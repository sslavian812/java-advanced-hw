package ru.ifmo.ctddev.shalamov.task7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    private static final int producersNum = 10;
    private static final int workersNum = 10;
    private static final int publishersNum = 10;
    private static final int types = 3;
    private static final int amount = 15;

    private static BlockingQueue<Task> toRun;
    private static BlockingQueue<String> toPublish;

    private static ThreadPoolExecutor producersPool;
    private static ThreadPoolExecutor workersPool;
    private static ThreadPoolExecutor publishersPool;

    public static void main(String[] args) {
        TaskFactory[] factories = new TaskFactory[types];
        factories[0] = new SortArrayTaskFactory(20);
        factories[1] = new ShuffleArrayTaskFactory(10);
        factories[2] = new FactorialTaskFactory(5);

        toRun = new LinkedBlockingQueue<Task>(amount);
        toPublish = new LinkedBlockingQueue<String>(amount);

        //producers
        producersPool = new ThreadPoolExecutor(producersNum, producersNum, Integer.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(producersNum));
//        BlockingQueue<Runnable> producersQueue = new LinkedBlockingQueue<>(producersNum);
        for (int i = 0; i < producersNum; ++i) {
            Producer p = new Producer(factories[i % types], toRun, i);
            producersPool.execute(p);
        }


        //workers
        workersPool = new ThreadPoolExecutor(workersNum, workersNum, Integer.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(workersNum));
        //BlockingQueue<Runnable> workersQueue = new LinkedBlockingQueue<>(workersNum);
        for (int i = 0; i < workersNum; ++i) {
            Worker w = new Worker(toRun, toPublish, i);
            workersPool.execute(w);
        }


        //publishers
        publishersPool = new ThreadPoolExecutor(publishersNum, publishersNum, Integer.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(publishersNum));
        //BlockingQueue<Runnable> publishersQueue = new LinkedBlockingQueue<>(publishersNum);
        for (int i = 0; i < publishersNum; ++i) {
            Publisher p = new Publisher(toPublish, i);
            publishersPool.execute(p);
        }
    }
}
