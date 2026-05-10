package musicbandlab.consoleui.commands;

import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.Utils;
import musicbandlab.core.domain.Coordinates;
import musicbandlab.core.domain.Label;
import musicbandlab.core.domain.MusicBand;
import musicbandlab.core.domain.MusicGenre;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;
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

    public abstract void execute() throws Exception;

    protected MusicBand Parse() {
        if (parts == null) {
            throw new IllegalArgumentException("Non-skipped parts should have length 4");
        }
        int id = GetId();
        String name = GetName();
        Long numberOfParticipants = GetNumberOfParticipants();
        Long albumsCount = GetAlbumsCount();
        Coordinates coordinates = GetCoordinates();
        MusicGenre musicGenre = GetMusicGenre();
        Label label = GetLabel();

        return new MusicBand(name, coordinates, numberOfParticipants, albumsCount, musicGenre, label, id);
    }

    private void throwIfPartsLengthNotEqualTo(int length) {
        if (parts.length != length)
            throw new IllegalArgumentException("Invalid arguments count");
    }

    private String NextLine() {
        return scanner.nextLine().trim();
    }

    private int GetId() {
        return GetLineValue("Введите id:", (line)
                -> line == null || line.equalsIgnoreCase("null")
                ? 1
                : Integer.parseInt(line));
    }

    private String GetName() {
        return GetLineValue("Введите Имя:", (line) -> line);
    }

    private long GetNumberOfParticipants() {
        return GetLineValue("Введите количество участников:", (line) -> Long.parseLong(line));
    }

    private long GetAlbumsCount() {
        return GetLineValue("Введите количество альбомов:", (line) -> Long.parseLong(line));
    }

    private <T> T GetLineValue(String message, Function<String, T> parser) {
        return Utils.retryUntilSuccess(() -> {
            systemMessagesStream.println(message);
            String line = NextLine();
            return parser.apply(line);
        });
    }

    private Coordinates GetCoordinates() {
        return Utils.retryUntilSuccess(() -> {
            systemMessagesStream.println("Введите координаты:");
            int x = GetLineValue("x = ", (line)
                    -> Integer.parseInt(line));
            double y = GetLineValue("y = ", (line)
                    -> Double.parseDouble(line));
            return new Coordinates(x, y);
        });
    }

    private MusicGenre GetMusicGenre() {
        String enumPossibleValues = Arrays.stream(MusicGenre.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        String message = "Введите значение MusicGenre (" + enumPossibleValues + ") или пустую строчку";
        return GetLineValue(message, (line)
                -> line.isEmpty()
                ? null
                : Enum.valueOf(MusicGenre.class, line.toUpperCase()));
    }

    private Label GetLabel() {
        return GetLineValue("Введите количество групп в лейбле", (line)
                -> line.isEmpty()
                ? null
                : new Label(Integer.parseInt(line)));
    }
}