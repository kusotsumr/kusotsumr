package musicbandlab.server.core.application.usecases.queries;

import musicbandlab.common.contracts.queries.getmusicbandswherelabellessthan.GetMusicBandsWhereLabelLessThanQuery;
import musicbandlab.common.contracts.queries.getmusicbandswherelabellessthan.GetMusicBandsWhereLabelLessThanQueryResponse;
import musicbandlab.common.domain.MusicBand;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Реализация обработчика запроса на получение музыкальных групп с label меньше заданного.
 */
public class GetMusicBandsWhereLabelLessThanQueryHandler
        implements RequestHandler<GetMusicBandsWhereLabelLessThanQuery, GetMusicBandsWhereLabelLessThanQueryResponse> {
    private final MusicBandRepository repository;

    public GetMusicBandsWhereLabelLessThanQueryHandler(MusicBandRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public GetMusicBandsWhereLabelLessThanQueryResponse handle(GetMusicBandsWhereLabelLessThanQuery request) {
        Objects.requireNonNull(request, "request");
        ArrayList<MusicBand> musicBands = repository.getWhereLabelLessThan(
                request.getLabel(), request.getPage(), request.getPageSize());

        return new GetMusicBandsWhereLabelLessThanQueryResponse(musicBands);
    }
}