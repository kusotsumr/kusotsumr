package musicbandlab.server.api.adapters.udp;

import com.fasterxml.jackson.databind.ObjectMapper;
import musicbandlab.common.contracts.packets.PacketResponse;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestSender {
    private final ObjectMapper mapper;

    public RequestSender(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void send(DatagramChannel channel,
                      InetSocketAddress client,
                      PacketResponse response) throws Exception {
        byte[] data = mapper.writeValueAsBytes(response);
        channel.send(ByteBuffer.wrap(data), client);
    }

    public void sendError(DatagramChannel channel,
                           InetSocketAddress client,
                           String error) {
        try {
            send(channel, client,
                    new PacketResponse(null,
                            false, null, error));
        } catch (Exception ignored) { }
    }
}