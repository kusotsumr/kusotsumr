package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.common.contracts.commands.replaceiflessthanmusicband.ReplaceIfLessThanMusicBandCommand;
import musicbandlab.common.domain.MusicBand;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Команда для замены элемента в коллекции.
 * Заменяет значение элемента по заданному ключу, если новое значение меньше старого.
 */
@CommandAnnotation(
        help = "replace_if_lower key {element}: заменить значение по ключу, если новое значение меньше старого",
        keyword = "replace_if_lower"
)
public class ReplaceIfLowerCommand extends AbstractConsoleCommand{
    public ReplaceIfLowerCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 2);
    }

    @Override
    public void execute() throws Exception {
        String key = parts[1];
        MusicBand musicBand = Parse();

        gateway.get(new ReplaceIfLessThanMusicBandCommand(key, musicBand));
    }
}