package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.commands.removemusicband.RemoveMusicBandCommand;
import musicbandlab.core.application.usecases.commands.removemusicband.RemoveMusicBandCommandHandler;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Команда для удаления элемента из коллекции.
 * Удаляет музыкальную группу по заданному ключу.
 */
@CommandAnnotation(
        help = "remove_key key: удалить элемент из коллекции по его ключу",
        keyword = "remove_key"
)
public class RemoveKeyCommand extends AbstractConsoleCommand{
    public RemoveKeyCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 2);
    }

    @Override
    public void execute() {
        String key = parts[1];
        RemoveMusicBandCommandHandler handler = serviceLocator.getRemoveMusicBandCommandHandler();
        handler.handle(new RemoveMusicBandCommand(key));
    }
}