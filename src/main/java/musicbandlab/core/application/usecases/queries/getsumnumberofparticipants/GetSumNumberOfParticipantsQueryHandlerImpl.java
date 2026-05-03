package musicbandlab.core.application.usecases.queries.getsumnumberofparticipants;

import musicbandlab.core.ports.MusicBandRepository;

public class GetSumNumberOfParticipantsQueryHandlerImpl implements GetSumNumberOfParticipantsQueryHandler {
    private final MusicBandRepository repository;

    public GetSumNumberOfParticipantsQueryHandlerImpl(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public long handle() {
        return repository.getSumNumberOfParticipants();
    }
}