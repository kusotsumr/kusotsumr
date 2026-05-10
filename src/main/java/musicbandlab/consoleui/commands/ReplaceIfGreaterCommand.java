package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.commands.replaceifgreaterthanmusicband.ReplaceIfGreaterThanMusicBandCommand;
import musicbandlab.core.application.usecases.commands.replaceifgreaterthanmusicband.ReplaceIfGreaterThanMusicBandCommandHandler;
import musicbandlab.core.domain.MusicBand;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Команда для замены элемента в коллекции.
 * Заменяет значение элемента по заданному ключу, если новое значение больше старого.
 */
@CommandAnnotation(
        help = "replace_if_greater key {element}: заменить значение по ключу, если новое значение больше старого",
        keyword = "replace_if_greater"
)
public class ReplaceIfGreaterCommand extends AbstractConsoleCommand {
    public ReplaceIfGreaterCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 2);
    }

    @Override
    public void execute() throws Exception {
        String key = parts[1];
        MusicBand musicBand = Parse();

        ReplaceIfGreaterThanMusicBandCommandHandler handler = serviceLocator.getReplaceIfGreaterThanMusicBandCommandHandler();
        handler.handle(new ReplaceIfGreaterThanMusicBandCommand(key, musicBand));
    }
}