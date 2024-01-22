package ir.xenoncommunity.jss.utils;

import lombok.experimental.UtilityClass;

import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketOption;

@UtilityClass
public class SocketUtils {
    @SafeVarargs
    public void enableSocketOpts(Socket socket, SocketOption<Boolean>... options) {
        for (SocketOption<Boolean> option : options) {
            try {
                socket.setOption(option, true);
            } catch (Exception ignored) {
            }
        }
    }

    public void setSoTimeout(Socket socket, int i) {
        try {
            socket.setSoTimeout(i);
        } catch (Exception ignored) {
        }
    }

    public void setSoTimeout(DatagramSocket socket, int i) {
        try {
            socket.setSoTimeout(i);
        } catch (Exception ignored) {
        }
    }
}
