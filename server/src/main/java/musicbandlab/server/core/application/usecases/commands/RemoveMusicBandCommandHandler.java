package musicbandlab.server.core.application.usecases.commands;

import musicbandlab.common.contracts.UnitResponse;
import musicbandlab.common.contracts.commands.removemusicband.RemoveMusicBandCommand;
import musicbandlab.server.core.application.usecases.CurrentUserContext;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.Objects;

public class RemoveMusicBandCommandHandler implements RequestHandler<RemoveMusicBandCommand, UnitResponse> {
    private final MusicBandRepository repository;

    public RemoveMusicBandCommandHandler(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public UnitResponse handle(RemoveMusicBandCommand request) {
        Objects.requireNonNull(request, "request");
        repository.remove(request.getKey(), CurrentUserContext.get());
        return UnitResponse.INSTANCE;
    }
}