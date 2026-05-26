package musicbandlab.server.core.application.usecases.queries;

import musicbandlab.common.contracts.queries.getmusicbandsinfo.GetMusicBandsInfoQuery;
import musicbandlab.common.contracts.queries.getmusicbandsinfo.GetMusicBandsInfoQueryResponse;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Реализация обработчика запроса на получение информации о коллекции.
 */
public class GetMusicBandsInfoQueryHandler
        implements RequestHandler<GetMusicBandsInfoQuery, GetMusicBandsInfoQueryResponse> {
    private final MusicBandRepository musicBandRepository;

    public GetMusicBandsInfoQueryHandler(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public GetMusicBandsInfoQueryResponse handle(GetMusicBandsInfoQuery request) {
        Objects.requireNonNull(request, "request");
        ZonedDateTime initializationDate = musicBandRepository.getInitializationDate();
        int size = musicBandRepository.getSize();

        return new GetMusicBandsInfoQueryResponse("hashSet", initializationDate, size);
    }
}