package musicbandlab.server.core.application.usecases.commands;

import musicbandlab.common.contracts.UnitResponse;
import musicbandlab.common.contracts.commands.insertmusicband.InsertMusicBandCommand;
import musicbandlab.common.domain.MusicBand;
import musicbandlab.server.core.application.usecases.CurrentUserContext;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.Objects;

public class InsertMusicBandCommandHandler implements RequestHandler<InsertMusicBandCommand, UnitResponse> {
    private final MusicBandRepository musicBandRepository;

    public InsertMusicBandCommandHandler(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public UnitResponse handle(InsertMusicBandCommand request) {
        Objects.requireNonNull(request, "request");

        MusicBand musicBand = request.getMusicBand();
        musicBand.setOwnerLogin(CurrentUserContext.get());

        musicBandRepository.insert(request.getKey(), musicBand);
        return UnitResponse.INSTANCE;
    }
}
