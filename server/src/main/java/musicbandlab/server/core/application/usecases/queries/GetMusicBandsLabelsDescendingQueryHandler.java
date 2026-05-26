package musicbandlab.server.core.application.usecases.queries;

import musicbandlab.common.contracts.queries.getmusicbandslabelsdescending.GetMusicBandsLabelsDescendingQuery;
import musicbandlab.common.contracts.queries.getmusicbandslabelsdescending.GetMusicBandsLabelsDescendingQueryResponse;
import musicbandlab.common.domain.Label;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Реализация обработчика запроса на получение лейблов в порядке убывания.
 */
public class GetMusicBandsLabelsDescendingQueryHandler
        implements RequestHandler<GetMusicBandsLabelsDescendingQuery, GetMusicBandsLabelsDescendingQueryResponse> {
    private final MusicBandRepository repository;

    public GetMusicBandsLabelsDescendingQueryHandler(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public GetMusicBandsLabelsDescendingQueryResponse handle(GetMusicBandsLabelsDescendingQuery request) {
        Objects.requireNonNull(request, "request");
        ArrayList<Label> labels =  repository.getLabelsDescending(request.getPage(), request.getPageSize());

        return new GetMusicBandsLabelsDescendingQueryResponse(labels);
    }
}