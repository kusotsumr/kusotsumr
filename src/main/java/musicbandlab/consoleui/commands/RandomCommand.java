package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;
import musicbandlab.core.application.usecases.commands.insertmusicband.InsertMusicBandCommand;
import musicbandlab.core.domain.Coordinates;
import musicbandlab.core.domain.Label;
import musicbandlab.core.domain.MusicBand;
import musicbandlab.core.domain.MusicGenre;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

@CommandAnnotation(
        help = "random key: добавить элемент с заданным ключом и случайными данными",
        keyword = "random"
)
public class RandomCommand extends AbstractConsoleCommand {
    private static final Random random = new Random();
    private static final String[] NAMES = {"The Echoes", "Neon Void", "Iron Skies", "Last Signal", "Velvet Storm"};

    public RandomCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 2);
    }

    @Override
    public void execute() {
        String key = parts[1];

        Coordinates coordinates = new Coordinates(
                random.nextInt(255),
                random.nextDouble() * 93
        );
        MusicGenre[] genres = MusicGenre.values();
        MusicGenre genre = genres[random.nextInt(genres.length)];
        Label label = new Label(random.nextInt(100) + 1);

        MusicBand musicBand = new MusicBand(
                NAMES[random.nextInt(NAMES.length)],
                coordinates,
                random.nextInt(99) + 1,
                random.nextInt(49) + 1,
                genre,
                label
        );

        serviceLocator.getInsertMusicBandCommandHandler().handle(new InsertMusicBandCommand(key, musicBand));

        System.out.println("Добавлена случайная группа по ключу: " + key);
    }
}