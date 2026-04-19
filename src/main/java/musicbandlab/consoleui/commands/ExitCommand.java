package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Команда для завершения программы.
 * Завершает работу приложения без сохранения данных в файл.
 */
@CommandAnnotation(
        help = "exit: завершить программу (без сохранения в файл)",
        keyword = "exit"
)
public class ExitCommand extends AbstractConsoleCommand{
    public ExitCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 1);
    }

    @Override
    public void execute() throws Exception {
        System.exit(0);
    }
}