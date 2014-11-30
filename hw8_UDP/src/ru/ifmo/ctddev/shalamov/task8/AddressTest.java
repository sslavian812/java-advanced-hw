package ru.ifmo.ctddev.shalamov.task8;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by viacheslav on 22.04.14.
 */
public class AddressTest {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress ina = Inet4Address.getLocalHost();
        System.out.println(ina.getCanonicalHostName());
        System.out.println(ina.getHostAddress());
        System.out.println("asdasd");
    }
}
