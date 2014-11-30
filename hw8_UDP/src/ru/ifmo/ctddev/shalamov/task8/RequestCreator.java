package ru.ifmo.ctddev.shalamov.task8;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

/**
 * Created by viacheslav on 22.04.14.
 */
public class RequestCreator implements Runnable {

    private int id;
    private int num;
    private String prefix;
    private InetAddress address;
    private int port;

    /**
     * the limit of requests to be generated.
     */
    private static final int LIMIT = 50;

    /**
     * the maximum size of datagram corresponding to protocol
     */
    private static final int MAXN = 65536;


    /**
     * Creates a RequestCreator whith specified parameters.
     *
     * @param id      id creator
     * @param address address of the computer with HelloUDPServer
     * @param port    port which is listened for the server
     * @param prefix  request prefix
     */
    public RequestCreator(int id, InetAddress address, int port, String prefix) {
        this.id = id;
        this.num = -1;
        this.address = address;
        this.port = port;
        this.prefix = prefix;
    }

    /**
     * creates LIMIT requests and terminates.
     */
    @Override
    public void run() {
        DatagramSocket clientDatagramSocket = null;
        while (true) {
            try {
                ++num;
                clientDatagramSocket = new DatagramSocket();

                String fromServer;
                String fromUser;

                fromUser = prefix + "_" + id + "_" + num;
                System.out.println("Client: " + fromUser);
                byte[] bytes = fromUser.getBytes();

                DatagramPacket out = new DatagramPacket(bytes, bytes.length, address, port); // sending
                DatagramPacket in = new DatagramPacket(new byte[MAXN], MAXN); // reseiving

                clientDatagramSocket.send(out);
                clientDatagramSocket.receive(in);
                fromServer = new String(in.getData(), in.getOffset(), in.getLength());

                if (fromServer.equals("")) {
                    System.out.println("Response receiving FAILED: null or \"\" received");
                    continue;
                }
                System.out.println("Server: " + fromServer);

            } catch (UnknownHostException e) {
                System.out.println("Don't know about host ");
                break;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                break;
            }

            if (num == LIMIT) {
                break;
            }
        }

        try {
            clientDatagramSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
