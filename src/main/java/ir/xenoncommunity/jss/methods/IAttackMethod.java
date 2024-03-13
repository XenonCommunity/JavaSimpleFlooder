package ir.xenoncommunity.jss.methods;

import java.net.InetAddress;

public interface IAttackMethod {
    void send(final InetAddress addr, final int port) throws Exception;
}
