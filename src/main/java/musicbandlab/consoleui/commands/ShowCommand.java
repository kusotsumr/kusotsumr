package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.queries.getallmusicbands.GetAllMusicBandsQueryHandler;
import musicbandlab.core.domain.MusicBand;

import java.io.PrintStream;
import java.util.ArrayList;
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
        GetAllMusicBandsQueryHandler handler = serviceLocator.getGetAllMusicBandsQueryHandler();
        ArrayList<MusicBand> musicBands = handler.handle();

        if(musicBands.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        System.out.println("Элементы коллекции:");
        for(MusicBand musicBand : musicBands) {
            System.out.println(musicBand);
        }
    }
}