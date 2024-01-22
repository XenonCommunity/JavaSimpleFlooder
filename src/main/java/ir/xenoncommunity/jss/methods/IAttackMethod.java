package ir.xenoncommunity.jss.methods;

import java.net.InetAddress;

public interface IAttackMethod {
    void send(InetAddress addr, int port) throws Exception;
}
