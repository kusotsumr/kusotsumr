package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.MusicBandApplication;
import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;

import java.io.PrintStream;
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
        String fileName = parts[1];
        MusicBandApplication musicBandApplication = serviceLocator.getMusicBandApplication();
        musicBandApplication.run(fileName);
    }
}