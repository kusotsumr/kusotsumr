package musicbandlab.common.contracts.queries.getmusicbandswherelabellessthan;

import musicbandlab.common.contracts.Request;
import musicbandlab.common.domain.Label;

import java.util.Objects;

/**
 * Запрос на получение музыкальных групп, у которых label меньше заданного.
 * Содержит объект Label для сравнения.
 */
public class GetMusicBandsWhereLabelLessThanQuery implements Request<GetMusicBandsWhereLabelLessThanQueryResponse> {
    private final Label label;
    private final int page;
    private final int pageSize;

    public GetMusicBandsWhereLabelLessThanQuery(Label label,
                                                int page,
                                                int pageSize) {
        Objects.requireNonNull(label, "label");

        if (page < 1) {
            throw new IllegalArgumentException("page must be >= 1");
        }

        if (pageSize < 1) {
            throw new IllegalArgumentException("pageSize must be >= 1");
        }

        this.label = label;
        this.page = page;
        this.pageSize = pageSize;
    }

    public Label getLabel() {
        return label;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }
}