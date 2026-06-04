package musicbandlab.server.api.adapters.udp;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestReader {
    private final RequestInvoker requestInvoker;
    private final RequestSender requestSender;

    public RequestReader(RequestInvoker requestInvoker, RequestSender requestSender) {
        this.requestInvoker = requestInvoker;
        this.requestSender = requestSender;
    }

    public void read(DatagramChannel channel,
                     ByteBuffer buffer,
                     InetSocketAddress client) {
        try {
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);

            Object request;
            try (java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(data);
                 java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais)) {
                request = ois.readObject();
            }

            Object result = requestInvoker.handle(request);
            if (result == null) return;

            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos)) {
                oos.writeObject(result);
            }

            requestSender.send(channel, client, baos.toByteArray());

        } catch (Exception e) {
            try {
                musicbandlab.common.exceptions.RemoteServerException remoteEx =
                        new musicbandlab.common.exceptions.RemoteServerException(e.getMessage());

                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos)) {
                    oos.writeObject(remoteEx);
                }

                requestSender.send(channel, client, baos.toByteArray());
            } catch (Exception serializationException) {
                serializationException.printStackTrace();
            }
        }
    }
}