package musicbandlab.common.contracts.queries.getmusicbandsinfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

/**
 * Ответ на запрос информации о коллекции.
 * Содержит тип коллекции, дату инициализации и количество элементов.
 */
public final class GetMusicBandsInfoQueryResponse {
    private final String type;
    private final ZonedDateTime initializationDate;
    private final int size;

    @JsonCreator
    public GetMusicBandsInfoQueryResponse(
            @JsonProperty("type")String type,
            @JsonProperty("initializationDate")ZonedDateTime initializationDate,
            @JsonProperty("size")int size) {
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
        return "Информация о коллекции:{\n" +
                "    type                = '" + type + "'\n" +
                "    initializationDate  = " + initializationDate + "\n" +
                "    size                = " + size + "\n" +
                "}";
    }
}