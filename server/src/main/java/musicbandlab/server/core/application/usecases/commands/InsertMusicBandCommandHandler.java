package musicbandlab.server.core.application.usecases.commands;

import musicbandlab.common.contracts.UnitResponse;
import musicbandlab.common.contracts.commands.insertmusicband.InsertMusicBandCommand;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика команды вставки новой музыкальной группы.
 */
public class InsertMusicBandCommandHandler implements RequestHandler<InsertMusicBandCommand, UnitResponse> {
    public MusicBandRepository musicBandRepository;

    public InsertMusicBandCommandHandler(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public UnitResponse handle(InsertMusicBandCommand request) {
        Objects.requireNonNull(request, "request");
        musicBandRepository.insert(request.getKey(), request.getMusicBand());

        return UnitResponse.INSTANCE;
    }
}