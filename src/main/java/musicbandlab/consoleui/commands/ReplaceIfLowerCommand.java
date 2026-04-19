package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.commands.replaceiflessthanmusicband.ReplaceIfLessThanMusicBandCommand;
import musicbandlab.core.application.usecases.commands.replaceiflessthanmusicband.ReplaceIfLessThanMusicBandCommandHandler;
import musicbandlab.core.domain.MusicBand;

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
        super(serviceLocator, scanner, systemMessagesStream, parts, 6);
    }

    @Override
    public void execute() throws Exception {
        String key = parts[1];
        MusicBand musicBand = Parse(2);

        ReplaceIfLessThanMusicBandCommandHandler handler = serviceLocator.getReplaceIfLessThanMusicBandCommandHandler();
        handler.handle(new ReplaceIfLessThanMusicBandCommand(key, musicBand));
    }
}