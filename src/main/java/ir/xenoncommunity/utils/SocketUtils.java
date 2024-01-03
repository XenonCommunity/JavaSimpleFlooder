package ir.xenoncommunity.utils;

import ir.xenoncommunity.Main;
import ir.xenoncommunity.MainRunner;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketUtils {
   
    @SneakyThrows
    public void connect(final String ipIn, final int port, final boolean sendResult, final boolean keepAlive){
        val socket = new Socket(ipIn, port);
        if(sendResult && socket.isConnected()) System.out.println(String.format("Connected! ip: %s, port: %s, threads: %s", ipIn, port, Main.runner.getTaskManager().tasks.size()));
        if(keepAlive) {
            socket.setKeepAlive(true);
        } else{
            socket.close();
        }

    }
}
