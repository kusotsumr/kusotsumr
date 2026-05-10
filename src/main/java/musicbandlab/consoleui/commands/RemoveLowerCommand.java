package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.commands.removewherelessmusicbands.RemoveWhereLessMusicBandsCommand;
import musicbandlab.core.application.usecases.commands.removewherelessmusicbands.RemoveWhereLessMusicBandsCommandHandler;
import musicbandlab.core.domain.MusicBand;

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
        RemoveWhereLessMusicBandsCommandHandler handler = serviceLocator.getRemoveWhereLessMusicBandsCommandHandler();
        handler.handle(new RemoveWhereLessMusicBandsCommand(musicBand));
    }
}