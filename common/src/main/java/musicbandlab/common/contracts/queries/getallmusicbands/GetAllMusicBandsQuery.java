package musicbandlab.common.contracts.queries.getallmusicbands;

import musicbandlab.common.contracts.Request;

public class GetAllMusicBandsQuery implements Request<GetAllMusicBandsQueryResponse> {
    private final int page;
    private final int pageSize;

    public GetAllMusicBandsQuery(
            int page,
            int pageSize) {
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