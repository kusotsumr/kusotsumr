package musicbandlab.common.contracts.queries.getmusicbandslabelsdescending;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import musicbandlab.common.domain.Label;

import java.util.ArrayList;

public class GetMusicBandsLabelsDescendingQueryResponse {
    private final ArrayList<Label> labels;

    @JsonCreator
    public GetMusicBandsLabelsDescendingQueryResponse(@JsonProperty("labels")ArrayList<Label> labels) {
        this.labels = labels;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }
}