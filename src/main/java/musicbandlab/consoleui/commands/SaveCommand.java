package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.ports.MusicBandRepository;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Команда для сохранения коллекции в файл.
 * Сериализует текущую коллекцию музыкальных групп в JSON и сохраняет ее в файл.
 * Путь к файлу указывается с помощью аргументов командной строки.
 */
@CommandAnnotation(
        help = "save: сохранить коллекцию в файл",
        keyword = "save"
)
public class SaveCommand extends AbstractConsoleCommand{
    public SaveCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 1);
    }

    @Override
    public void execute() throws Exception {
        MusicBandRepository repository = serviceLocator.getMusicBandRepository();
        String json = repository.serializeToJson();
        String fileName = serviceLocator.getFileName();

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(fileName);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();

        }
        finally {
            if(fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }
}