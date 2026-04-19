package musicbandlab.core.application.usecases.commands.replaceifgreaterthanmusicband;

import musicbandlab.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика команды замены элемента (если новый больше старого).
 */
public class ReplaceIfGreaterThanMusicBandCommandHandlerImpl implements ReplaceIfGreaterThanMusicBandCommandHandler {
    private final MusicBandRepository repository;

    public ReplaceIfGreaterThanMusicBandCommandHandlerImpl(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean handle(ReplaceIfGreaterThanMusicBandCommand command) {
        Objects.requireNonNull(command, "command");
        return repository.replaceIfGreaterThan(command.getKey(), command.getMusicBand());
    }
}