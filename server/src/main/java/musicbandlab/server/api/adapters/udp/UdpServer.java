package musicbandlab.server.api.adapters.udp;

import musicbandlab.server.api.Config;
import musicbandlab.server.api.ControlCommand;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UdpServer {

    private final Config config;
    private final BlockingQueue<ControlCommand> commands;
    private final RequestReader requestReader;
    private final ExecutorService readPool = Executors.newCachedThreadPool();
    private volatile boolean isRunning = true;

    public UdpServer(BlockingQueue<ControlCommand> commands,
                     Config config,
                     RequestReader requestReader) {
        this.commands = commands;
        this.config = config;
        this.requestReader = requestReader;
    }

    public void run() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(65535);

        try (DatagramChannel channel = DatagramChannel.open()) {

            channel.bind(new InetSocketAddress(config.getPort()));
            channel.configureBlocking(false);

            System.out.println("Run. Port " + config.getPort());

            while (isRunning) {
                try {
                    ControlCommand controlCommand = commands.poll();

                    if (controlCommand == ControlCommand.EXIT) {
                        isRunning = false;
                        break;
                    }

                    buffer.clear();

                    InetSocketAddress client = (InetSocketAddress) channel.receive(buffer);

                    if (client == null) continue;

                    buffer.flip();

                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);

                    readPool.submit(() -> requestReader.read(channel, data, client));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            readPool.shutdown();
        }
    }
}