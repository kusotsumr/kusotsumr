package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.common.contracts.commands.removewherelessmusicbands.RemoveWhereLessMusicBandsCommand;
import musicbandlab.common.domain.MusicBand;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Команда для удаления элементов из коллекции.
 * Удаляет все музыкальные группы, которые меньше заданного элемента.
 */
@CommandAnnotation(
        help = "remove_lower {element}: удалить из коллекции все элементы, меньшие, чем заданный",
        keyword = "remove_lower"
)
public class RemoveLowerCommand extends AbstractConsoleCommand{
    public RemoveLowerCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 1);
    }

    @Override
    public void execute() throws Exception {
        MusicBand musicBand = Parse();
        gateway.get(new RemoveWhereLessMusicBandsCommand(musicBand));
    }
}