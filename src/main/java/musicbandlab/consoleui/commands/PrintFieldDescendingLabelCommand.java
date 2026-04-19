package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.queries.getmusicbandslabelsdescending.GetMusicBandsLabelsDescendingQueryHandler;
import musicbandlab.core.domain.Label;

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
        GetMusicBandsLabelsDescendingQueryHandler handler = serviceLocator.getGetMusicBandsLabelsDescendingQueryHandler();
        ArrayList<Label> labels = handler.handle();

        if(labels.isEmpty()) {
            System.out.println("Элементов нет");
            return;
        }

        System.out.println("Лейблы:");
        for(Label label : labels) {
            System.out.println(label);
        }
    }
}