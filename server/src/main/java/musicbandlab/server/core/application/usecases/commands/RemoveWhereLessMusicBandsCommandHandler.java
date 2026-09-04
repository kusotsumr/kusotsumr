package musicbandlab.server.core.application.usecases.commands;

import musicbandlab.common.contracts.UnitResponse;
import musicbandlab.common.contracts.commands.removewherelessmusicbands.RemoveWhereLessMusicBandsCommand;
import musicbandlab.server.core.application.usecases.CurrentUserContext;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.Objects;

public class RemoveWhereLessMusicBandsCommandHandler implements RequestHandler<RemoveWhereLessMusicBandsCommand, UnitResponse> {
    private final MusicBandRepository repository;

    public RemoveWhereLessMusicBandsCommandHandler(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public UnitResponse handle(RemoveWhereLessMusicBandsCommand request) {
        Objects.requireNonNull(request, "request");
        repository.removeWhereLessThan(request.getMusicBand(), CurrentUserContext.get());
        return UnitResponse.INSTANCE;
    }
}