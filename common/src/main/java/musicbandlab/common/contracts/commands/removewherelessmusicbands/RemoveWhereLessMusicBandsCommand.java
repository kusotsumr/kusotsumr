package musicbandlab.common.contracts.commands.removewherelessmusicbands;

import musicbandlab.common.contracts.Request;
import musicbandlab.common.contracts.UnitResponse;
import musicbandlab.common.domain.MusicBand;

import java.util.Objects;

/**
 * Команда для удаления всех элементов, меньших заданного.
 * Содержит объект MusicBand, с которым сравниваются элементы коллекции для удаления.
 */
public class RemoveWhereLessMusicBandsCommand implements Request<UnitResponse> {
    private final MusicBand musicBand;

    public RemoveWhereLessMusicBandsCommand(MusicBand musicBand) {
        Objects.requireNonNull(musicBand, "musicBand");
        this.musicBand = musicBand;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }
}