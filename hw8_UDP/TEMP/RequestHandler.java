

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

/**
 * Created by viacheslav on 22.04.14.
 */
public class RequestHandler implements Runnable {

    private DatagramSocket socket;
    private DatagramPacket in;
    private DatagramPacket out;

    /**
     * the maximum size of datagram corresponding to protocol
     */
    private static final int MAXN = 65536;

    /**
     * Creates a RequestHandler which listens for specified socket.
     *
     * @param s specified socket
     */
    public RequestHandler(DatagramSocket s) {
        this.socket = s;
        in = new DatagramPacket(new byte[MAXN], MAXN); // receiving
    }

    /**
     * receives datagrams and sends answers back.
     */
    public void run() {
        while (true) {
            try {
                socket.receive(in);
                String inputLine, outputLine;
                byte[] bytesReceived = new byte[in.getLength()];
                System.arraycopy(in.getData(), 0, bytesReceived, 0, in.getLength());

                inputLine = new String(bytesReceived);
                System.out.println("received: " + inputLine);

                if (inputLine.equals("")) {
                    System.out.println("Client processing FAILED: null or \"\" received");
                    continue;
                }

                outputLine = "HELLO, " + inputLine;
                byte[] bytesSent = outputLine.getBytes();
                out = new DatagramPacket(bytesSent, bytesSent.length, in.getAddress(), in.getPort()); // sending
                socket.send(out);
            } catch (IOException e) {
                System.out.println("FAILED: " + e.getMessage());
                continue;
            }
        }
    }
}
