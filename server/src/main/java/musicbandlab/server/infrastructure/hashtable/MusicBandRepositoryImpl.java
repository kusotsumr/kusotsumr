package musicbandlab.server.infrastructure.hashtable;

import com.fasterxml.jackson.databind.ObjectMapper;
import musicbandlab.common.contracts.DataEntry;
import musicbandlab.common.domain.Label;
import musicbandlab.common.domain.MusicBand;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Реализация репозитория для хранения коллекции музыкальных групп.
 * Использует Hashtable для хранения элементов по ключу.
*/
public final class MusicBandRepositoryImpl implements MusicBandRepository {
    private final ObjectMapper mapper;
    private final Hashtable<String, MusicBand> hashTable = new Hashtable<>();
    private static int nextId = 1;

    public MusicBandRepositoryImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ArrayList<DataEntry<String, MusicBand>> getAll(int page, int pageSize) {
        return hashTable.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .map(entry -> new DataEntry<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public MusicBand get(String key) {
        throwIfKeyIsNullOrEmpty(key);
        return hashTable.get(key);
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

        boolean found = hashTable.values().stream()
                .anyMatch(band -> {
                    if (band.getId() == id) {
                        band.mapFrom(musicBand);
                        return true;
                    }
                    return false;
                });

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
        if (existing != null && musicBand.compareTo(existing) > 0) {
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
        if (existing != null && musicBand.compareTo(existing) < 0) {
            existing.mapFrom(musicBand);
            return true;
        }
        return false;
    }

    @Override
    public int getCountWhereNumberOfParticipantsEqualsTo(long numberOfParticipants) {
        if (numberOfParticipants <= 0) {
            return 0;
        }

        return (int) hashTable.values().stream()
                .filter(Objects::nonNull)
                .filter(band -> band.getNumberOfParticipants() == numberOfParticipants)
                .count();
    }

    @Override
    public ArrayList<MusicBand> getWhereLabelLessThan(Label label, int page, int pageSize) {
        if (label == null) {
            return new ArrayList<>();
        }

        return hashTable.values().stream()
                .filter(Objects::nonNull)
                .filter(band -> band.getLabel() != null)
                .filter(band -> band.getLabel().compareTo(label) < 0)
                .sorted()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Label> getLabelsDescending(int page, int pageSize) {
        return hashTable.values().stream()
                .filter(Objects::nonNull)
                .map(MusicBand::getLabel)
                .filter(Objects::nonNull)
                .filter(label -> label.getBands() != null)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toCollection(ArrayList::new));
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

        return hashTable.values().stream()
                .filter(Objects::nonNull)
                .map(MusicBand::getCreationDate)
                .filter(Objects::nonNull)
                .min(ZonedDateTime::compareTo)
                .orElseThrow(() -> new RuntimeException("CreationDate cannot be null"));
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

    public static MusicBandRepositoryImpl deserializeFromJson(String json, ObjectMapper mapper) {
        if (json == null || json.trim().isEmpty() || "{}".equals(json.trim())) {
            return new MusicBandRepositoryImpl(mapper);
        }

        try {
            MusicBandRepositoryImpl repo = new MusicBandRepositoryImpl(mapper);

            Map<String, Object> data = mapper.readValue(json, Map.class);

            if (data.containsKey("nextId")) {
                nextId = ((Number) data.get("nextId")).intValue();
            }

            if (data.containsKey("bands")) {
                Map<String, Object> bandsMap = (Map<String, Object>) data.get("bands");

                int errors = 0;
                for (Map.Entry<String, Object> entry : bandsMap.entrySet()) {
                    try {
                        String key = entry.getKey();
                        MusicBand band = mapper.convertValue(entry.getValue(), MusicBand.class);
                        repo.hashTable.put(key, band);
                    }
                    catch (Exception e) {
                        errors++;
                    }
                }

                if (errors > 0) {
                    System.out.println("Сохраненная коллекция прочитана с ошибками. Пропущено музыкальных групп: " + errors);
                }
            }

            return repo;
        } catch (Exception e) {
            System.out.println("Ошибка при чтении сохраненной коллекции.");
            System.exit(1);
            throw new UnsupportedOperationException("Эта строчка никогда не вызовется из-за system.exit");
        }
    }

    private void throwIfKeyIsNullOrEmpty(String key) {
        if(key == null || key.isEmpty())
            throw new IllegalArgumentException("Key should be not empty");
    }
}