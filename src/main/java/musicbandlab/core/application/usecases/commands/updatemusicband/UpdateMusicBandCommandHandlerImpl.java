package musicbandlab.core.application.usecases.commands.updatemusicband;

import musicbandlab.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика команды обновления музыкальной группы по id.
 */
public class UpdateMusicBandCommandHandlerImpl implements UpdateMusicBandCommandHandler {
    private final MusicBandRepository musicBandRepository;

    public UpdateMusicBandCommandHandlerImpl(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public void handle(UpdateMusicBandCommand command) {
        Objects.requireNonNull(command, "command");
        musicBandRepository.updateWhereIdIsEqualTo(command.getId(), command.getMusicBand());
    }
}