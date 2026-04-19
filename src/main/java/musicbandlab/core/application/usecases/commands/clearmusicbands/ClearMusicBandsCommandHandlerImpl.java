package musicbandlab.core.application.usecases.commands.clearmusicbands;

import musicbandlab.core.ports.MusicBandRepository;

/**
 * Реализация обработчика команды очистки коллекции.
 */
public class ClearMusicBandsCommandHandlerImpl implements  ClearMusicBandsCommandHandler{
    private final MusicBandRepository repository;

    public ClearMusicBandsCommandHandlerImpl(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle() {
        repository.clear();
    }
}
