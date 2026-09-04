package musicbandlab.client.consoleui;

import musicbandlab.common.contracts.commands.register.RegisterCommand;
import musicbandlab.common.contracts.queries.getallmusicbands.GetAllMusicBandsQuery;

import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Управляет запуском интерактивного режима и режима выполнения скриптов из файла.
 * Обрабатывает ввод команд. Перед основным циклом требует обязательный вход
 * (login/register) — без него сервер всё равно отклонит любую команду.
 */
public class MusicBandApplication {
    private final CommandExecutor commandExecutor;
    private final ServiceLocator serviceLocator;
    private final PrintStream nullOut = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) {
        }
    });

    public MusicBandApplication(Config config) {
        this.serviceLocator = new ServiceLocator(config, this);
        this.commandExecutor = new CommandExecutor(serviceLocator);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        if (!authenticate(scanner)) {
            return;
        }
        System.out.println("При вводе внутри строки null вводится как 'null'");
        System.out.println("При вводе с новой строки null вводится в виде пустой строки");
        System.out.println("Каждое значение из {element} вводится с новой строки");
        System.out.println("id = null нужен для поисковых запросов. При insert он генерируется автоматически");
        System.out.println("Пример:");
        System.out.println("insert my_key");
        System.out.println("null");
        System.out.println("my_name");
        System.out.println("5");
        System.out.println("6");
        System.out.println("40");
        System.out.println("45");
        System.out.println("blues");
        System.out.println("5");
        System.out.println();
        System.out.println("Для получения справки введите 'help'");

        run(scanner, System.out);
    }

    public void run(String fileName) throws Exception {
        Scanner scanner = new Scanner(new FileReader(fileName));
        try {
            run(scanner, nullOut);
        } finally {
            scanner.close();
        }
    }

    private boolean authenticate(Scanner scanner) {
        ServerGateway gateway = serviceLocator.getServerGateway();

        while (true) {
            System.out.println("Введите 'login', если уже зарегистрированы, или 'register' для регистрации:");
            String action = scanner.nextLine().trim();

            if (!action.equalsIgnoreCase("login") && !action.equalsIgnoreCase("register")) {
                System.out.println("Неизвестная команда, введите 'login' или 'register'");
                continue;
            }

            System.out.println("Логин:");
            String login = scanner.nextLine().trim();
            System.out.println("Пароль:");
            String password = scanner.nextLine().trim();

            gateway.setCredentials(login, password);

            try {
                if (action.equalsIgnoreCase("register")) {
                    gateway.get(new RegisterCommand());
                    System.out.println("Регистрация прошла успешно, выполнен вход как " + login);
                } else {
                    // Отдельной команды "войти" на сервере нет — сервер проверяет
                    // логин/пароль при КАЖДОМ запросе. Здесь просто отправляем
                    // лёгкий запрос на чтение, чтобы сразу сказать пользователю,
                    // правильный ли пароль, а не ждать первой настоящей команды.
                    gateway.get(new GetAllMusicBandsQuery(1, 1));
                    System.out.println("Вход выполнен как " + login);
                }
                return true;
            } catch (UdpNetworkException e) {
                System.out.println("Сетевая ошибка при попытке входа. Попробуйте снова.");
            } catch (Exception e) {
                System.out.println("Не удалось войти: " + e.getMessage());
            }
        }
    }
    private void run(Scanner scanner, PrintStream systemMessagesStream) {
        systemMessagesStream.println("Введите команду:");
        while (scanner.hasNextLine()) {
            try {
                String line = NextLine(scanner);
                commandExecutor.executeCommand(scanner, systemMessagesStream, line);
            }
            catch (UdpNetworkException ex) {
                System.out.println("Сетевая ошибка.");
            }
            catch (Exception ex) {
                System.out.println("Ошибка!");
            }

            systemMessagesStream.println();
            systemMessagesStream.println("Введите команду:");
        }
    }

    private static String NextLine(Scanner scanner) {
        return scanner.nextLine().trim();
    }
}