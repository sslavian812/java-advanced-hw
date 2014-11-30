

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by viacheslav on 22.04.14.
 */
public class HelloUDPServer {

    private static final int N_THREADS = 10;

    /**
     * Starts N_THREADS threads, each of them waits for datagram and sends response.
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage:HelloUDPServer <port number>");
            System.exit(1);
        }

        final int portNumber = Integer.parseInt(args[0]);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(N_THREADS, N_THREADS, Integer.MAX_VALUE,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        try {
            DatagramSocket serverDatagramSocket = new DatagramSocket(portNumber);

            for (int i = 0; i < N_THREADS; ++i) {
                executor.execute(new RequestHandler(serverDatagramSocket));
                System.out.println("created thread " + i + " on server");
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
