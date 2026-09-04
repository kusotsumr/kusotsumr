package musicbandlab.server.api.adapters.udp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestSender {

    public void sendResult(DatagramChannel channel, InetSocketAddress client, Object result) throws Exception {
        byte[] data = serialize(result);
        channel.send(ByteBuffer.wrap(data), client);
    }

    private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }
        return baos.toByteArray();
    }
}