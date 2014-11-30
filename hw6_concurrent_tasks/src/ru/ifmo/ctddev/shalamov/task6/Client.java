package ru.ifmo.ctddev.shalamov.task6;


/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public class Client<Res, Arg> implements Runnable {

    private int id;
    private TaskRunner runner;
    private TaskFactory<Res, Arg> factory;

    private Client() {
        this(0, null, null);
    }

    /**
     * Constructor without id. id 0 will be used.
     *
     * @param runner  {@link ru.ifmo.ctddev.shalamov.task6.TaskRunner}
     * @param factory {@link ru.ifmo.ctddev.shalamov.task6.TaskFactory}
     */
    public Client(TaskRunner runner, TaskFactory factory) {
        this(0, runner, factory);
    }

    /**
     * Creates a Client with specified id, TaskRunner and TaskFactory.
     *
     * @param clientId id
     * @param runner   {@link ru.ifmo.ctddev.shalamov.task6.TaskRunner}
     * @param factory  {@link ru.ifmo.ctddev.shalamov.task6.TaskFactory}
     */
    public Client(int clientId, TaskRunner runner, TaskFactory factory) {
        this.runner = runner;
        this.factory = factory;
        this.id = clientId;
    }

    /**
     * creates a tasks, executes it with TaskRunner and prints the result.
     */
    @Override
    public void run() {
        while (true) {
            System.out.println("Client: " + id + " +");
            Task<Res, Arg> task = factory.getTask();
            Arg arg = factory.getArg();
            Res res = runner.run(task, arg); // повисаем
            if (res == null) {
                System.out.println("Client " + id + " : task FAILED on runner " + runner.getId());
                continue;
            }
            String s = factory.taskFinished(res);
            System.out.println("Client " + id + " : task finished on runner " + runner.getId() + ": " + s);
            System.out.println("Client: " + id + " -");
        }
    }
}
