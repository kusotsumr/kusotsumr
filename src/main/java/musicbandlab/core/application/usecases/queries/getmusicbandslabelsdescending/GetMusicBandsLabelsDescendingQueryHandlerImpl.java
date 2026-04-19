package musicbandlab.core.application.usecases.queries.getmusicbandslabelsdescending;

import musicbandlab.core.domain.Label;
import musicbandlab.core.ports.MusicBandRepository;

import java.util.ArrayList;

/**
 * Реализация обработчика запроса на получение лейблов в порядке убывания.
 */
public class GetMusicBandsLabelsDescendingQueryHandlerImpl implements  GetMusicBandsLabelsDescendingQueryHandler {
    private final MusicBandRepository repository;

    public GetMusicBandsLabelsDescendingQueryHandlerImpl(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArrayList<Label> handle() {
        return repository.getLabelsDescending();
    }
}