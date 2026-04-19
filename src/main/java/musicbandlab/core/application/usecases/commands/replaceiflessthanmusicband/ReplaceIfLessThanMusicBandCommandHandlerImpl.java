package musicbandlab.core.application.usecases.commands.replaceiflessthanmusicband;

import musicbandlab.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика команды замены элемента (если новый меньше старого).
 */
public class ReplaceIfLessThanMusicBandCommandHandlerImpl implements ReplaceIfLessThanMusicBandCommandHandler {
    private final MusicBandRepository repository;

    public ReplaceIfLessThanMusicBandCommandHandlerImpl(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean handle(ReplaceIfLessThanMusicBandCommand command) {
        Objects.requireNonNull(command, "command");
        return repository.replaceIfLessThan(command.getKey(), command.getMusicBand());
    }
}