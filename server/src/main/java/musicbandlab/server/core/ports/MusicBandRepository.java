package musicbandlab.server.core.ports;

import musicbandlab.common.domain.Label;
import musicbandlab.common.domain.MusicBand;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * Интерфейс репозитория для работы с коллекцией музыкальных групп.
 * Определяет все доступные операции для получения, добавления, обновления, удаления элементов и получения различной информации о коллекции.
 */
public interface MusicBandRepository {
    public ArrayList<Map.Entry<String, MusicBand>> getAll(int page, int pageSize);
    public MusicBand get(String key);
    public void insert(String key, MusicBand musicBand);
    public void updateWhereIdIsEqualTo(int id, MusicBand musicBand);
    public void remove(String key);
    public void clear();
    public void removeWhereLessThan(MusicBand musicBand);
    public Boolean replaceIfGreaterThan(String key, MusicBand band);
    public Boolean replaceIfLessThan(String key, MusicBand band);
    public int getCountWhereNumberOfParticipantsEqualsTo(long numberOfParticipants);
    public ArrayList<MusicBand> getWhereLabelLessThan(Label label, int page, int pageSize);
    public ArrayList<Label> getLabelsDescending(int page, int pageSize);
    public int getSize();
    public ZonedDateTime getInitializationDate();
    public String serializeToJson();
}