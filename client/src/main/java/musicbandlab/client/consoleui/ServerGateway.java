package musicbandlab.client.consoleui;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import musicbandlab.common.contracts.Request;

import com.fasterxml.jackson.databind.ObjectMapper;
import musicbandlab.common.contracts.packets.PacketRequest;
import musicbandlab.common.contracts.packets.PacketResponse;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class ServerGateway {
    private final Config config;
    private final ObjectMapper mapper;

    public ServerGateway(Config config,
                         ObjectMapper mapper) {
        this.config = config;
        this.mapper = mapper;
    }

    public <TRequest extends Request<TResponse>, TResponse>
    TResponse get(TRequest request) throws Exception {

        DatagramChannel channel = null;

        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);

            InetSocketAddress server =
                    new InetSocketAddress(config.getHost(), config.getPort());

            PacketRequest packetRequest = new PacketRequest(
                    request.getClass().getName(),
                    mapper.writeValueAsString(request)
            );

            channel.send(
                    ByteBuffer.wrap(mapper.writeValueAsBytes(packetRequest)),
                    server
            );

            ByteBuffer buffer = ByteBuffer.allocate(65535);

            long start = System.currentTimeMillis();

            while (System.currentTimeMillis() - start < 3000) {

                buffer.clear();

                InetSocketAddress addr =
                        (InetSocketAddress) channel.receive(buffer);

                if (addr == null) continue;

                buffer.flip();

                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);

                PacketResponse response =
                        mapper.readValue(new String(data, StandardCharsets.UTF_8),
                                PacketResponse.class);

                if (!response.success()) {
                    throw new RuntimeException(response.error());
                }

                if (response.payload() == null) {
                    return null;
                }

                try {
                    Class<?> type = Class.forName(response.responseType());

                    return (TResponse) mapper.readValue(
                            response.payload(),
                            type
                    );

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Invalid response type", e);
                }
            }

            throw new UdpNetworkException("Timeout waiting for UDP response");

        } catch (IOException e) {
            throw new UdpNetworkException("UDP transport failure", e);

        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException ignored) {}
            }
        }
    }
}