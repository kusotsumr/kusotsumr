package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.common.contracts.commands.clearmusicbands.ClearMusicBandsCommand;

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
    public void execute() throws Exception {
        gateway.get(new ClearMusicBandsCommand());
    }
}