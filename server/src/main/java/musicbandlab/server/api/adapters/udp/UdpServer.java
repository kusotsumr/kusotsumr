package musicbandlab.server.api.adapters.udp;

import musicbandlab.server.api.Config;
import musicbandlab.server.api.ControlCommand;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;

public class UdpServer {

    private final Config config;
    private final MusicBandRepository musicBandRepository;
    private final BlockingQueue<ControlCommand> commands;
    private final RequestReader requestReader;
    private boolean isRunning = true;

    public UdpServer(BlockingQueue<ControlCommand> commands,
                     Config config,
                     MusicBandRepository musicBandRepository,
                     RequestReader requestReader) {
        this.commands = commands;
        this.config = config;
        this.musicBandRepository = musicBandRepository;
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
                    while (true) {
                        ControlCommand controlCommand = commands.poll();

                        if (controlCommand == null) {
                            break;
                        }

                        switch (controlCommand) {
                            case SAVE -> save();
                            case EXIT -> {
                                save();
                                isRunning = false;
                                return;
                            }
                        }
                    }

                    buffer.clear();

                    InetSocketAddress client = (InetSocketAddress) channel.receive(buffer);

                    if (client == null) continue;

                    buffer.flip();

                    requestReader.read(channel, buffer, client);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void save() throws IOException {
        String json = musicBandRepository.serializeToJson();

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(config.getFileName());
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();

        }
        finally {
            if(fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }
}