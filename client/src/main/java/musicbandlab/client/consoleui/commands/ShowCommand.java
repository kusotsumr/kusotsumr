package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.common.contracts.queries.getallmusicbands.GetAllMusicBandsQuery;
import musicbandlab.common.contracts.queries.getallmusicbands.GetAllMusicBandsQueryResponse;
import musicbandlab.common.domain.MusicBand;

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
    public void execute() throws Exception {
        int page = 1;

        while (true) {
            GetAllMusicBandsQueryResponse response = gateway.get(
                    new GetAllMusicBandsQuery(page, 2));

            ArrayList<Map.Entry<String, MusicBand>> musicBands = response.getMusicBands();

            if (musicBands.isEmpty()) {
                if (page == 1) {
                    System.out.println("Коллекция пуста");
                }
                break;
            }

            if (page == 1) {
                System.out.println("Элементы коллекции:");
            }
            for(Map.Entry<String, MusicBand> musicBand : musicBands) {
                System.out.println("Ключ: " + musicBand.getKey());
                System.out.println(musicBand.getValue());
                System.out.println();
                System.out.println();
            }
            page++;
        }
    }
}