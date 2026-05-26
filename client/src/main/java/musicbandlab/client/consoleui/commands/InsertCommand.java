package musicbandlab.client.consoleui.commands;

import musicbandlab.client.consoleui.ServiceLocator;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.common.contracts.commands.insertmusicband.InsertMusicBandCommand;
import musicbandlab.common.domain.MusicBand;

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
    public void execute() throws Exception {
        String key = parts[1];
        MusicBand musicBand = Parse();

        gateway.get(new InsertMusicBandCommand(key, musicBand));
    }
}