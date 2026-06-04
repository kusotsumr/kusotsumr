package musicbandlab.common.contracts.queries.getmusicbandslabelsdescending;

import musicbandlab.common.domain.Label;

import java.util.ArrayList;

public class GetMusicBandsLabelsDescendingQueryResponse implements java.io.Serializable {
    private final ArrayList<Label> labels;

    public GetMusicBandsLabelsDescendingQueryResponse(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }
}