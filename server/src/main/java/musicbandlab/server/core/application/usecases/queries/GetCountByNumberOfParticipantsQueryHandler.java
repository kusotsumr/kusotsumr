package musicbandlab.server.core.application.usecases.queries;

import musicbandlab.common.contracts.queries.getcountbynumberofparticipants.GetCountByNumberOfParticipantsQuery;
import musicbandlab.common.contracts.queries.getcountbynumberofparticipants.GetCountByNumberOfParticipantsQueryResponse;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика запроса на получение количества музыкальных групп с заданным числом участников.
 */
public class GetCountByNumberOfParticipantsQueryHandler
        implements RequestHandler<GetCountByNumberOfParticipantsQuery, GetCountByNumberOfParticipantsQueryResponse> {
    private final MusicBandRepository repository;

    public GetCountByNumberOfParticipantsQueryHandler(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public GetCountByNumberOfParticipantsQueryResponse handle(GetCountByNumberOfParticipantsQuery request) {
        Objects.requireNonNull(request, "request");
        int count = repository.getCountWhereNumberOfParticipantsEqualsTo(request.getNumberOfParticipants());

        return new GetCountByNumberOfParticipantsQueryResponse(count);
    }
}