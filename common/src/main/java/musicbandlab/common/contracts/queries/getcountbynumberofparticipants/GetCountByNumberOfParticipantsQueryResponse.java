package musicbandlab.common.contracts.queries.getcountbynumberofparticipants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetCountByNumberOfParticipantsQueryResponse {
    private final int count;

    @JsonCreator
    public GetCountByNumberOfParticipantsQueryResponse(@JsonProperty("count")int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
