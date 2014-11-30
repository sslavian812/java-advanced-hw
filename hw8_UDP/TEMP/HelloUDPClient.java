

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.*;

/**
 * Created by viacheslav on 22.04.14.
 */
public class HelloUDPClient {

    private static final int N_THREADS = 10;


    /**
     * Starts N_THREADS threads, witch generate requests to HelloUDPServer and printing responses to the stdout.
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("invalid arguments");
            System.exit(1);
        }

        int port = Integer.parseInt(args[1]);
        InetAddress address = null;
        try {
            address = InetAddress.getByName(args[0]);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        ThreadPoolExecutor tpe = new ThreadPoolExecutor(N_THREADS, N_THREADS, Integer.MAX_VALUE,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        for (int i = 0; i < N_THREADS; ++i) {
            tpe.execute(new RequestCreator(i, address, port, args[2]));
        }
    }
}
