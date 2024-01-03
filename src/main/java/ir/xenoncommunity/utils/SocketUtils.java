package ir.xenoncommunity.utils;

import ir.xenoncommunity.Main;
import lombok.SneakyThrows;
import lombok.val;
import java.net.Socket;

public class SocketUtils {
    @SneakyThrows
    public void connect(final String ipIn, final int port, final boolean sendResult, final boolean keepAlive){
        try(val socket = new Socket(ipIn, port)){
            if (sendResult && socket.isConnected()) {
                System.out.printf("Connected! ip: %s, port: %s, threads: %s\n", ipIn, port, Main.runner.getTaskManager().tasks.size());
            }
            if (keepAlive) {
                socket.setKeepAlive(true);
            } else{
                socket.close();
            }
        }
    }
}
