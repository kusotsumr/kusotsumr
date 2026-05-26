package musicbandlab.common.domain;

import java.util.Objects;

/**
 * Класс, представляющий лейбл музыкальной группы.
 * Хранит количество групп на лейбле и позволяет сравнивать лейблы по этому значению.
 */
public class Label implements Comparable<Label> {
    private Integer bands;

    private Label() {
        bands = null;
    }

    public Label(Integer bands) {
        this.bands = bands;
    }

    public Integer getBands() {
        return bands;
    }

    @Override
    public int compareTo(Label other) {
        if (this.bands == null && other.bands == null) {
            return 0;
        }
        if (this.bands == null) {
            return -1;
        }
        if (other.bands == null) {
            return 1;
        }
        return this.bands.compareTo(other.bands);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Label)) return false;

        Label label = (Label) o;

        return Objects.equals(this.bands, label.bands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bands);
    }

    @Override
    public String toString() {
        return bands == null ? "отсутствует" : "(кол-во групп: " + bands + ")";
    }
}