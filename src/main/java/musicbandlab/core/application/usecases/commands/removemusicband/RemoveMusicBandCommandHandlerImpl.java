package musicbandlab.core.application.usecases.commands.removemusicband;

import musicbandlab.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика команды удаления музыкальной группы по ключу.
 */
public class RemoveMusicBandCommandHandlerImpl implements RemoveMusicBandCommandHandler{
    private final MusicBandRepository repository;

    public RemoveMusicBandCommandHandlerImpl(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(RemoveMusicBandCommand command) {
        Objects.requireNonNull(command, "command");
        repository.remove(command.getKey());
    }
}