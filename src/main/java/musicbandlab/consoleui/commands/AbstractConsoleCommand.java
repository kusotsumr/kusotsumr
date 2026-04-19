package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.Utils;
import musicbandlab.core.domain.Coordinates;
import musicbandlab.core.domain.Label;
import musicbandlab.core.domain.MusicBand;
import musicbandlab.core.domain.MusicGenre;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Абстрактный базовый класс для всех консольных команд приложения.
 * Содержит общую логику работы с аргументами команды, ServiceLocator, Scanner и системными сообщениями.
 * Предоставляет вспомогательные методы для парсинга объекта MusicBand и интерактивного ввода координат, жанра и лейбла.
 */
public abstract class AbstractConsoleCommand {
    protected final ServiceLocator serviceLocator;
    protected final Scanner scanner;
    private final PrintStream systemMessagesStream;
    protected final String[] parts;

    public AbstractConsoleCommand(
            ServiceLocator serviceLocator,
            Scanner scanner,
            PrintStream systemMessagesStream,
            String[] parts,
            int partsRequiredSize) {
        this.serviceLocator = serviceLocator;
        this.scanner = scanner;
        this.systemMessagesStream = systemMessagesStream;
        this.parts = parts;

        throwIfPartsLengthNotEqualTo(partsRequiredSize);
    }

    public abstract void execute() throws  Exception;

    protected MusicBand Parse(int index) {
        Objects.requireNonNull(scanner, "scanner");
        if(parts == null || parts.length - index != 4) {
            throw new IllegalArgumentException("Non-skipped parts should have length 4");
        }
        if(index < 0) {
            throw new IllegalArgumentException("Index out of range");
        }

        int id = parts[index].equals("null")
                ? 1
                : Integer.parseInt(parts[index]);
        String name = parts[index + 1];
        long numberOfParticipants = Long.parseLong(parts[index + 2]);
        long albumsCount = Long.parseLong(parts[index + 3]);

        Coordinates coordinates= GetCoordinates(scanner);
        MusicGenre musicGenre = GetMusicGenre(scanner);
        Label label = GetLabel(scanner);

        return new MusicBand(name, coordinates, numberOfParticipants, albumsCount, musicGenre, label, id);
    }

    private void throwIfPartsLengthNotEqualTo(int length) {
        if(parts.length != length)
            throw new IllegalArgumentException("Invalid arguments count");
    }

    private static String NextLine(Scanner scanner) {
        return scanner.nextLine().trim();
    }

    private Coordinates GetCoordinates(Scanner scanner) {
        return Utils.retryUntilSuccess(() -> {
            systemMessagesStream.println("Введите координаты:");
            int x = Utils.retryUntilSuccess(() -> {
                systemMessagesStream.println("x = ");
                return Integer.parseInt(NextLine(scanner));
            });
            double y = Utils.retryUntilSuccess(() -> {
                systemMessagesStream.println("y =");
                return Double.parseDouble(NextLine(scanner));
            });

            return new Coordinates(x, y);
        });
    }

    private MusicGenre GetMusicGenre(Scanner scanner) {
        String enumPossibleValues = Arrays.stream(MusicGenre.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        return Utils.retryUntilSuccess(() -> {
            systemMessagesStream.println("Введите значение MusicGenre (" + enumPossibleValues + ") или пустую строчку");
            String musicGenreLine = NextLine(scanner);
            return musicGenreLine.isEmpty()
                    ? null
                    : Enum.valueOf(MusicGenre.class, musicGenreLine.toUpperCase());
        });
    }

    private Label GetLabel(Scanner scanner) {
        return Utils.retryUntilSuccess(() -> {
            systemMessagesStream.println("Введите количество групп в лейбле");
            String labelBandsCountLine = NextLine(scanner);
            return new Label(labelBandsCountLine.isEmpty() ? null : Integer.parseInt(labelBandsCountLine));
        });
    }
}