package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.commands.insertmusicband.InsertMusicBandCommand;
import musicbandlab.core.application.usecases.commands.insertmusicband.InsertMusicBandCommandHandler;
import musicbandlab.core.domain.MusicBand;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Добавляет новый объект MusicBand с заданным ключом. Данные элемента передаются после ключа.
 */
@CommandAnnotation(
        help = "insert key {element}: добавить новый элемент с заданным ключом",
        keyword = "insert"
)
public class InsertCommand extends AbstractConsoleCommand{
    public InsertCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 2);
    }

    @Override
    public void execute() {
        InsertMusicBandCommandHandler handler = serviceLocator.getInsertMusicBandCommandHandler();
        String key = parts[1];
        MusicBand musicBand = Parse();

        handler.handle(new InsertMusicBandCommand(key, musicBand));
    }
}