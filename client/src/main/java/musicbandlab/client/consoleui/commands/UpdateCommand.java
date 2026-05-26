package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.UdpNetworkException;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.common.contracts.commands.updatemusicband.UpdateMusicBandCommand;
import musicbandlab.common.domain.MusicBand;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Команда для обновления элемента в коллекции.
 * Обновляет значение музыкальной группы, id которой равен заданному.
 * Ошибка, если группа с таким id не содержится в коллекции.
 */
@CommandAnnotation(
        help = "update id {element}: обновить значение элемента коллекции, id которого равен заданному",
        keyword = "update"
)
public class UpdateCommand extends AbstractConsoleCommand{
    public UpdateCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 2);
    }

    @Override
    public void execute() throws Exception {
        int id = Integer.parseInt(parts[1]);
        MusicBand musicBand = Parse();

        gateway.get(new UpdateMusicBandCommand(id, musicBand));
    }
}