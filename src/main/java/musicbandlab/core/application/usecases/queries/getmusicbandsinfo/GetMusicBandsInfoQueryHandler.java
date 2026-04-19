package musicbandlab.core.application.usecases.queries.getmusicbandsinfo;

/**
 * Интерфейс обработчика запроса на получение информации о коллекции.
 * Возвращает тип коллекции, дату инициализации и количество элементов.
 */
public interface GetMusicBandsInfoQueryHandler {
    public GetMusicBandsInfoQueryResponse handle();
}