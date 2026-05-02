package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.queries.getallmusicbands.GetAllMusicBandsQueryHandler;
import musicbandlab.core.application.usecases.queries.gethashcode.GetHashCodeQueryHandler;
import musicbandlab.core.domain.MusicBand;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Команда для вывода всех элементов коллекции.
 * Выводит в стандартный поток все музыкальные группы в строковом представлении.
 */
@CommandAnnotation(
        help = "show: вывести в стандартный поток вывода все элементы в строковом представлении",
        keyword = "show"
)
public class ShowCommand extends AbstractConsoleCommand {
    public ShowCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 1);
    }

    @Override
    public void execute() {
        GetAllMusicBandsQueryHandler allMusicBandsQueryHandler = serviceLocator.getGetAllMusicBandsQueryHandler();
        GetHashCodeQueryHandler hashCodeQueryHandler = serviceLocator.getGetHashCodeQueryHandler();
        ArrayList<Map.Entry<String, MusicBand>> entries = allMusicBandsQueryHandler.handle();
        int hashCode = hashCodeQueryHandler.handle();

        if(entries.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        System.out.println("Элементы коллекции:");
        for(Map.Entry<String, MusicBand> entry : entries) {
            System.out.println("Ключ: " + entry.getKey());
            System.out.println(entry.getValue());
            System.out.println();
            System.out.println();
        }
        System.out.println("Хешкод хештейбла:\t" + hashCode);
    }
}