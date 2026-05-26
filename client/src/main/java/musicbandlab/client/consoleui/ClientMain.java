package musicbandlab.client.consoleui;

public class ClientMain {
    public static void main(String[] args) {
        Config config = getConfig(args);
        if(config == null) {
            return;
        }

        MusicBandApplication application = new MusicBandApplication(config);
        application.run();
    }

    private static Config getConfig(String[] args) {
        if (args.length != 2) {
            System.out.println("Ошибка: программа принимает только хост и порт");
            System.out.println("java ... хост порт");
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
}
