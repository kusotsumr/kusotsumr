package musicbandlab.client.consoleui;

import musicbandlab.common.contracts.Request;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerGateway {
    private static final int MAX_RETRIES = 5;
    public static final int TIMEOUT_MILLISECONDS = 1000;

    private final Config config;
    private DatagramChannel channel;

    public ServerGateway(Config config) {
        this.config = config;
    }

    public <TRequest extends Request<TResponse>, TResponse extends Serializable>
    TResponse get(TRequest request) throws Exception {

        InetSocketAddress server =
                new InetSocketAddress(config.getHost(), config.getPort());

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos)) {
            oos.writeObject(request);
        }
        byte[] requestBytes = baos.toByteArray();

        Exception lastException = null;

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            if (channel == null) {
                channel = openChannel();
            }

            try {
                channel.send(
                        ByteBuffer.wrap(requestBytes),
                        server
                );

                ByteBuffer buffer = ByteBuffer.allocate(65535);

                long start = System.currentTimeMillis();

                while (System.currentTimeMillis() - start < TIMEOUT_MILLISECONDS) {

                    buffer.clear();

                    InetSocketAddress addr =
                            (InetSocketAddress) channel.receive(buffer);

                    if (addr == null) continue;

                    buffer.flip();

                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);

                    Object responseObj;
                    try (java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(data);
                         java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais)) {
                        responseObj = ois.readObject();
                    }

                    if (responseObj instanceof Exception serverException) {
                        throw serverException;
                    }

                    return (TResponse) responseObj;
                }

                throw new UdpNetworkException("Timeout waiting for UDP response");

            } catch (UdpNetworkException e) {
                lastException = e;
                reconnect();
            } catch (IOException e) {
                lastException = new UdpNetworkException("UDP transport failure", e);
                reconnect();
            }
        }

        throw lastException;
    }

    private void reconnect() throws IOException {
        try { channel.close(); } catch (IOException ignored) {}
        channel = openChannel();
    }

    private static DatagramChannel openChannel() throws IOException {
        DatagramChannel ch = DatagramChannel.open();
        ch.configureBlocking(false);
        return ch;
    }
}