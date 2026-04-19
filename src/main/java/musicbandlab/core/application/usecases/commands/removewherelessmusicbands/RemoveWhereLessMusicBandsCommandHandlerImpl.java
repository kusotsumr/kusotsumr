package musicbandlab.core.application.usecases.commands.removewherelessmusicbands;

import musicbandlab.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика команды удаления всех элементов, меньших заданного.
 */
public class RemoveWhereLessMusicBandsCommandHandlerImpl implements RemoveWhereLessMusicBandsCommandHandler{
    private final MusicBandRepository repository;

    public RemoveWhereLessMusicBandsCommandHandlerImpl(MusicBandRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void handle(RemoveWhereLessMusicBandsCommand command) {
        Objects.requireNonNull(command, "command");
        repository.removeWhereLessThan(command.getMusicBand());
    }
}