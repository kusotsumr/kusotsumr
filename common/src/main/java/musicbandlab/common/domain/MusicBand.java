package musicbandlab.common.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс, представляющий музыкальную группу.
 * Содержит информацию о названии, координатах, дате создания, количестве участников, количестве альбомов, жанре и лейбле.
 * Реализует Comparable для сравнения по координатам.
 */
public class MusicBand implements Comparable<MusicBand>, java.io.Serializable {
    private static final DateTimeFormatter SYSTEM_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss 'МСК'");

    private Integer id;
    private String name; // Поле не может быть null; Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private ZonedDateTime creationDate; // Поле не может быть null, значение этого поля должно генерироваться автоматически
    private long numberOfParticipants; // Значение поля должно быть больше 0
    private long albumsCount; // значение поля должно быть больше 0
    private MusicGenre genre; // поле может быть null
    private Label label; // Поле не может быть null
    private String ownerLogin;

    private MusicBand() {

    }

    public MusicBand(
            String name,
            Coordinates coordinates,
            long numberOfParticipants,
            long albumsCount,
            MusicGenre genre,
            Label label,
            int id) {
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("Name should be not empty");
        if(numberOfParticipants <= 0)
            throw new IllegalArgumentException("Number of participants should be greater than 0");
        if(albumsCount <= 0)
            throw new IllegalArgumentException("Albums count should be greater than 0");
        if(id < 1)
            throw new IllegalArgumentException("id should be greater than 0");
        this.id = id;
        this.name = name;
        this.coordinates = Objects.requireNonNull(coordinates, "coordinates");
        this.creationDate = ZonedDateTime.now();
        this.numberOfParticipants = numberOfParticipants;
        this.albumsCount = albumsCount;
        this.genre = genre;
        this.label = Objects.requireNonNull(label, "label");
    }

    public MusicBand(
            String name,
            Coordinates coordinates,
            long numberOfParticipants,
            long albumsCount,
            MusicGenre genre,
            Label label) {
        this(name, coordinates, numberOfParticipants, albumsCount, genre, label, 1);
    }

    public void mapFrom(MusicBand from) {
        name = from.name;
        coordinates = from.coordinates;
        creationDate = from.creationDate;
        numberOfParticipants = from.numberOfParticipants;
        albumsCount = from.albumsCount;
        genre = from.genre;
        label = from.label;
        // ownerLogin намеренно НЕ копируется: владелец объекта не должен
        // меняться при update/replace, только при первоначальном insert.
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public long getNumberOfParticipants () {
        return numberOfParticipants;
    }

    public long getAlbumsCount() {
        return albumsCount;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public Label getLabel() {
        return label;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    @Override
    public int compareTo(MusicBand other) {
        Objects.requireNonNull(coordinates);
        Objects.requireNonNull(other.coordinates);
        return coordinates.compareTo(other.coordinates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MusicBand)) return false;
        MusicBand that = (MusicBand) o;
        return numberOfParticipants == that.numberOfParticipants &&
                albumsCount == that.albumsCount &&
                Objects.equals(name, that.name) &&
                Objects.equals(coordinates, that.coordinates) &&
                Objects.equals(genre, that.genre) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, numberOfParticipants, albumsCount, genre, label);
    }

    @Override
    public String toString() {
        return "Музыкальная группа {\n" +
                "    Идентификатор     = " + id + "\n" +
                "    название          = '" + name + "'\n" +
                "    координаты        = " + coordinates + "\n" +
                "    дата создания     = " + creationDate.withZoneSameInstant(ZoneId.of("Europe/Moscow")).format(SYSTEM_DATE_FORMATTER) + "\n" +
                "    кол-во участников = " + numberOfParticipants + "\n" +
                "    кол-во альбомов   = " + albumsCount + "\n" +
                "    жанр              = " + (genre != null ? genre : "отсутствует") + "\n" +
                "    лейбл             = " + label + "\n" +
                "    владелец          = " + (ownerLogin != null ? ownerLogin : "отсутствует") + "\n" +
                "}";
    }
}