package musicbandlab.core.application.usecases.commands.removewherelessmusicbands;

import musicbandlab.core.domain.MusicBand;

import java.util.Objects;

/**
 * Команда для удаления всех элементов, меньших заданного.
 * Содержит объект MusicBand, с которым сравниваются элементы коллекции для удаления.
 */
public class RemoveWhereLessMusicBandsCommand {
    private final MusicBand musicBand;

    public RemoveWhereLessMusicBandsCommand(MusicBand musicBand) {
        Objects.requireNonNull(musicBand, "musicBand");
        this.musicBand = musicBand;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }
}