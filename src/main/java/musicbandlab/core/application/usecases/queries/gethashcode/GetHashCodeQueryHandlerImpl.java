package musicbandlab.core.application.usecases.queries.gethashcode;

import musicbandlab.core.ports.MusicBandRepository;

public class GetHashCodeQueryHandlerImpl implements GetHashCodeQueryHandler {
    private final MusicBandRepository musicBandRepository;

    public GetHashCodeQueryHandlerImpl(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public int handle() {
        return musicBandRepository.getHashCode();
    }
}