package musicbandlab.server.api.adapters.udp;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestSender {

    public RequestSender() {
    }

    public void send(DatagramChannel channel,
                     InetSocketAddress client,
                     byte[] data) throws Exception {
        channel.send(ByteBuffer.wrap(data), client);
    }
}