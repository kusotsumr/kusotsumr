package musicbandlab.core.application.usecases.queries.getcountbynumberofparticipants;

/**
 * Запрос на получение количества музыкальных групп с заданным числом участников.
 * Содержит значение numberOfParticipants для фильтрации.
 */
public class GetCountByNumberOfParticipantsQuery {
    private final long numberOfParticipants;

    public GetCountByNumberOfParticipantsQuery(long numberOfParticipants) {
        if (numberOfParticipants < 1) {
            throw new IllegalArgumentException("Number of participants be greater than or equal to 1");
        }

        this.numberOfParticipants = numberOfParticipants;
    }

    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }
}