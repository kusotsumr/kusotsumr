package musicbandlab.core.application.usecases.queries.getmusicbandswherelabellessthan;

import musicbandlab.core.domain.MusicBand;
import musicbandlab.core.ports.MusicBandRepository;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Реализация обработчика запроса на получение музыкальных групп с label меньше заданного.
 */
public class GetMusicBandsWhereLabelLessThanQueryHandlerImpl implements  GetMusicBandsWhereLabelLessThanQueryHandler{
    private final MusicBandRepository repository;

    public GetMusicBandsWhereLabelLessThanQueryHandlerImpl(MusicBandRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public ArrayList<MusicBand> handle(GetMusicBandsWhereLabelLessThanQuery query) {
        Objects.requireNonNull(query, "query");
        return repository.getWhereLabelLessThan(query.getLabel());
    }
}