package musicbandlab.server.core.application.usecases.queries;

import musicbandlab.common.contracts.queries.getallmusicbands.GetAllMusicBandsQuery;
import musicbandlab.common.contracts.queries.getallmusicbands.GetAllMusicBandsQueryResponse;
import musicbandlab.common.domain.MusicBand;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Реализация обработчика запроса на получение всех музыкальных групп.
 */
public class GetAllMusicBandsQueryHandler
        implements RequestHandler<GetAllMusicBandsQuery, GetAllMusicBandsQueryResponse> {
    private final MusicBandRepository musicBandRepository;

    public GetAllMusicBandsQueryHandler(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public GetAllMusicBandsQueryResponse handle(GetAllMusicBandsQuery request) {
        Objects.requireNonNull(request, "request");
        ArrayList<Map.Entry<String, MusicBand>> musicBands = musicBandRepository.getAll(
                request.getPage(), request.getPageSize());

        return new GetAllMusicBandsQueryResponse(musicBands);
    }
}