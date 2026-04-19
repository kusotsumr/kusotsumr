package musicbandlab.core.application.usecases.commands.updatemusicband;

import musicbandlab.core.domain.MusicBand;

import java.util.Objects;

/**
 * Команда для обновления музыкальной группы по id.
 * Содержит идентификатор и новый объект MusicBand для обновления.
 */
public class UpdateMusicBandCommand {
    private final int id;
    private final MusicBand musicBand;

    public UpdateMusicBandCommand(int id, MusicBand musicBand) {
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