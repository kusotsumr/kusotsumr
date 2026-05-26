package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.common.contracts.queries.getmusicbandsinfo.GetMusicBandsInfoQuery;
import musicbandlab.common.contracts.queries.getmusicbandsinfo.GetMusicBandsInfoQueryResponse;

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
    public void execute() throws Exception {
        GetMusicBandsInfoQueryResponse response = gateway.get(new GetMusicBandsInfoQuery());
        System.out.println(response);
    }
}