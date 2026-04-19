package musicbandlab.core.application.usecases.commands.insertmusicband;

import musicbandlab.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика команды вставки новой музыкальной группы.
 */
public class InsertMusicBandCommandHandlerImpl implements InsertMusicBandCommandHandler {
    public MusicBandRepository musicBandRepository;

    public InsertMusicBandCommandHandlerImpl(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public void handle(InsertMusicBandCommand command) {
        Objects.requireNonNull(command, "command");
        musicBandRepository.insert(command.getKey(), command.getMusicBand());
    }
}