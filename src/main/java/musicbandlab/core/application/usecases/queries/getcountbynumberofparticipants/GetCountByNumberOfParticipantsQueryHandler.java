package musicbandlab.core.application.usecases.queries.getcountbynumberofparticipants;

/**
 * Интерфейс обработчика запроса на получение количества музыкальных групп с заданным числом участников.
 * Определяет метод для выполнения подсчета элементов по критерию.
 */
public interface GetCountByNumberOfParticipantsQueryHandler {
    public int handle(GetCountByNumberOfParticipantsQuery query);
}