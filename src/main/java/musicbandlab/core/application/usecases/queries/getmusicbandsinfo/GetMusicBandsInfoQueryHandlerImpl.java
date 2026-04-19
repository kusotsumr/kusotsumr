package musicbandlab.core.application.usecases.queries.getmusicbandsinfo;

import musicbandlab.core.ports.MusicBandRepository;

import java.time.ZonedDateTime;

/**
 * Реализация обработчика запроса на получение информации о коллекции.
 */
public class GetMusicBandsInfoQueryHandlerImpl implements GetMusicBandsInfoQueryHandler{
    private final MusicBandRepository musicBandRepository;

    public GetMusicBandsInfoQueryHandlerImpl(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public GetMusicBandsInfoQueryResponse handle() {
        ZonedDateTime initializationDate = musicBandRepository.getInitializationDate();
        int size = musicBandRepository.getSize();

        return new GetMusicBandsInfoQueryResponse("hashSet", initializationDate, size);
    }
}