package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ExecuteScriptHelper;
import musicbandlab.client.consoleui.MusicBandApplication;
import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;

import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Команда для выполнения скрипта из файла.
 * Считывает команды из указанного файла и исполняет их в том же формате,
 * в котором пользователь вводит команды в интерактивном режиме.
 * Используется для автоматизации последовательности команд.
 */
@CommandAnnotation(
    help = "execute_script file_name: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме",
    keyword = "execute_script"
)
public class ExecuteScriptCommand extends AbstractConsoleCommand{
    public ExecuteScriptCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 2);
    }

    @Override
    public void execute() throws Exception {
        // Конвертируем в абсолютный путь, чтобы моментально приостановить
        // скрытую рекурсию, т.е. когда одинаковый файл
        // вызывается под разными именами (относительный / абсолютный)
        String fileName = Paths.get(parts[1]).toAbsolutePath().toString();

        MusicBandApplication musicBandApplication = serviceLocator.getMusicBandApplication();
        ExecuteScriptHelper executeScriptHelper = serviceLocator.getExecuteScriptHelper();

        if(!executeScriptHelper.TryEnter(fileName)) {
            System.out.println("Файл " + fileName + " не может быть вызван рекурсивно, скрипт не будет исполнен.");
            return;
        }

        try {
            musicBandApplication.run(fileName);
        } finally {
            executeScriptHelper.Exit(fileName);
        }
    }
}