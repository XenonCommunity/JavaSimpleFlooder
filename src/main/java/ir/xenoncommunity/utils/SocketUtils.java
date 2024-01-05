package ir.xenoncommunity.utils;

import ir.xenoncommunity.Main;
import lombok.val;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class SocketUtils {
    public void connect(final String ipAddress, final int port, final boolean sendResult, final boolean keepAlive, final boolean udp) {
        Main.runner.getLogger().setSection("CONNECT");
        try {
            if (udp) {
                if (keepAlive) {
                    Main.runner.getLogger().print(Logger.LEVEL.ERROR, "Can't use keepAlive for UDP protocol.");
                    throw new IllegalStateException("UDP is used with keepAlive.");
                }
                try (val socket = new DatagramSocket(port, InetAddress.getByName(ipAddress))) {
                    if (sendResult && socket.isConnected()) {
                        Main.runner.getLogger().print(Logger.LEVEL.INFO, String.format("Connected! ip: %s, port: %s, threads: %s\n", ipAddress, port, Main.runner.getTaskManager().tasks.size()));
                    }
                }
            } else {
                try (val socket = new Socket(ipAddress, port)) {
                    if (sendResult && socket.isConnected()) {
                        Main.runner.getLogger().print(Logger.LEVEL.INFO, String.format("Connected! ip: %s, port: %s, threads: %s\n", ipAddress, port, Main.runner.getTaskManager().tasks.size()));
                    }
                    if (keepAlive) {
                        socket.setKeepAlive(true);
                    } else
                        socket.close();
                }
            }
        } catch (final Exception e) {
            Main.runner.getLogger().print(Logger.LEVEL.ERROR, e.getMessage());
        }
    }
}
