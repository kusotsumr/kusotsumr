package musicbandlab.core.application.usecases.queries.getmusicbandswherelabellessthan;

import musicbandlab.core.domain.MusicBand;

import java.util.ArrayList;

/**
 * Интерфейс обработчика запроса на получение музыкальных групп с label меньше заданного.
 * Определяет метод для выполнения фильтрации элементов коллекции.
 */
public interface GetMusicBandsWhereLabelLessThanQueryHandler {
    public ArrayList<MusicBand> handle(GetMusicBandsWhereLabelLessThanQuery query);
}