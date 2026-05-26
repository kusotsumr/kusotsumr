package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.common.contracts.commands.removemusicband.RemoveMusicBandCommand;

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
    public void execute() throws Exception {
        String key = parts[1];
        gateway.get(new RemoveMusicBandCommand(key));
    }
}