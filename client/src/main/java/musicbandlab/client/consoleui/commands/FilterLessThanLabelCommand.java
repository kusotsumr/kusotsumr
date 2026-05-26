package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.common.contracts.queries.getmusicbandswherelabellessthan.GetMusicBandsWhereLabelLessThanQuery;
import musicbandlab.common.contracts.queries.getmusicbandswherelabellessthan.GetMusicBandsWhereLabelLessThanQueryResponse;
import musicbandlab.common.domain.Label;
import musicbandlab.common.domain.MusicBand;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда для фильтрации элементов коллекции.
 * Выводит все музыкальные группы, у которых значение поля label меньше заданного.
 * Аргументом является bands у label (или "null", если bands должен быть "null")
 */
@CommandAnnotation(
        help = "filter_less_than_label bands/null: вывести элементы, значение поля label которых меньше заданного",
        keyword = "filter_less_than_label"
)
public class FilterLessThanLabelCommand extends  AbstractConsoleCommand {
    public FilterLessThanLabelCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 2);
    }

    @Override
    public void execute() throws Exception {
        Label label = new Label(!parts[1].equals("null")
                    ? Integer.parseInt(parts[1])
                    : null);

        int page = 1;

        while (true) {
            GetMusicBandsWhereLabelLessThanQueryResponse response = gateway.get(
                    new GetMusicBandsWhereLabelLessThanQuery(label, page, 2));

            ArrayList<MusicBand> musicBands = response.getMusicBands();
            if (musicBands.isEmpty()) {
                if (page == 1) {
                    System.out.println("Элементов нет");
                }
                break;
            }

            if (page == 1) {
                System.out.println("Элементы:");
            }
            for (MusicBand musicBand : musicBands) {
                System.out.println(musicBand);
            }
            page++;
        }
    }
}