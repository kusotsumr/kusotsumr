package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.common.contracts.queries.getcountbynumberofparticipants.GetCountByNumberOfParticipantsQuery;
import musicbandlab.common.contracts.queries.getcountbynumberofparticipants.GetCountByNumberOfParticipantsQueryResponse;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Команда count_by_number_of_participants. Выводит количество элементов коллекции, у которых значение поля numberOfParticipants равно заданному.
 */
@CommandAnnotation(
        help = "count_by_number_of_participants number_of_participants: вывести количество элементов, значение поля numberOfParticipants которых равному заданному",
        keyword = "count_by_number_of_participants"
)
public class CountByNumberOfParticipantsCommand extends AbstractConsoleCommand{
    public CountByNumberOfParticipantsCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 2);
    }

    @Override
    public void execute() throws Exception {
        long numberOfParticipants = Long.parseLong(parts[1]);
        GetCountByNumberOfParticipantsQueryResponse response =
                gateway.get(new GetCountByNumberOfParticipantsQuery(numberOfParticipants));
        int count = response.getCount();

        System.out.println("Количество элементов, значение поля numberOfParticipants которых равному заданному: " + count);
    }
}