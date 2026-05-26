package musicbandlab.common.contracts.queries.getmusicbandslabelsdescending;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import musicbandlab.common.contracts.Request;

public class GetMusicBandsLabelsDescendingQuery implements Request<GetMusicBandsLabelsDescendingQueryResponse> {
    private final int page;
    private final int pageSize;

    @JsonCreator
    public GetMusicBandsLabelsDescendingQuery(@JsonProperty("page")int page, @JsonProperty("pageSize")int pageSize) {
        if (page < 1) {
            throw new IllegalArgumentException("page must be >= 1");
        }

        if (pageSize < 1) {
            throw new IllegalArgumentException("pageSize must be >= 1");
        }

        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }
}