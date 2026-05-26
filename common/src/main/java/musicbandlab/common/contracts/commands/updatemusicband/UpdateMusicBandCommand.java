package musicbandlab.common.contracts.commands.updatemusicband;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import musicbandlab.common.contracts.Request;
import musicbandlab.common.contracts.UnitResponse;
import musicbandlab.common.domain.MusicBand;

import java.util.Objects;

/**
 * Команда для обновления музыкальной группы по id.
 * Содержит идентификатор и новый объект MusicBand для обновления.
 */
public class UpdateMusicBandCommand implements Request<UnitResponse> {
    private final int id;
    private final MusicBand musicBand;

    @JsonCreator
    public UpdateMusicBandCommand(@JsonProperty("id")int id, @JsonProperty("musicBand")MusicBand musicBand) {
        this.id = id;
        this.musicBand = Objects.requireNonNull(musicBand, "musicBand");
    }

    public int getId() {
        return id;
    }
    public MusicBand getMusicBand() {
        return musicBand;
    }
}