package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.queries.getsumnumberofparticipants.GetSumNumberOfParticipantsQueryHandler;

import java.io.PrintStream;
import java.util.Scanner;

@CommandAnnotation(
        help = "median: выводит медианное значение количества участников в группе",
        keyword = "median"
)
public class MedianCommand extends AbstractConsoleCommand {
    public MedianCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 1);
    }
    
    @Override
    public void execute() throws Exception {
        GetSumNumberOfParticipantsQueryHandler handler = serviceLocator.getGetSumNumberOfParticipantsHandler();
        long result = handler.handle();
        System.out.print(result);
    }
}