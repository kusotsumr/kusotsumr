package musicbandlab.core.application.usecases.queries.getmusicbandslabelsdescending;

import musicbandlab.core.domain.Label;

import java.util.ArrayList;

/**
 * Интерфейс обработчика запроса на получение лейблов в порядке убывания.
 * Возвращает список всех лейблов музыкальных групп, отсортированных по убыванию.
 */
public interface GetMusicBandsLabelsDescendingQueryHandler {
    public ArrayList<Label> handle();
}