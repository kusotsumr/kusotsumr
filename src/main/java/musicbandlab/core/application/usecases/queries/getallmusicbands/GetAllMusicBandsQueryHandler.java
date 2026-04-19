package musicbandlab.core.application.usecases.queries.getallmusicbands;

import musicbandlab.core.domain.MusicBand;

import java.util.ArrayList;

/**
 * Интерфейс обработчика запроса на получение всех музыкальных групп.
 * Определяет метод для возврата полного списка элементов коллекции.
 */
public interface GetAllMusicBandsQueryHandler {
    public ArrayList<MusicBand> handle();
}