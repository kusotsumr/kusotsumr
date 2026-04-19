package musicbandlab.core.application.usecases.queries.getcountbynumberofparticipants;

import musicbandlab.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика запроса на получение количества музыкальных групп с заданным числом участников.
 */
public class GetCountByNumberOfParticipantsQueryHandlerImpl implements  GetCountByNumberOfParticipantsQueryHandler{
    private final MusicBandRepository repository;

    public GetCountByNumberOfParticipantsQueryHandlerImpl(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public int handle(GetCountByNumberOfParticipantsQuery query) {
        Objects.requireNonNull(query, "query");
        return repository.getCountWhereNumberOfParticipantsEqualsTo(query.getNumberOfParticipants());
    }
}