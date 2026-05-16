package musicbandlab.consoleui;

import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Управляет запуском интерактивного режима и режима выполнения скриптов из файла.
 * Обрабатывает ввод команд.
 */
public class MusicBandApplication {
    private final CommandExecutor commandExecutor;
    private final PrintStream nullOut = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) {
        }
    });

    public MusicBandApplication(String fileName) {
        ServiceLocator serviceLocator = new ServiceLocator(fileName, this);
        commandExecutor = new CommandExecutor(serviceLocator);
    }

    public void run() {
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
        Scanner scanner = new Scanner(System.in);
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

    private void run(Scanner scanner, PrintStream systemMessagesStream) {
        systemMessagesStream.println("Введите команду:");
        while (scanner.hasNextLine()) {
            try {
                String line = NextLine(scanner);
                commandExecutor.executeCommand(scanner, systemMessagesStream, line);
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