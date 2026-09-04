package musicbandlab.server.api;

import musicbandlab.server.api.adapters.udp.RequestInvoker;
import musicbandlab.server.api.adapters.udp.RequestReader;
import musicbandlab.server.api.adapters.udp.RequestSender;
import musicbandlab.server.api.adapters.udp.UdpServer;
import musicbandlab.server.core.application.usecases.commands.*;
import musicbandlab.server.core.application.usecases.queries.*;
import musicbandlab.server.core.ports.MusicBandRepository;
import musicbandlab.server.core.ports.UserRepository;
import musicbandlab.server.infrastructure.database.ConnectionManager;
import musicbandlab.server.infrastructure.database.MusicBandRepositoryImpl;
import musicbandlab.server.infrastructure.database.UserRepositoryImpl;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        Config config = getConfig(args);
        if (config == null) {
            return;
        }

        ConnectionManager connectionManager = new ConnectionManager(config);
        UserRepository userRepository = new UserRepositoryImpl(connectionManager);

        MusicBandRepository repository;
        try {
            repository = MusicBandRepositoryImpl.loadFromDatabase(connectionManager);
        } catch (Exception e) {
            System.out.println("Не удалось загрузить коллекцию из БД: " + e.getMessage());
            return;
        }

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
        RequestReader requestReader = new RequestReader(requestHandler, requestSender, userRepository);
        UdpServer server = new UdpServer(commands, config, requestReader);

        Thread serverThread = new Thread(server::run);
        serverThread.start();

        runConsoleScanner(commands);

        serverThread.join();
    }

    private static void runConsoleScanner(BlockingQueue<ControlCommand> commands) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("exit")) {
                commands.put(ControlCommand.EXIT);
                break;
            }
        }
    }

    private static Config getConfig(String[] args) {
        if (args.length != 3) {
            System.out.println("Ошибка: программа принимает порт, логин и пароль от БД");
            System.out.println("java ... порт логин пароль");
            return null;
        }

        int port;
        try {
            port = Integer.parseInt(args[0]);
            if (port < 0 || port > 65535) {
                System.out.println("Ошибка: Порт должен быть в диапазоне от 0 до 65535");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Порт должен быть числом");
            return null;
        }

        return new Config(port, args[1], args[2]);
    }
}