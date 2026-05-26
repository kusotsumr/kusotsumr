package musicbandlab.server.core.application.usecases.commands;

import musicbandlab.common.contracts.UnitResponse;
import musicbandlab.common.contracts.commands.updatemusicband.UpdateMusicBandCommand;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика команды обновления музыкальной группы по id.
 */
public class UpdateMusicBandCommandHandler implements RequestHandler<UpdateMusicBandCommand, UnitResponse> {
    private final MusicBandRepository musicBandRepository;

    public UpdateMusicBandCommandHandler(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public UnitResponse handle(UpdateMusicBandCommand request) {
        Objects.requireNonNull(request, "request");
        musicBandRepository.updateWhereIdIsEqualTo(request.getId(), request.getMusicBand());

        return UnitResponse.INSTANCE;
    }
}