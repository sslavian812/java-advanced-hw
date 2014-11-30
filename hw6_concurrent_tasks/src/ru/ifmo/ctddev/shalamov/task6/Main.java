package ru.ifmo.ctddev.shalamov.task6;


/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    private static final int runners = 2;
    private static final int clients = 3;
    private static final int nThreads = 2;
    private static final int types = 3;

    public static void main(String[] args) {
        TaskFactory[] factories = new TaskFactory[types];
        factories[0] = new SortArrayTaskFactory(10);
        factories[1] = new ShuffleArrayTaskFactory(10);
        factories[2] = new FactorialTaskFactory(5);

        for (int i = 0; i < runners; i++) {
            TaskRunnerImpl runner = new TaskRunnerImpl(i, nThreads);

            for (int j = 0; j < clients; j++) {
                Client client = new Client(i * clients + j, runner, factories[j % types]);
                Thread clientThread = new Thread(client);
                clientThread.start();
            }
        }
    }
}
