package musicbandlab.common.contracts.commands.insertmusicband;

import musicbandlab.common.contracts.Request;import musicbandlab.common.contracts.UnitResponse;import musicbandlab.common.domain.MusicBand;

import java.util.Objects;

/**
 * Команда для добавления новой музыкальной группы в коллекцию.
 * Содержит ключ и объект MusicBand, который необходимо вставить.
 */
public class InsertMusicBandCommand implements Request<UnitResponse> {
    private final String key;
    private final MusicBand musicBand;

    public InsertMusicBandCommand(String key, MusicBand musicBand) {
        if(key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key should be not empty");
        }

        this.key = key;
        this.musicBand = Objects.requireNonNull(musicBand, "musicBand");
    }

    public String getKey() {
        return key;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }
}