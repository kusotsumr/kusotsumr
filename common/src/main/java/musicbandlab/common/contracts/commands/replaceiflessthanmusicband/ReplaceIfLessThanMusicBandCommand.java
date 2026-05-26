package musicbandlab.common.contracts.commands.replaceiflessthanmusicband;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import musicbandlab.common.contracts.Request;
import musicbandlab.common.domain.MusicBand;

import java.util.Objects;

/**
 * Команда для замены элемента, если новое значение меньше старого.
 * Содержит ключ и новый объект MusicBand для замены.
 */
public class ReplaceIfLessThanMusicBandCommand implements Request<ReplaceIfLessThanMusicBandCommandResponse> {
    private final String key;
    private final MusicBand musicBand;

    @JsonCreator
    public ReplaceIfLessThanMusicBandCommand(@JsonProperty("key")String key, @JsonProperty("musicBand")MusicBand musicBand) {
        if(key == null || key.isEmpty())
            throw new IllegalArgumentException("key should be not empty");
        Objects.requireNonNull(musicBand, "musicBand");

        this.key = key;
        this.musicBand = musicBand;
    }

    public String getKey() {
        return key;
    }
    public MusicBand getMusicBand() {
        return musicBand;
    }
}