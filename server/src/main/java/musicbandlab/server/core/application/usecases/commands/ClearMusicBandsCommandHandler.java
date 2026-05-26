package musicbandlab.server.core.application.usecases.commands;

import musicbandlab.common.contracts.UnitResponse;
import musicbandlab.common.contracts.commands.clearmusicbands.ClearMusicBandsCommand;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика команды очистки коллекции.
 */
public class ClearMusicBandsCommandHandler implements RequestHandler<ClearMusicBandsCommand, UnitResponse> {
    private final MusicBandRepository repository;

    public ClearMusicBandsCommandHandler(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public UnitResponse handle(ClearMusicBandsCommand request) {
        Objects.requireNonNull(request, "request");
        repository.clear();

        return UnitResponse.INSTANCE;
    }
}