package musicbandlab.core.ports;

import musicbandlab.core.domain.Label;
import musicbandlab.core.domain.MusicBand;

import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Интерфейс репозитория для работы с коллекцией музыкальных групп.
 * Определяет все доступные операции для получения, добавления, обновления, удаления элементов и получения различной информации о коллекции.
 */
public interface MusicBandRepository {
    public ArrayList<MusicBand> getAll();
    public MusicBand get(String key);
    public void insert(String key, MusicBand musicBand);
    public void updateWhereIdIsEqualTo(int id, MusicBand musicBand);
    public void remove(String key);
    public void clear();
    public void removeWhereLessThan(MusicBand musicBand);
    public Boolean replaceIfGreaterThan(String key, MusicBand band);
    public Boolean replaceIfLessThan(String key, MusicBand band);
    public int getCountWhereNumberOfParticipantsEqualsTo(long numberOfParticipants);
    public ArrayList<MusicBand> getWhereLabelLessThan(Label label);
    public ArrayList<Label> getLabelsDescending();
    public int getSize();
    public ZonedDateTime getInitializationDate();
    public String serializeToJson();
}