package musicbandlab.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import musicbandlab.server.api.adapters.udp.RequestInvoker;
import musicbandlab.server.api.adapters.udp.RequestReader;
import musicbandlab.server.api.adapters.udp.RequestSender;
import musicbandlab.server.api.adapters.udp.UdpServer;
import musicbandlab.server.core.application.usecases.commands.*;
import musicbandlab.server.core.application.usecases.queries.*;
import musicbandlab.server.core.ports.MusicBandRepository;
import musicbandlab.server.infrastructure.hashtable.MusicBandRepositoryImpl;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        Config config = getConfig(args);
        if(config == null) {
            return;
        }

        ObjectMapper mapper = createObjectMapper();
        MusicBandRepository repository = getRepositoryFromFileOrCreate(config, mapper);
        HandlerRegistry registry = new HandlerRegistry(List.of(
                new ClearMusicBandsCommandHandler(repository),
                new InsertMusicBandCommandHandler(repository),
                new RemoveMusicBandCommandHandler(repository),
                new RemoveWhereLessMusicBandsCommandHandler(repository),
                new ReplaceIfGreaterThanMusicBandCommandHandler(repository),
                new ReplaceIfLessThanMusicBandCommandHandler(repository),
                new UpdateMusicBandCommandHandler(repository),
                new GetAllMusicBandsQueryHandler(repository),
                new GetCountByNumberOfParticipantsQueryHandler(repository),
                new GetMusicBandsInfoQueryHandler(repository),
                new GetMusicBandsLabelsDescendingQueryHandler(repository),
                new GetMusicBandsWhereLabelLessThanQueryHandler(repository)
        ));

        BlockingQueue<ControlCommand> commands = new LinkedBlockingQueue<>();
        RequestSender requestSender = new RequestSender();
        RequestInvoker requestHandler = new RequestInvoker(registry);
        RequestReader requestReader = new RequestReader(requestHandler, requestSender);
        UdpServer server = new UdpServer(commands, config, repository, requestReader);

        Thread serverThread = new Thread(server::run);
        serverThread.start();

        runConsoleScanner(commands);

        serverThread.join();
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    private static MusicBandRepository getRepositoryFromFileOrCreate(Config config, ObjectMapper objectMapper) {
        MusicBandRepository repository = new MusicBandRepositoryImpl(objectMapper);

        if(config.getFileName() != null) {
            try {
                String json = readAllText(config.getFileName());
                repository =  MusicBandRepositoryImpl.deserializeFromJson(json, objectMapper);;
            } catch (IOException e) {
                System.out.println("Error while reading file. New collection was created");;
            }
        }
        return repository;
    }

    private static void runConsoleScanner(BlockingQueue<ControlCommand> commands) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("save")) {
                commands.put(ControlCommand.SAVE);
            }

            if (line.equalsIgnoreCase("exit")) {
                commands.put(ControlCommand.EXIT);
                break;
            }
        }
    }

    private static Config getConfig(String[] args) {
        if (args.length != 2) {
            System.out.println("Ошибка: программа принимает только путь к файлу и порт");
            System.out.println("java ... путь порт");
            return null;
        }

        int port;
        try {
            port = Integer.parseInt(args[1]);
            if (port < 0 || port > 65535) {
                System.out.println("Ошибка: Порт должен быть в диапазоне от 0 до 65535");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Порт должен быть числом");
            return null;
        }

        return new Config(args[0], port);
    }

    private static String readAllText(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        StringBuilder stringBuilder = new StringBuilder();

        int symbol;
        while ((symbol = fileReader.read()) != -1) {
            stringBuilder.append((char) symbol);
        }

        fileReader.close();
        return stringBuilder.toString();
    }
}