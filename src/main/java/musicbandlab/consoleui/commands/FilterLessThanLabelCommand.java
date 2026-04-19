package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.queries.getmusicbandswherelabellessthan.GetMusicBandsWhereLabelLessThanQuery;
import musicbandlab.core.application.usecases.queries.getmusicbandswherelabellessthan.GetMusicBandsWhereLabelLessThanQueryHandler;
import musicbandlab.core.domain.Label;
import musicbandlab.core.domain.MusicBand;

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
        GetMusicBandsWhereLabelLessThanQueryHandler handler = serviceLocator.getGetMusicBandsWhereLabelLessThanQueryHandler();
        ArrayList<MusicBand> musicBands =  handler.handle(new GetMusicBandsWhereLabelLessThanQuery(label));

        if(musicBands.isEmpty()) {
            System.out.println("Элементов нет");
            return;
        }

        System.out.println("Элементы:");
        for(MusicBand musicBand : musicBands) {
            System.out.println(musicBand);
        }
    }
}