package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.commands.updatemusicband.UpdateMusicBandCommand;
import musicbandlab.core.application.usecases.commands.updatemusicband.UpdateMusicBandCommandHandler;
import musicbandlab.core.domain.MusicBand;

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
        super(serviceLocator, scanner, systemMessagesStream, parts, 6);
    }

    @Override
    public void execute() {
        int id = Integer.parseInt(parts[1]);
        MusicBand musicBand = Parse(2);

        UpdateMusicBandCommandHandler handler = serviceLocator.getUpdateMusicBandCommandHandler();
        handler.handle(new UpdateMusicBandCommand(id, musicBand));
    }
}