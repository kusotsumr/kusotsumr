package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.queries.getmusicbandsinfo.GetMusicBandsInfoQueryHandler;
import musicbandlab.core.application.usecases.queries.getmusicbandsinfo.GetMusicBandsInfoQueryResponse;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Команда для вывода информации о коллекции.
 * Выводит в стандартный поток тип коллекции, дату ее инициализации и количество элементов.
 */
@CommandAnnotation(
        help = "info: вывести в стандартный поток вывода тип, дату инициализации и количество элементов в коллекции",
        keyword = "info"
)
public class InfoCommand extends  AbstractConsoleCommand{
    public InfoCommand(ServiceLocator serviceLocator, Scanner scanner,  PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 1);
    }

    @Override
    public void execute() {
        GetMusicBandsInfoQueryHandler handler = serviceLocator.getGetMusicBandsInfoQueryHandler();
        GetMusicBandsInfoQueryResponse response = handler.handle();
        System.out.println(response);
    }
}