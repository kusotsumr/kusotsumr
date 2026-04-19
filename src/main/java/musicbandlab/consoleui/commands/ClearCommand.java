package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.commands.clearmusicbands.ClearMusicBandsCommandHandler;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Команда clear. Очищает всю коллекцию музыкальных групп.
 */
@CommandAnnotation(
        help = "clear: очистить коллекцию",
        keyword = "clear"
)
public class ClearCommand extends AbstractConsoleCommand {
    public ClearCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 1);
    }

    @Override
    public void execute() {
        ClearMusicBandsCommandHandler handler = serviceLocator.getClearMusicBandsCommandHandler();
        handler.handle();
    }
}