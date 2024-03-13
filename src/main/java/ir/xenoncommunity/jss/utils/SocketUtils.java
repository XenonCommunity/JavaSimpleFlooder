package ir.xenoncommunity.jss.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketOption;

@UtilityClass
public class SocketUtils {
    @SafeVarargs
    public void enableSocketOpts(final Socket socket, final SocketOption<Boolean>... options) {
        for (val option : options) {
            try {
                socket.setOption(option, true);
            } catch (final Exception ignored) {}
        }
    }

    @SneakyThrows
    public void setSoTimeout(final Socket socket, final int i) {
        socket.setSoTimeout(i);
    }

    @SneakyThrows
    public void setSoTimeout(final DatagramSocket socket, final int i) {
        socket.setSoTimeout(i);
    }
}
