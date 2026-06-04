package musicbandlab.common.contracts.queries.getmusicbandsinfo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Ответ на запрос информации о коллекции.
 * Содержит тип коллекции, дату инициализации и количество элементов.
 */
public final class GetMusicBandsInfoQueryResponse implements java.io.Serializable {
    private static final DateTimeFormatter SYSTEM_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss 'МСК'");
    private final String type;
    private final ZonedDateTime initializationDate;
    private final int size;

    public GetMusicBandsInfoQueryResponse(
            String type,
            ZonedDateTime initializationDate,
            int size) {
        this.type = type;
        this.initializationDate = initializationDate;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public ZonedDateTime getInitializationDate() {
        return initializationDate;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        String date = initializationDate == null
                ? "не инициализирована"
                : initializationDate.withZoneSameInstant(ZoneId.of("Europe/Moscow")).format(SYSTEM_DATE_FORMATTER);

        return "Информация о коллекции:{\n" +
                "    type                = '" + type + "'\n" +
                "    initializationDate  = " + date + "\n" +
                "    size                = " + size + "\n" +
                "}";
    }
}