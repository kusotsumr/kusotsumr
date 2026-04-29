package musicbandlab.core.application.usecases.queries.getallmusicbands;

import musicbandlab.core.domain.MusicBand;
import musicbandlab.core.ports.MusicBandRepository;

import java.util.ArrayList;
import java.util.Map;

/**
 * Реализация обработчика запроса на получение всех музыкальных групп.
 */
public class GetAllMusicBandsQueryHandlerImpl implements GetAllMusicBandsQueryHandler {
    private final MusicBandRepository musicBandRepository;

    public GetAllMusicBandsQueryHandlerImpl(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public ArrayList<Map.Entry<String, MusicBand>> handle() {
        return musicBandRepository.getAll();
    }
}