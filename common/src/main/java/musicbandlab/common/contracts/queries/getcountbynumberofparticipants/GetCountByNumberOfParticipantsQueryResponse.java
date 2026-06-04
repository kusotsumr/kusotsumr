package musicbandlab.common.contracts.queries.getcountbynumberofparticipants;

public class GetCountByNumberOfParticipantsQueryResponse implements java.io.Serializable {
    private final int count;

    public GetCountByNumberOfParticipantsQueryResponse(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
