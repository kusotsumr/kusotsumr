package musicbandlab.server.api.adapters.udp;

import com.fasterxml.jackson.databind.ObjectMapper;
import musicbandlab.common.contracts.packets.PacketRequest;
import musicbandlab.common.contracts.packets.PacketResponse;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class RequestReader {
    private final ObjectMapper mapper;
    private final RequestInvoker requestInvoker;
    private final RequestSender requestSender;

    public RequestReader(ObjectMapper mapper, RequestInvoker requestInvoker, RequestSender requestSender) {
        this.mapper = mapper;
        this.requestInvoker = requestInvoker;
        this.requestSender = requestSender;
    }

    public void read(DatagramChannel channel,
                     ByteBuffer buffer,
                     InetSocketAddress client) {
        try {

            String json = StandardCharsets.UTF_8.decode(buffer).toString();

            PacketRequest request =
                    mapper.readValue(json, PacketRequest.class);

            Class<?> requestType =
                    Class.forName(request.requestType());

            Object requestObj =
                    mapper.readValue(request.payload(), requestType);

            Object result = requestInvoker.handle(requestType, requestObj);
            if (result == null) return;

            PacketResponse response =
                    new PacketResponse(
                            result.getClass().getName(),
                            true,
                            result != null ? mapper.writeValueAsString(result) : null,
                            null
                    );

            requestSender.send(channel, client, response);

        } catch (Exception e) {
            requestSender.sendError(channel, client, e.getMessage());
        }
    }
}