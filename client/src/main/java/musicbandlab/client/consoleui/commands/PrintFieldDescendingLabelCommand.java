package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.common.contracts.queries.getmusicbandslabelsdescending.GetMusicBandsLabelsDescendingQuery;
import musicbandlab.common.contracts.queries.getmusicbandslabelsdescending.GetMusicBandsLabelsDescendingQueryResponse;
import musicbandlab.common.domain.Label;
import musicbandlab.common.domain.MusicBand;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда для вывода значений поля label в порядке убывания.
 * Получает все лейблы музыкальных групп и выводит их в порядке убывания.
 * Исключает лейблы с bands = null.
 */
@CommandAnnotation(
    help = "print_field_descending_label: вывести значения поля label всех элементов в порядке убывания",
    keyword = "print_field_descending_label"
)
public class PrintFieldDescendingLabelCommand extends AbstractConsoleCommand{
    public PrintFieldDescendingLabelCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 1);
    }

    @Override
    public void execute() throws Exception {
        int page = 1;

            while (true) {
                GetMusicBandsLabelsDescendingQueryResponse response = gateway.get(
                        new GetMusicBandsLabelsDescendingQuery(page, 2));

                ArrayList<Label> labels = response.getLabels();

                if (labels.isEmpty()) {
                    if (page == 1) {
                        System.out.println("Элементов нет");
                    }
                    break;
                }

                if (page == 1) {
                    System.out.println("Лейблы:");
                }
                for(Label label : labels) {
                    System.out.println(label);
                }
                page++;
        }
    }
}