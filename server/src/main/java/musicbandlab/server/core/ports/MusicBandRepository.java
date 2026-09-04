package musicbandlab.server.core.ports;

import musicbandlab.common.contracts.DataEntry;
import musicbandlab.common.domain.Label;
import musicbandlab.common.domain.MusicBand;

import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Интерфейс репозитория для работы с коллекцией музыкальных групп.
 * Методы-модификаторы (кроме insert) принимают currentUserLogin — логин
 * пользователя, от чьего имени выполняется операция — чтобы реализация
 * могла ограничить изменение только объектами этого пользователя.
 */
public interface MusicBandRepository {
    ArrayList<DataEntry<String, MusicBand>> getAll(int page, int pageSize);
    MusicBand get(String key);
    void insert(String key, MusicBand musicBand);
    void updateWhereIdIsEqualTo(int id, MusicBand musicBand, String currentUserLogin);
    void remove(String key, String currentUserLogin);
    void clear(String currentUserLogin);
    void removeWhereLessThan(MusicBand musicBand, String currentUserLogin);
    Boolean replaceIfGreaterThan(String key, MusicBand band, String currentUserLogin);
    Boolean replaceIfLessThan(String key, MusicBand band, String currentUserLogin);
    int getCountWhereNumberOfParticipantsEqualsTo(long numberOfParticipants);
    ArrayList<MusicBand> getWhereLabelLessThan(Label label, int page, int pageSize);
    ArrayList<Label> getLabelsDescending(int page, int pageSize);
    int getSize();
    ZonedDateTime getInitializationDate();
    String serializeToJson();
}