package musicbandlab.core.application.usecases.queries.getmusicbandswherelabellessthan;

import musicbandlab.core.domain.Label;

import java.util.Objects;

/**
 * Запрос на получение музыкальных групп, у которых label меньше заданного.
 * Содержит объект Label для сравнения.
 */
public class GetMusicBandsWhereLabelLessThanQuery {
    private final Label label;

    public GetMusicBandsWhereLabelLessThanQuery(Label label) {
        Objects.requireNonNull(label, "label");
        this.label = label;
    }

    public Label getLabel() {
        return label;
    }
}