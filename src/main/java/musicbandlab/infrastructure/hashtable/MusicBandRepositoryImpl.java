package musicbandlab.infrastructure.hashtable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import musicbandlab.core.domain.Label;
import musicbandlab.core.domain.MusicBand;
import musicbandlab.core.ports.MusicBandRepository;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Реализация репозитория для хранения коллекции музыкальных групп.
 * Использует Hashtable для хранения элементов по ключу.
*/
public final class MusicBandRepositoryImpl implements MusicBandRepository {
    private static final ObjectMapper mapper = createMapper();
    private final Hashtable<String, MusicBand> hashTable = new Hashtable<>();
    private static int nextId = 1;

    @Override
    public ArrayList<Map.Entry<String, MusicBand>> getAll() {
        return new ArrayList<>(hashTable.entrySet());
    }

    @Override
    public MusicBand get(String key) {
        throwIfKeyIsNullOrEmpty(key);
        return hashTable.getOrDefault(key, null);
    }

    @Override
    public int getHashCode() {
        return hashTable.hashCode();
    }

    @Override
    public void insert(String key, MusicBand musicBand) {
        throwIfKeyIsNullOrEmpty(key);
        Objects.requireNonNull(musicBand, "musicBand");

        if(hashTable.containsKey(key))
            throw new IllegalArgumentException("Key already exists");

        musicBand.setId(nextId);
        hashTable.put(key, musicBand);
        nextId++;
    }

    @Override
    public void updateWhereIdIsEqualTo(int id, MusicBand musicBand) {
        Objects.requireNonNull(musicBand, "musicBand");
        boolean found = false;

        for (MusicBand band : hashTable.values()) {
            if (band.getId() == id) {
                band.mapFrom(musicBand);
                found = true;
            }
        }

        if(!found) {
            throw new IllegalArgumentException("There are no music bands with id " + id);
        }
    }

    @Override
    public void remove(String key) {
        throwIfKeyIsNullOrEmpty(key);
        hashTable.remove(key);
    }

    @Override
    public void clear() {
        hashTable.clear();
    }

    @Override
    public void removeWhereLessThan(MusicBand musicBand) {
        Objects.requireNonNull(musicBand, "musicBand");

        hashTable.entrySet().removeIf(entry ->
            entry.getValue().compareTo(musicBand) < 0
        );
    }

    @Override
    public Boolean replaceIfGreaterThan(String key, MusicBand musicBand) {
        throwIfKeyIsNullOrEmpty(key);
        Objects.requireNonNull(musicBand, "musicBand");

        MusicBand existing = hashTable.get(key);
        if (existing == null) {
            return false;
        }

        if (musicBand.compareTo(existing) > 0) {
            existing.mapFrom(musicBand);
            return true;
        }
        return false;
    }

    @Override
    public Boolean replaceIfLessThan(String key, MusicBand musicBand) {
        throwIfKeyIsNullOrEmpty(key);
        Objects.requireNonNull(musicBand, "musicBand");

        MusicBand existing = hashTable.get(key);
        if (existing == null) {
            return false;
        }

        if (musicBand.compareTo(existing) < 0) {
            existing.mapFrom(musicBand);
            return true;
        }
        return false;
    }
    @Override
    public long getSumNumberOfParticipants() {
        Collection<MusicBand> musicBands = hashTable.values();
        int count = 0;
        long result;
        ArrayList<Long> numberOfParticipants = new ArrayList<>();
        for (MusicBand per : musicBands) {
            count += 1;
            numberOfParticipants.add(per.getNumberOfParticipants());
        }
        numberOfParticipants.sort(Comparator.naturalOrder());
        if (count % 2 == 1) {
            result = numberOfParticipants.get(count / 2);
        } else if (count == 0) {
            result = 0;
        }else {
            result = (numberOfParticipants.get(count / 2) + numberOfParticipants.get(count / 2 - 1)) /2 ;
        }
        return result;
    }

    @Override
    public int getCountWhereNumberOfParticipantsEqualsTo(long numberOfParticipants) {
        if (numberOfParticipants <= 0) {
            return 0;
        }

        int count = 0;
        for (MusicBand band : hashTable.values()) {
            if (band != null && band.getNumberOfParticipants() == numberOfParticipants) {
                count++;
            }
        }
        return count;
    }

    @Override
    public ArrayList<MusicBand> getWhereLabelLessThan(Label label) {
        if (label == null) {
            return new ArrayList<>();
        }

        ArrayList<MusicBand> result = new ArrayList<>();

        for (MusicBand band : hashTable.values()) {
            if (band != null && band.getLabel() != null) {
                if (band.getLabel().compareTo(label) < 0) {
                    result.add(band);
                }
            }
        }
        return result;
    }

    @Override
    public ArrayList<Label> getLabelsDescending() {
        ArrayList<Label> labels = new ArrayList<>();

        for (MusicBand band : hashTable.values()) {
            if (band != null && band.getLabel() != null && band.getLabel().getBands() != null) {
                labels.add(band.getLabel());
            }
        }

        labels.sort(Comparator.reverseOrder());
        return labels;
    }

    @Override
    public int getSize() {
        return hashTable.size();
    }

    @Override
    public ZonedDateTime getInitializationDate() {
        if (hashTable.isEmpty()) {
            return null;
        }

        ZonedDateTime min = null;

        for (MusicBand band : hashTable.values()) {
            ZonedDateTime creationDate = band.getCreationDate();
            if(creationDate == null) {
                throw new RuntimeException("CreationDate cannot be null");
            }

            if (min == null || creationDate.isBefore(min)) {
                min = creationDate;
            }
        }

        return min;
    }

    public String serializeToJson() {
        try {
            Hashtable<String, Object> data = new Hashtable<>();
            data.put("nextId", nextId);
            data.put("bands", hashTable);

            return mapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize to JSON", e);
        }
    }

    public static MusicBandRepositoryImpl deserializeFromJson(String json) {
        if (json == null || json.trim().isEmpty() || "{}".equals(json.trim())) {
            return new MusicBandRepositoryImpl();
        }

        try {
            MusicBandRepositoryImpl repo = new MusicBandRepositoryImpl();

            Map<String, Object> data = mapper.readValue(json, Map.class);

            if (data.containsKey("nextId")) {
                nextId = ((Number) data.get("nextId")).intValue();
            }

            if (data.containsKey("bands")) {
                Map<String, Object> bandsMap = (Map<String, Object>) data.get("bands");

                for (Map.Entry<String, Object> entry : bandsMap.entrySet()) {
                    String key = entry.getKey();
                    MusicBand band = mapper.convertValue(entry.getValue(), MusicBand.class);
                    repo.hashTable.put(key, band);
                }
            }

            return repo;
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize from json", e);
        }
    }

    private static ObjectMapper createMapper() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return om;
    }

    private void throwIfKeyIsNullOrEmpty(String key) {
        if(key == null || key.isEmpty())
            throw new IllegalArgumentException("Key should be not empty");
    }
}