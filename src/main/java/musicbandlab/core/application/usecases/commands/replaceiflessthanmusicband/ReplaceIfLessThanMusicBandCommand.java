package musicbandlab.core.application.usecases.commands.replaceiflessthanmusicband;

import musicbandlab.core.domain.MusicBand;

import java.util.Objects;

/**
 * Команда для замены элемента, если новое значение меньше старого.
 * Содержит ключ и новый объект MusicBand для замены.
 */
public class ReplaceIfLessThanMusicBandCommand {
    private final String key;
    private final MusicBand musicBand;

    public ReplaceIfLessThanMusicBandCommand(String key, MusicBand musicBand) {
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