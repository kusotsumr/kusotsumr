package musicbandlab.server.core.application.usecases.commands;

import musicbandlab.common.contracts.UnitResponse;
import musicbandlab.common.contracts.commands.updatemusicband.UpdateMusicBandCommand;
import musicbandlab.server.core.application.usecases.CurrentUserContext;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.Objects;

public class UpdateMusicBandCommandHandler implements RequestHandler<UpdateMusicBandCommand, UnitResponse> {
    private final MusicBandRepository musicBandRepository;

    public UpdateMusicBandCommandHandler(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public UnitResponse handle(UpdateMusicBandCommand request) {
        Objects.requireNonNull(request, "request");
        musicBandRepository.updateWhereIdIsEqualTo(request.getId(), request.getMusicBand(), CurrentUserContext.get());
        return UnitResponse.INSTANCE;
    }
}