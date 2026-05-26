package musicbandlab.common.contracts.queries.getcountbynumberofparticipants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import musicbandlab.common.contracts.Request; /**
 * Запрос на получение количества музыкальных групп с заданным числом участников.
 * Содержит значение numberOfParticipants для фильтрации.
 */
public class GetCountByNumberOfParticipantsQuery implements Request<GetCountByNumberOfParticipantsQueryResponse> {
    private final long numberOfParticipants;

    @JsonCreator
    public GetCountByNumberOfParticipantsQuery(@JsonProperty("numberOfParticipants")long numberOfParticipants) {
        if (numberOfParticipants < 1) {
            throw new IllegalArgumentException("Number of participants be greater than or equal to 1");
        }

        this.numberOfParticipants = numberOfParticipants;
    }

    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }
}